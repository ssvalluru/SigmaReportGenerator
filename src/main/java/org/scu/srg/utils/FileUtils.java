package org.scu.srg.utils;

import org.scu.srg.exceptions.SRGException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

  public static List<Path> listFilesFromPath(String path) {
    try (Stream<Path> paths = Files.walk(Paths.get(path), Integer.MAX_VALUE)) {
      return paths
          .filter(Files::isRegularFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new SRGException("Error in listing files from path " + path, e);
    }
  }

  public static List<Path> listFilesFromClassPathResource(String resource) {
    URI classPathUri;
    try {
      classPathUri = ClassLoader.getSystemResource(resource).toURI();
    } catch (URISyntaxException e) {
      throw new SRGException("Invalid or unknown resource " + resource, e);
    }
    try (Stream<Path> paths = Files.walk(Paths.get(classPathUri), Integer.MAX_VALUE)) {
      return paths
          .filter(Files::isRegularFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("Error in listing files from resource" + resource, e);
    }
  }
}
