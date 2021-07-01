package file;
import java.io.*;

public class CheckOpen {
    public static boolean hasFile(String path) {
        File dir = new File("src/file/" + path);
        if (dir.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static void createDir(String path) {
        File dir = new File("src/file/" + path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void createFile(String path) {
        File file = new File("src/file/" + path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
