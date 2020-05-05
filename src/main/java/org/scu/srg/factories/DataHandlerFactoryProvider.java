package org.scu.srg.factories;

import org.scu.srg.exceptions.SRGException;
import org.scu.srg.models.DataFormat;

public class DataHandlerFactoryProvider {

  public static DataHandlerFactory getFactory(DataFormat dataFormat) {
    switch (dataFormat) {
      case TEXT:
        return TextHandlerFactory.getInstance();
      case XML:
        return XMLHandlerFactory.getInstance();
      case DB:
        return DBHandlerFactory.getInstance();
      default:
        throw new SRGException("Unsupported data format: " + dataFormat);
    }
  }
}
