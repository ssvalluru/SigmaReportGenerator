package org.scu.srg.factories;

import org.scu.srg.readers.DBDataReader;
import org.scu.srg.readers.DataReader;
import org.scu.srg.writers.DBReportWriter;
import org.scu.srg.writers.ReportWriter;

public class DBHandlerFactory extends DataHandlerFactory {

  private DBHandlerFactory() {
  }

  private static final DBHandlerFactory instance = new DBHandlerFactory();

  public static DBHandlerFactory getInstance() {
    return instance;
  }

  @Override
  public DataReader createDataReader() {
    return new DBDataReader();
  }

  @Override
  public ReportWriter createReportWriter() {
    return new DBReportWriter();
  }
}
