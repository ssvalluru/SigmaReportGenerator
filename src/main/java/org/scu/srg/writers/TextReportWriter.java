package org.scu.srg.writers;

import org.scu.srg.exceptions.SRGException;
import org.scu.srg.models.CompletedProjectReportContext;
import org.scu.srg.models.ProjectReportContext;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class TextReportWriter implements ReportWriter {

  private static final String DASH_LINE = repeatChar('-', 100);
  private static final String projectsCanceledKey = "No. of Projects Canceled: ";
  private static final String projectsCompletedKey = "No. of Projects Completed: ";
  private static final String projectsActiveKey = "No. of Projects Active: ";
  private static final String PROJECT_ID_KEY = "Project Id: ";
  private static final String TASK_COUNT_KEY = "No. of Tasks: ";
  private static final String START_DATE_KEY = "Start Date: ";
  private static final String COMPLETED_DATE_KEY = "Completed Date: ";
  private static final String STAFF_COUNT_KEY = "No. of Staff: ";
  private static final String CONTRACT_EMPLOYEE_COUNT_KEY = "No. of Contract Employees: ";
  private static final String EMPLOYEE_LIST_KEY = "List of Employees (Staff and Contract employees) assigned to the project: ";

  @Override
  public void writeReport(ProjectReportContext projectReportContext) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("project-report.txt"))) {
      writer.println(DASH_LINE);
      writer.println(projectsCanceledKey + projectReportContext.getCancelledProjectsCount());
      writer.println(projectsCompletedKey + projectReportContext.getCompletedProjectsCount());
      writer.println(projectsActiveKey + projectReportContext.getActiveProjectsCount());
      writer.println();
      writer.println("Completed projects:");
      for (CompletedProjectReportContext completedProjectReportContext : projectReportContext.getCompletedProjectReportContexts()) {
        writer.println(PROJECT_ID_KEY + completedProjectReportContext.getProjectId());
        writer.println(TASK_COUNT_KEY + completedProjectReportContext.getTasksCount());
        writer.println(START_DATE_KEY + completedProjectReportContext.getStartDate());
        writer.println(COMPLETED_DATE_KEY + completedProjectReportContext.getEndDate());
        writer.println(STAFF_COUNT_KEY + completedProjectReportContext.getStaffCount());
        writer.println(CONTRACT_EMPLOYEE_COUNT_KEY + completedProjectReportContext.getContractEmployeeCount());
        writer.println(EMPLOYEE_LIST_KEY + String.join(", ", completedProjectReportContext.getEmployees()));
        writer.println();
      }
      writer.println(DASH_LINE);
    } catch (IOException e) {
      throw new SRGException("Error in writing report", e);
    }

  }

  private static String repeatChar(char c, int length) {
    char[] data = new char[length];
    Arrays.fill(data, c);
    return new String(data);
  }
}
