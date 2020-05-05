package org.scu.srg.models;

import java.time.LocalDate;

public class Task {
  private String taskId;
  private LocalDate startDate;
  private LocalDate endDate;
  private Employee employee;

  public Task(String taskId) {
    this.taskId = taskId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public String toString() {
    return "Task{" +
        "taskId='" + taskId + '\'' +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", employee=" + employee +
        '}';
  }
}
