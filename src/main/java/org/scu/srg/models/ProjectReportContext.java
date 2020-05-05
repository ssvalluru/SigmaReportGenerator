package org.scu.srg.models;

import java.util.List;

public class ProjectReportContext {
  private int cancelledProjectsCount;
  private int completedProjectsCount;
  private int activeProjectsCount;
  private List<CompletedProjectReportContext> completedProjectReportContexts;

  private ProjectReportContext(Builder builder) {
    this.cancelledProjectsCount = builder.cancelledProjectsCount;
    this.completedProjectsCount = builder.completedProjectsCount;
    this.activeProjectsCount = builder.activeProjectsCount;
    this.completedProjectReportContexts = builder.completedProjectReportContexts;
  }

  public int getCancelledProjectsCount() {
    return cancelledProjectsCount;
  }

  public int getCompletedProjectsCount() {
    return completedProjectsCount;
  }

  public int getActiveProjectsCount() {
    return activeProjectsCount;
  }

  public List<CompletedProjectReportContext> getCompletedProjectReportContexts() {
    return completedProjectReportContexts;
  }

  public static ProjectReportContext.Builder builder() {
    return new ProjectReportContext.Builder();
  }

  public static class Builder {
    private int cancelledProjectsCount;
    private int completedProjectsCount;
    private int activeProjectsCount;
    private List<CompletedProjectReportContext> completedProjectReportContexts;

    public Builder cancelledProjectsCount(int cancelledProjectsCount) {
      this.cancelledProjectsCount = cancelledProjectsCount;
      return this;
    }

    public Builder completedProjectsCount(int completedProjectsCount) {
      this.completedProjectsCount = completedProjectsCount;
      return this;
    }

    public Builder activeProjectsCount(int activeProjectsCount) {
      this.activeProjectsCount = activeProjectsCount;
      return this;
    }

    public Builder completedProjectReportContexts(
        List<CompletedProjectReportContext> completedProjectReportContexts) {
      this.completedProjectReportContexts = completedProjectReportContexts;
      return this;
    }

    public ProjectReportContext build() {
      return new ProjectReportContext(this);
    }

  }

  @Override
  public String toString() {
    return "ProjectReportContext{" +
        "cancelledProjectsCount=" + cancelledProjectsCount +
        ", completedProjectsCount=" + completedProjectsCount +
        ", activeProjectsCount=" + activeProjectsCount +
        ", completedProjectReportContexts=" + completedProjectReportContexts +
        '}';
  }
}
