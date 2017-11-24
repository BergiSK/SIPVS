package retrieve;

import java.io.File;
import java.util.List;

public interface RetrieveService {

    List<File> getFilesFromLocation(String path);
}
