package validation.check;

import validation.check.checkers.SignatureChecker;

import java.io.File;
import java.util.List;

public interface CheckingService {
    void checkFile(List<SignatureChecker> checkerList, File file);
}
