package validation.check;

import validation.check.checkers.SignatureChecker;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class CheckingServiceImpl implements CheckingService {

    @Override
    public void checkFile(List<SignatureChecker> checkerList, File file) {
        System.out.format("Checking file: %s \n", file);
        checkerList.forEach(checker -> checker.check(file));
    }
}
