package validation;

import retrieve.RetrieveService;
import retrieve.RetrieveServiceImpl;
import validation.check.CheckingService;
import validation.check.CheckingServiceImpl;
import validation.check.checkers.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ValidationManagerImpl implements ValidationManager{

    private RetrieveService retrieveService = new RetrieveServiceImpl();

    private CheckingService checkingService = new CheckingServiceImpl();

    private List<Checker> checkers =
            Arrays.asList(new TimeRangeChecker(), new AttributeChecker(), new TimeStampChecker(), new ElementContentChecker());

    @Override
    public void validate() {
        for (File f : retrieveService.getFilesFromLocation("./signature-validator/src/main/resources/xml")) {
            checkingService.checkFile(checkers, f);
        }
    }
}
