package retrieve;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class RetrieveServiceImpl implements RetrieveService {

    @Override
    public List<File> getFilesFromLocation(String path) {
        File resources = new File(path);
        return Arrays.asList(resources.listFiles());
    }
}
