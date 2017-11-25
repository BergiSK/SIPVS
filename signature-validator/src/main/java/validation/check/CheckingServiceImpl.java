package validation.check;

import validation.check.checkers.Checker;

import java.io.File;
import java.util.List;

public class CheckingServiceImpl implements CheckingService {

    @Override
    public void checkFile(List<Checker> checkerList, File file) {
        System.out.format("Checking file: %s \n", file);
        checkerList.forEach(checker -> checker.check(file));
    }
}
