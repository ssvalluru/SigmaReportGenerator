package org.scu.srg.factories;

import org.scu.srg.readers.DataReader;
import org.scu.srg.writers.ReportWriter;

public abstract class DataHandlerFactory {
   public abstract DataReader createDataReader();
   public abstract ReportWriter createReportWriter();
}
