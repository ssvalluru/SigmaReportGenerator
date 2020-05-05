package org.scu.srg.services;

import static java.util.stream.Collectors.groupingBy;
import org.scu.srg.models.CompletedProjectReportContext;
import org.scu.srg.models.Employee;
import org.scu.srg.models.EmployeeType;
import org.scu.srg.models.Project;
import org.scu.srg.models.ProjectReportContext;
import org.scu.srg.models.ProjectStatus;
import org.scu.srg.models.Task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectReportService {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  public ProjectReportContext prepareReportContext(List<Project> projects) {
    Map<ProjectStatus, List<Project>> projectsPerStatus =
        projects.stream().collect(groupingBy(Project::getProjectStatus));

    return ProjectReportContext.builder()
        .cancelledProjectsCount(projectsPerStatus.getOrDefault(ProjectStatus.CANCELLED, Collections.emptyList()).size())
        .completedProjectsCount(projectsPerStatus.getOrDefault(ProjectStatus.COMPLETED, Collections.emptyList()).size())
        .activeProjectsCount(projectsPerStatus.getOrDefault(ProjectStatus.ACTIVE, Collections.emptyList()).size())
        .completedProjectReportContexts(prepareCompletedReportContext(
            projectsPerStatus.get(ProjectStatus.COMPLETED)))
        .build();
  }

  private List<CompletedProjectReportContext> prepareCompletedReportContext(
      List<Project> projects) {
    return projects == null ? Collections.emptyList() :
        projects.stream()
            .map(this::prepareCompletedReportContext)
            .collect(Collectors.toList());
  }

  private CompletedProjectReportContext prepareCompletedReportContext(Project project) {
    List<String> employees = new ArrayList<>();
    LocalDate startDate = project.getTasks().get(0).getStartDate();
    LocalDate endDate = project.getTasks().get(0).getEndDate();
    int staffCount = 0;
    int contractorCount = 0;

    for (Task task : project.getTasks()) {
      startDate = startDate.isBefore(task.getStartDate()) ? startDate : task.getStartDate();
      endDate = endDate.isAfter(task.getEndDate()) ? endDate : task.getEndDate();
      Employee employee = task.getEmployee();
      if (employee != null) {
        employees.add(employee.getName());
        if (employee.getEmployeeType() == EmployeeType.STAFF) {
          staffCount++;
        } else {
          contractorCount++;
        }
      }
    }

    return CompletedProjectReportContext.builder()
        .projectId(project.getProjectId())
        .tasksCount(project.getTasks().size())
        .startDate(startDate.format(formatter))
        .endDate(endDate.format(formatter))
        .staffCount(staffCount)
        .contractEmployeeCount(contractorCount)
        .employees(employees)
        .build();
  }
}
