package com.intuit.craft.streaming;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigFactory {
  public static Properties loadConfiguration() {
    Properties properties = null;
    InputStream input = null;
    try {
      input = new FileInputStream(StreamingConstants.CONFIG_FILE_PATH + StreamingConstants.CONFIG_FILE_NAME);
      properties = new Properties();
      properties.load(input);
    } catch (IOException ex) {
      System.out.println("Couldn't find configuration file. Please validate config file.");
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          System.out.println("Unable to close the InputStream for configuration file. : " + e.getMessage());
        }
      }
    }
    return properties;
  }
}
