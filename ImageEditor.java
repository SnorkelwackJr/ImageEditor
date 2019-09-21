package editor;

import java.io.File;
import java.io.IOException;

public class ImageEditor {

    public static void main(String args[]) throws IOException {

        // Check for input file
        File inFile = null;
        if (0 < args.length) {
            inFile = new File(args[0]);
        }
        else {
            System.err.println("Invalid arguments count:" + args.length);
            System.exit(1);
        }

        // Instantiate PPMImage
        PPMImage myImage = new PPMImage();

        // Read input file
        ReadFromFile reader = new ReadFromFile(myImage);
        reader.processFile(inFile);

        // Apply Transformation
        if (args.length == 4) {
            myImage.transformImage(args[2], args[3]);
        }
        else {
            myImage.transformImage(args[2], "0");
        }

        // Write to output file
        File outFile = new File(args[1]);
        WriteToFile writer = new WriteToFile();
        writer.writeFile(outFile, myImage);
    }
}
