package org.scu.srg.factories;

import org.scu.srg.readers.DataReader;
import org.scu.srg.readers.XMLDataReader;
import org.scu.srg.writers.ReportWriter;
import org.scu.srg.writers.XMLReportWriter;

public class XMLHandlerFactory extends DataHandlerFactory {
  private XMLHandlerFactory() {
  }

  private static final XMLHandlerFactory instance = new XMLHandlerFactory();

  public static XMLHandlerFactory getInstance() {
    return instance;
  }

  @Override
  public DataReader createDataReader() {
    return new XMLDataReader();
  }

  @Override
  public ReportWriter createReportWriter() {
    return new XMLReportWriter();
  }
}
