package editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    public void writeFile(File file, PPMImage image) throws IOException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(image.ToString());
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
