package editor;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class ReadFromFile {

    private PPMImage myImage;

    public ReadFromFile(PPMImage imageToSet) {
        myImage = imageToSet;
    }

    public void processFile(File file) throws IOException {

        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");


        while (scanner.hasNext()) {
            String str = scanner.next();

            // Collect and set width, height, and maxRGB values
            if (str.equals("P3")) {
                continue;
            }
            if (myImage.width == 0) {
                myImage.width = Integer.parseInt(str);
                continue;
            }
            if (myImage.height == 0) {
                myImage.height = Integer.parseInt(str);
                continue;
            }
            if (myImage.maxRGB == 0) {
                myImage.maxRGB = Integer.parseInt(str);
                continue;
            }

            // Collect and set RGB color values for the pixel
            int[] RGBSet = new int[3];
            RGBSet[0] = Integer.parseInt(str);
            str = scanner.next();
            RGBSet[1] = Integer.parseInt(str);
            str = scanner.next();
            RGBSet[2] = Integer.parseInt(str);

            Pixel currentPixel = new Pixel(RGBSet);
            myImage.allRGB.add(currentPixel);
        }
    }

    public PPMImage getMyImage() {
        return myImage;
    }
}