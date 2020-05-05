package org.scu.srg.factories;

import org.scu.srg.readers.DataReader;
import org.scu.srg.readers.TextDataReader;
import org.scu.srg.writers.ReportWriter;
import org.scu.srg.writers.TextReportWriter;

public class TextHandlerFactory extends DataHandlerFactory {
  private TextHandlerFactory() {
  }

  private static final TextHandlerFactory instance = new TextHandlerFactory();

  public static TextHandlerFactory getInstance() {
    return instance;
  }

  @Override
  public DataReader createDataReader() {
    return new TextDataReader();
  }

  @Override
  public ReportWriter createReportWriter() {
    return new TextReportWriter();
  }
}
