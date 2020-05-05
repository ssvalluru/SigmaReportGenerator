package org.scu.srg.helpers;

import org.scu.srg.exceptions.SRGException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesStore {
  private static final String PROPERTIES_FILE = "/application.properties";

  private static Properties properties;

  static {
    loadProperties();
  }

  private static void loadProperties() {
    properties = new Properties();
    try {
      properties.load(PropertiesStore.class.getResourceAsStream(PROPERTIES_FILE));
    } catch (IOException e) {
      throw new SRGException("Error in loading properties", e);
    }
  }

  public static String getPropertyValue(String key) {
    return properties.getProperty(key);
  }
}
