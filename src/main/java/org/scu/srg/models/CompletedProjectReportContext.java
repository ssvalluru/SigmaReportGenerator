package org.scu.srg.models;

import java.util.List;

public class CompletedProjectReportContext {
  private String projectId;
  private int tasksCount;
  private String startDate;
  private final String endDate;
  private int staffCount;
  private int contractEmployeeCount;
  private List<String> employees;

  private CompletedProjectReportContext(Builder builder) {
    this.projectId = builder.projectId;
    this.tasksCount = builder.tasksCount;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.staffCount = builder.staffCount;
    this.contractEmployeeCount = builder.contractEmployeeCount;
    this.employees = builder.employees;
  }

  public String getProjectId() {
    return projectId;
  }

  public int getTasksCount() {
    return tasksCount;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public int getStaffCount() {
    return staffCount;
  }

  public int getContractEmployeeCount() {
    return contractEmployeeCount;
  }

  public List<String> getEmployees() {
    return employees;
  }

  public static CompletedProjectReportContext.Builder builder() {
    return new CompletedProjectReportContext.Builder();
  }

  public static class Builder {
    private String projectId;
    private int tasksCount;
    private String startDate;
    private String endDate;
    private int staffCount;
    private int contractEmployeeCount;
    private List<String> employees;

    public Builder projectId(String projectId) {
      this.projectId = projectId;
      return this;
    }

    public Builder tasksCount(int tasksCount) {
      this.tasksCount = tasksCount;
      return this;
    }

    public Builder startDate(String startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder endDate(String endDate) {
      this.endDate = endDate;
      return this;
    }

    public Builder staffCount(int staffCount) {
      this.staffCount = staffCount;
      return this;
    }

    public Builder contractEmployeeCount(int contractEmployeeCount) {
      this.contractEmployeeCount = contractEmployeeCount;
      return this;
    }

    public Builder employees(List<String> employees) {
      this.employees = employees;
      return this;
    }

    public CompletedProjectReportContext build() {
      return new CompletedProjectReportContext(this);
    }
  }

  @Override
  public String toString() {
    return "CompletedProjectReportContext{" +
        "projectId='" + projectId + '\'' +
        ", tasksCount=" + tasksCount +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", staffCount=" + staffCount +
        ", contractEmployeeCount=" + contractEmployeeCount +
        ", employees=" + employees +
        '}';
  }
}
