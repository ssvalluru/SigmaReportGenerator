package org.scu.srg.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Project {
  private String projectId;
  private ProjectStatus projectStatus;
  private final Map<String, Task> taskMap;

  public Project(String projectId) {
    this.projectId = projectId;
    this.taskMap = new HashMap<>();
    this.projectStatus = ProjectStatus.COMPLETED;
  }

  public void addTask(Task task) {
    taskMap.putIfAbsent(task.getTaskId(), task);
  }

  public Optional<Task> getTask(String taskId) {
    return Optional.ofNullable(taskMap.get(taskId));
  }

  public List<Task> getTasks() {
    return new ArrayList<>(taskMap.values());
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public ProjectStatus getProjectStatus() {
    return projectStatus;
  }

  @Override
  public String toString() {
    return "Project{" +
        "projectId='" + projectId + '\'' +
        ", projectStatus=" + projectStatus +
        ", taskMap=" + taskMap +
        '}';
  }

  public void setProjectStatus(ProjectStatus projectStatus) {
    this.projectStatus = projectStatus;
  }

}
