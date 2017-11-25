package validation.check;

import validation.check.checkers.Checker;

import java.io.File;
import java.util.List;

public interface CheckingService {
    void checkFile(List<Checker> checkerList, File file);
}
