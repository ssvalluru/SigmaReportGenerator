package org.scu.srg.readers;

import org.scu.srg.models.Project;
import java.util.List;

public interface DataReader {
  List<Project> readData();
}
