package org.scu.srg.writers;

import org.scu.srg.models.ProjectReportContext;

public interface ReportWriter {
  void writeReport(ProjectReportContext projectReportContext);
}
