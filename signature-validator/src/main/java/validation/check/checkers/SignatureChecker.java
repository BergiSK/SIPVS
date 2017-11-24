package validation.check.checkers;

import java.io.File;

public interface SignatureChecker {

    boolean check(File file);
}
