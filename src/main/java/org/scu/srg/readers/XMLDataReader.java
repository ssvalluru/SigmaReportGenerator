package org.scu.srg.readers;

import static org.scu.srg.utils.SRGConstants.XML_PROJECT_DATA_RESOURCE_KEY;
import org.scu.srg.exceptions.SRGException;
import org.scu.srg.helpers.PropertiesStore;
import org.scu.srg.models.Employee;
import org.scu.srg.models.EmployeeType;
import org.scu.srg.models.Project;
import org.scu.srg.models.ProjectStatus;
import org.scu.srg.models.Task;
import org.scu.srg.utils.FileUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLDataReader implements DataReader {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  @Override
  public List<Project> readData() {
    Map<String, Project> projectMap = new HashMap<>();
    List<Path> projectFiles = getProjectFiles();
    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    for (Path projectFile : projectFiles) {
      try (InputStream inputStream = new FileInputStream(projectFile.toFile())) {
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

        while (xmlEventReader.hasNext()) {
          XMLEvent xmlEvent = xmlEventReader.nextEvent();
          if (xmlEvent.isStartElement()) {
            StartElement startElement = xmlEvent.asStartElement();
            if (startElement.getName().getLocalPart().equalsIgnoreCase("starttask")) {

              //Active Task
              Attribute taskId = startElement.getAttributeByName(new QName("taskId"));
              Attribute projectId = startElement.getAttributeByName(new QName("projectId"));
              Attribute startDate = startElement.getAttributeByName(new QName("startDate"));
              if (taskId == null || projectId == null || startDate == null) {
                throw new SRGException("Invalid content in XML");
              }
              Project project = projectMap.getOrDefault(projectId.getValue(), new Project(projectId.getValue()));
              Task task = project.getTask(taskId.getValue()).orElse(new Task(taskId.getValue()));
              task.setStartDate(parseDate(startDate.getValue()));
              project.addTask(task);
              project.setProjectStatus(ProjectStatus.ACTIVE);
              projectMap.putIfAbsent(projectId.getValue(), project);
            } else if (startElement.getName().getLocalPart().equalsIgnoreCase("endtask")) {

              //Completed Task
              Attribute taskId = startElement.getAttributeByName(new QName("taskId"));
              Attribute projectId = startElement.getAttributeByName(new QName("projectId"));
              Attribute startDate = startElement.getAttributeByName(new QName("startDate"));
              Attribute endDate = startElement.getAttributeByName(new QName("endDate"));
              if (taskId == null || projectId == null || startDate == null || endDate == null) {
                throw new SRGException("Invalid content in XML");
              }

              Project project = projectMap.getOrDefault(projectId.getValue(), new Project(projectId.getValue()));
              Task task = project.getTask(taskId.getValue()).orElse(new Task(taskId.getValue()));
              task.setStartDate(parseDate(startDate.getValue()));
              task.setEndDate(parseDate(endDate.getValue()));
              project.addTask(task);
              projectMap.putIfAbsent(projectId.getValue(), project);

            } else if (startElement.getName().getLocalPart().equalsIgnoreCase("project")) {

              //Cancelled Task
              Attribute status = startElement.getAttributeByName(new QName("status"));
              Attribute projectId = startElement.getAttributeByName(new QName("projectId"));
              if (status == null || projectId == null) {
                throw new SRGException("Invalid content in XML");
              }
              if (status.getValue().equalsIgnoreCase(ProjectStatus.CANCELLED.name())) {
                Project project = projectMap.getOrDefault(projectId.getValue(), new Project(projectId.getValue()));
                project.setProjectStatus(ProjectStatus.CANCELLED);
                projectMap.putIfAbsent(projectId.getValue(), project);
              }

            } else if (startElement.getName().getLocalPart().equalsIgnoreCase("assignedtask")) {

              //Assign employee to task
              Attribute taskId = startElement.getAttributeByName(new QName("taskId"));
              Attribute projectId = startElement.getAttributeByName(new QName("projectId"));
              Attribute empName = startElement.getAttributeByName(new QName("empName"));
              Attribute email = startElement.getAttributeByName(new QName("email"));
              Attribute status = startElement.getAttributeByName(new QName("status"));
              if (taskId == null || projectId == null || empName == null || email == null || status == null) {
                throw new SRGException("Invalid content in XML");
              }

              Employee employee = new Employee(empName.getValue(), email.getValue(),
                  EmployeeType.valueOf(status.getValue().toUpperCase()));
              Project project = projectMap.getOrDefault(projectId.getValue(), new Project(projectId.getValue()));
              Task task = project.getTask(taskId.getValue()).orElse(new Task(taskId.getValue()));
              task.setEmployee(employee);
              project.addTask(task);
              projectMap.putIfAbsent(projectId.getValue(), project);
            }
          }
        }
      } catch (IOException | XMLStreamException e) {
        throw new SRGException("Error in reading xml project data", e);
      }
    }
    return new ArrayList<>(projectMap.values());
  }

  private List<Path> getProjectFiles() {
    return FileUtils.listFilesFromClassPathResource(
        PropertiesStore.getPropertyValue(XML_PROJECT_DATA_RESOURCE_KEY));
  }

  private LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      throw new SRGException(String.format("Invalid date %s in the data", date), e);
    }
  }
}
