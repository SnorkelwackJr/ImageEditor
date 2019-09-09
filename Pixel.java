package editor;

public class Pixel {
    int[] rgb = {0, 0, 0};

    public Pixel (int[] RGBSet) {
        rgb = RGBSet;
    }

    public int[] getRgb() {
        return rgb;
    }

    public String toString() {
        return rgb[0] + " " + rgb[1] + " " + rgb[2];
    }
}
