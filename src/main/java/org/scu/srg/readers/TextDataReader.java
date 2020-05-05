package org.scu.srg.readers;

import static org.scu.srg.utils.SRGConstants.TEXT_PROJECT_DATA_RESOURCE_KEY;
import org.scu.srg.exceptions.SRGException;
import org.scu.srg.helpers.PropertiesStore;
import org.scu.srg.models.Employee;
import org.scu.srg.models.EmployeeType;
import org.scu.srg.models.Project;
import org.scu.srg.models.ProjectStatus;
import org.scu.srg.models.Task;
import org.scu.srg.utils.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class TextDataReader implements DataReader {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  @Override
  public List<Project> readData() {
    Map<String, Project> projectMap = new HashMap<>();
    List<Path> projectFiles = getProjectFiles();

    for (Path projectFile : projectFiles) {
      try (Stream<String> lines = Files.lines(projectFile)) {
        lines.map(String::trim)
            .filter(line -> !line.startsWith("-")) //Ignore comments
            .forEach(line -> {
              String[] fields = splitAndTrim(line);
              if (isValidLine(fields)) {
                if (fields.length == 2
                    && fields[1].equalsIgnoreCase(ProjectStatus.CANCELLED.name())) {

                  //Cancelled Task
                  String projectId = fields[0];
                  Project project = projectMap.getOrDefault(projectId, new Project(projectId));
                  project.setProjectStatus(ProjectStatus.CANCELLED);
                  projectMap.putIfAbsent(fields[0], project);
                } else if (fields.length == 3) {

                  //Active Task
                  String taskId = fields[0];
                  String projectId = fields[1];
                  String startDate = fields[2];
                  Project project = projectMap.getOrDefault(projectId, new Project(projectId));
                  Task task = project.getTask(taskId).orElse(new Task(taskId));
                  task.setStartDate(parseDate(startDate));
                  project.addTask(task);
                  project.setProjectStatus(ProjectStatus.ACTIVE);
                  projectMap.putIfAbsent(projectId, project);
                } else if (fields.length == 4) {

                  //Completed Task
                  String taskId = fields[0];
                  String projectId = fields[1];
                  String startDate = fields[2];
                  String endDate = fields[3];
                  Project project = projectMap.getOrDefault(projectId, new Project(projectId));
                  Task task = project.getTask(taskId).orElse(new Task(taskId));
                  task.setStartDate(parseDate(startDate));
                  task.setEndDate(parseDate(endDate));
                  project.addTask(task);
                  projectMap.putIfAbsent(projectId, project);
                } else if (fields.length == 5) {

                  //Assign employee to task
                  String taskId = fields[0];
                  String projectId = fields[1];
                  Employee employee = new Employee(fields[2], fields[3],
                      EmployeeType.valueOf(fields[4].toUpperCase()));
                  Project project = projectMap.getOrDefault(projectId, new Project(projectId));
                  Task task = project.getTask(taskId).orElse(new Task(taskId));
                  task.setEmployee(employee);
                  project.addTask(task);
                  projectMap.putIfAbsent(projectId, project);
                }
              }

            });
      } catch (IOException e) {
        throw new SRGException("Error in reading project data ", e);
      }
    }
    return new ArrayList<>(projectMap.values());
  }

  private List<Path> getProjectFiles() {
    return FileUtils.listFilesFromClassPathResource(
        PropertiesStore.getPropertyValue(TEXT_PROJECT_DATA_RESOURCE_KEY));
  }

  private String[] splitAndTrim(String line) {
    return Stream.of(line.split(","))
        .map(String::trim)
        .toArray(String[]::new);
  }

  private boolean isValidLine(String[] fields) {
    return Arrays.stream(fields).noneMatch(s -> s == null || s.isEmpty());
  }

  private LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      throw new SRGException(String.format("Invalid date %s in the data", date), e);
    }
  }
}
