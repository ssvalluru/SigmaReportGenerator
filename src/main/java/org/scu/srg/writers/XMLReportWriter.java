package org.scu.srg.writers;

import org.scu.srg.models.CompletedProjectReportContext;
import org.scu.srg.models.ProjectReportContext;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLReportWriter implements ReportWriter {

  @Override
  public void writeReport(ProjectReportContext projectReportContext) {
    XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    try (FileWriter fileWriter = new FileWriter("project-report.xml")) {
      XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);

      xmlWriter.writeStartDocument();
      xmlWriter.writeStartElement("project-report");

      xmlWriter.writeStartElement("projects-cancelled-count");
      xmlWriter.writeCharacters(String.valueOf(projectReportContext.getCancelledProjectsCount()));
      xmlWriter.writeEndElement();

      xmlWriter.writeStartElement("projects-completed-count");
      xmlWriter.writeCharacters(String.valueOf(projectReportContext.getCompletedProjectsCount()));
      xmlWriter.writeEndElement();

      xmlWriter.writeStartElement("projects-active-count");
      xmlWriter.writeCharacters(String.valueOf(projectReportContext.getActiveProjectsCount()));
      xmlWriter.writeEndElement();

      if (projectReportContext.getCompletedProjectsCount() > 0) {
        xmlWriter.writeStartElement("completed-project-report");
        for (CompletedProjectReportContext context : projectReportContext.getCompletedProjectReportContexts()) {
          xmlWriter.writeStartElement("completed-project");

          xmlWriter.writeStartElement("projectId");
          xmlWriter.writeCharacters(context.getProjectId());
          xmlWriter.writeEndElement();

          xmlWriter.writeStartElement("tasks-count");
          xmlWriter.writeCharacters(String.valueOf(context.getTasksCount()));
          xmlWriter.writeEndElement();

          xmlWriter.writeStartElement("startDate");
          xmlWriter.writeCharacters(context.getStartDate());
          xmlWriter.writeEndElement();

          xmlWriter.writeStartElement("endDate");
          xmlWriter.writeCharacters(context.getEndDate());
          xmlWriter.writeEndElement();

          xmlWriter.writeStartElement("staff-count");
          xmlWriter.writeCharacters(String.valueOf(context.getStaffCount()));
          xmlWriter.writeEndElement();

          xmlWriter.writeStartElement("contractor-count");
          xmlWriter.writeCharacters(String.valueOf(context.getContractEmployeeCount()));
          xmlWriter.writeEndElement();

          List<String> employees = context.getEmployees();
          if (!employees.isEmpty()) {
            xmlWriter.writeStartElement("employees");

            for (String employee : employees) {
              xmlWriter.writeStartElement("employee");
              xmlWriter.writeCharacters(employee);
              xmlWriter.writeEndElement();
            }
            xmlWriter.writeEndElement();

          }
          xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();
      }
      xmlWriter.writeEndElement();
      xmlWriter.writeEndDocument();

      xmlWriter.flush();
      xmlWriter.close();

    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}
