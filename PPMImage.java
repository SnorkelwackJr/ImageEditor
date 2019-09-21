package editor;

import java.util.ArrayList;

public class PPMImage {
    int height = 0;
    int width = 0;
    int maxRGB = 0;
    ArrayList<Pixel> allRGB = new ArrayList<Pixel>();

    public void transformImage(String transformationType, String blurVal) {
        switch (transformationType) {
            case "invert":
                for (int i = 0; i < allRGB.size(); i++) {
                    allRGB.get(i).rgb[0] = 255 - allRGB.get(i).rgb[0];
                    allRGB.get(i).rgb[1] = 255 - allRGB.get(i).rgb[1];
                    allRGB.get(i).rgb[2] = 255 - allRGB.get(i).rgb[2];
                }
                break;
            case "grayscale":
                for (int i = 0; i < allRGB.size(); i++) {
                    int average = (allRGB.get(i).rgb[0] + allRGB.get(i).rgb[1] + allRGB.get(i).rgb[2])/3;
                    allRGB.get(i).rgb[0] = average;
                    allRGB.get(i).rgb[1] = average;
                    allRGB.get(i).rgb[2] = average;
                }
                break;
            case "emboss":
                for (int i = (allRGB.size() - 1); i >= 0; i--) {
                    // set top and left edge pixels to 128
                    if ((i < width) || (i % width == 0)) {
                        allRGB.get(i).rgb[0] = 128;
                        allRGB.get(i).rgb[1] = 128;
                        allRGB.get(i).rgb[2] = 128;
                    }
                    // run separate algorithm for remaining pixels
                    else {
                        int refPixel = (i - (width + 1));
                        int redDiff = (allRGB.get(i).rgb[0] - allRGB.get(refPixel).rgb[0]);
                        int greenDiff = (allRGB.get(i).rgb[1] - allRGB.get(refPixel).rgb[1]);
                        int blueDiff = (allRGB.get(i).rgb[2] - allRGB.get(refPixel).rgb[2]);

                        // find the largest difference
                        int maxDifference = 0;
                        if (Math.abs(redDiff) > Math.abs(maxDifference)) maxDifference = redDiff;
                        if (Math.abs(greenDiff) > Math.abs(maxDifference)) maxDifference = greenDiff;
                        if (Math.abs(blueDiff) > Math.abs(maxDifference)) maxDifference = blueDiff;

                        // set v and adjust as needed
                        int v = 128 + maxDifference;
                        if (v < 0) v = 0;
                        else if (v > 255) v = 255;

                        // assign v to all RGB values
                        allRGB.get(i).rgb[0] = v;
                        allRGB.get(i).rgb[1] = v;
                        allRGB.get(i).rgb[2] = v;
                    }
                }
                break;
            case "motionblur":
                // check number in command line
                int blurStrength = Integer.parseInt(blurVal);
                if (blurStrength <= 0) {
                    System.out.println("Blur strength must be greater than 0!");
                    break;
                }

                // begin blur effect
                for (int i = 0; i < allRGB.size(); i++) {
                    if ((i % width) != (width - 1)) {
                        int colorTotal = 0;
                        int pixels; // <-- pixels visited
                        for (pixels = 0; pixels < blurStrength; pixels++) {
                            colorTotal += (allRGB.get(i + pixels).rgb[0]);
                            if (((i + pixels) % width) == (width - 1)) {
                                pixels++;
                                break;
                            }
                        }
                        int colorAvg = colorTotal/pixels;
                        allRGB.get(i).rgb[0] = colorAvg;

                        colorTotal = 0;
                        pixels = 0; // <-- pixels visited
                        for (pixels = 0; pixels < blurStrength; pixels++) {
                            colorTotal += (allRGB.get(i + pixels).rgb[1]);
                            if (((i + pixels) % width) == (width - 1)) {
                                pixels++;
                                break;
                            }
                        }
                        colorAvg = colorTotal/pixels;
                        allRGB.get(i).rgb[1] = colorAvg;

                        colorTotal = 0;
                        pixels = 0; // <-- pixels visited
                        for (pixels = 0; pixels < blurStrength; pixels++) {
                            colorTotal += (allRGB.get(i + pixels).rgb[2]);
                            if (((i + pixels) % width) == (width - 1)) {
                                pixels++;
                                break;
                            }
                        }
                        colorAvg = colorTotal/pixels;
                        allRGB.get(i).rgb[2] = colorAvg;
                    }
                }
                break;
        }
    }

    public String ToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("P3").append("\n");
        sb.append(this.width).append("\n");
        sb.append(this.height).append("\n");
        sb.append(this.maxRGB).append("\n");
        for (int i = 0; i < allRGB.size(); i++) {
            sb.append(this.allRGB.get(i).rgb[0]).append(" ");
            sb.append(this.allRGB.get(i).rgb[1]).append(" ");
            sb.append(this.allRGB.get(i).rgb[2]).append("\n");
        }

        return sb.toString();
    }
}
