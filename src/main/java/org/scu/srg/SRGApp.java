package org.scu.srg;

import static org.scu.srg.utils.SRGConstants.DATA_FORMAT_KEY;
import org.scu.srg.exceptions.SRGException;
import org.scu.srg.factories.DataHandlerFactory;
import org.scu.srg.factories.DataHandlerFactoryProvider;
import org.scu.srg.helpers.PropertiesStore;
import org.scu.srg.models.DataFormat;
import org.scu.srg.models.Project;
import org.scu.srg.models.ProjectReportContext;
import org.scu.srg.readers.DataReader;
import org.scu.srg.services.ProjectReportService;
import org.scu.srg.writers.ReportWriter;
import java.util.List;

public class SRGApp {
  public static void main(String[] args) {

    //Read the data format from properties
    DataFormat dataFormat;
    try {
      dataFormat = DataFormat.valueOf(PropertiesStore.getPropertyValue(DATA_FORMAT_KEY));
    } catch (IllegalArgumentException e) {
      throw new SRGException("Unsupported data format", e);
    }

    //Get data handler factory
    DataHandlerFactory dataHandlerFactory = DataHandlerFactoryProvider.getFactory(dataFormat);

    //Read data from project files
    DataReader dataReader = dataHandlerFactory.createDataReader();
    List<Project> projects = dataReader.readData();

    //Process and calculate aggregations
    ProjectReportService service = new ProjectReportService();
    ProjectReportContext context = service.prepareReportContext(projects);

    //Write the final report
    ReportWriter reportWriter = dataHandlerFactory.createReportWriter();
    reportWriter.writeReport(context);
  }
}
