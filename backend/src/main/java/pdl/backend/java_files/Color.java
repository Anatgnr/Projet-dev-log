package imageprocessing;

import java.awt.image.BufferedImage;
import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.border.BorderType;
import boofcv.struct.convolve.Kernel1D_S32;
import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;


public class Color {
    public static void color_luminosity(Planar<GrayU8> input, int l){
        for (int i = 0; i < input.getNumBands(); ++i)
            GrayLevelProcessing.luminosity(input.getBand(i),l);
    }

    public static void color_convolution(Planar<GrayU8> input, Planar<GrayU8> output, int[][] kernel){
        for (int i = 0; i < input.getNumBands(); ++i)
            Convolution.convolution(input.getBand(i), output.getBand(i), kernel);
    }

    public static void fromColorToGray(Planar<GrayU8> input, Planar<GrayU8> outpout){
        double[] coef = {0.3, 0.59, 0.11};
        for (int y = 0; y < input.height; y++)
            for (int x = 0; x < input.width; x ++)
            {
                //0.3R+0.59G+0.11B.
                int ngl = 0;
                for (int i = 0; i < input.getNumBands(); ++i)
                {
                    int gl = input.getBand(i).get(x, y);
                    ngl += (int) (gl*coef[i]);
                    
                }
                outpout.getBand(0).set(x,y,ngl);
                outpout.getBand(1).set(x,y,ngl);
                outpout.getBand(2).set(x,y,ngl);
            }
    }

    static void rgbToHsv(int r, int g, int b, float[] hsv){
        double max = 0;
        double min = 255;
        double r2 = (double) r/255;
        double g2 = (double) g/255;
        double b2 = (double) b/255;
        double[] tab = {r2,g2,b2};

        for(int i = 0; i < 3; i++)
        {
            if(tab[i] > max){max = tab[i];}
            if(tab[i] < min){min = tab[i];}
        }

        double delta = max - min;

        if(max == min){hsv[0] = 0;}
        if(max == r2){hsv[0] = (float) (60 * (g2-b2)/(delta) +360) % 360;}
        if(max == g2){hsv[0] = (float) (60* (b2-r2)/(delta) + 120) % 360;}
        if(max == b2){hsv[0] = (float) (60* (r2-g2)/(delta) + 240) % 360;}

        if(max == 0){hsv[1] = 0;}
        if(max != 0){hsv[1] = (float) (delta/max) * 100;}

        hsv[2] = (float) max * 100;

        // for(int i = 0; i < 3; i++){System.out.println(hsv[i]);}
        // System.out.println("r:"+ r + " g:" + g + " b:" + b + " max:" + max + " min: "+ min + " delta:" + delta);
        // System.out.println("r2:"+ r2 + " g2:" + g2 + " b2:" + b2);
    }
    public static void hsvToRgb(float h, float s, float v, int[] rgb){
        s /= 100; 
        v/= 100;
        float c = v * s;
        float x = c * (1 - Math.abs((h/60)%2 -1));
        float m = v - c;
        float[] rgb2 = {0,0,0};
        if(h >= 0 && h < 60 ){rgb2[0] =  c; rgb2[1] =  x; rgb2[2] =  0;}
        if(h >= 60 && h < 120 ){rgb2[0] =  x; rgb2[1] =  c; rgb2[2] =  0;}
        if(h >= 120 && h < 180 ){rgb2[0] =  0; rgb2[1] =  c; rgb2[2] =  x;}
        if(h >= 180 && h < 240 ){rgb2[0] =  0; rgb2[1] =  x; rgb2[2] =  0;}
        if(h >= 240 && h < 300 ){rgb2[0] =  x; rgb2[1] =  0; rgb2[2] =  c;}
        if(h >= 300 && h < 360 ){rgb2[0] =  c; rgb2[1] =  0; rgb2[2] =  x;}
        for(int i = 0; i < 3; i++)
        {
            rgb2[i] = (rgb2[i] + m) * 255;
        }
        for(int i = 0; i < 3; i++)
        {
            rgb[i] = (int) Math.round(rgb2[i]);
            //if we don't use Math.round it is rounding under and it is not the same result
        }
        // System.out.println("R =" + rgb[0] + " | " + "G =" + rgb[1] + " | " + "B =" + rgb[2]);
        // System.out.println("R' =" + rgb2[0] + " | " + "G' =" + rgb2[1] + " | " + "B' =" + rgb2[2]);
        // System.out.println("C =" + c);
        // System.out.println("X =" + x);
        // System.out.println("M =" + m);
        // System.out.println("H =" + h + " | " + "S =" + s + " | " + "V =" + v);
    }

    public static void changeHue(Planar<GrayU8> input, Planar<GrayU8> output, int h)
    {
        int[] rgb = new int[3];
        float[] hsv = new float[3];
        for (int y = 0; y < input.height; y++)
            for (int x = 0; x < input.width; x ++)
            {
                for (int i = 0; i < input.getNumBands(); ++i)
                    rgb[i] = input.getBand(i).get(x, y);
                rgbToHsv(rgb[0], rgb[1], rgb[2], hsv);
                hsv[0] = h;
                hsvToRgb(hsv[0], hsv[1], hsv[2], rgb);
                for(int i = 0; i < input.getNumBands(); i++)
                    output.getBand(i).set(x,y,rgb[i]);
            }
    }

    public static void colordyn(Planar<GrayU8> input, Planar<GrayU8> output){
        fromColorToGray(input, output);
        int[] hist = GrayLevelProcessing.histogram(input.getBand(0));
        int[] histc = GrayLevelProcessing.histogramcumule(hist);
        for (int i = 0; i < input.getNumBands(); ++i)
            {
                for (int y = 0; y < input.height; ++y) 
                    for (int x = 0; x < input.width; ++x)
                    {
                        int gl = input.getBand(i).get(x, y);
                        output.getBand(i).set(x, y, histc[gl]*255/(input.height*input.width));
                    }
            }
            
    }

    public static int[][] vHistogram(Planar<GrayU8> input)
    {
        int[] rgb = new int[3];
        int[][] hist = new int[3][256];
		for (int y = 0; y < input.height; ++y) 
			for (int x = 0; x < input.width; ++x)
			{
                for(int i = 0; i < input.getNumBands(); i ++)
                {
                    rgb[i]=input.getBand(i).get(x, y);
                    hist[i][rgb[i]]++;
                }
			}
		return hist;
    }

    public static int[][] vHistogramc(int[][] hist)
    {
        int[][] histc = new int[3][256];
        for(int i = 0; i < 3; i++)
        {
            histc[i][0] = hist[i][0];
            for(int j = 1; j < 256; j++)
            {
                histc[i][j] = histc[i][j-1] + hist[i][j];
            }
        }
        return histc;
    }

    public static void colorcontrast(Planar<GrayU8> input, Planar<GrayU8> output){
        int[] rgb = new int[3];
        float[] hsv = new float[3];
        int[][] histc = vHistogramc(vHistogram(input));
        for(int x = 0; x < input.width; x++)
            for(int y = 0; y < input.height; y++)
            {
                for(int b = 0; b < input.getNumBands(); b++)
                    rgb[b] = histc[b][rgb[b]]*255/(input.height*input.width);
                rgbToHsv(rgb[0], rgb[1], rgb[2], hsv);
                hsv[2] = hsv[2]*255/100;
                hsvToRgb(hsv[0], hsv[1], hsv[2], rgb);
                for(int i = 0; i < input.getNumBands(); i++)
                    output.getBand(i).set(x,y,rgb[i]);
            }
    }


    public static void main(String[] args){
        if (args.length < 2) {
            System.out.println("missing input or output image filename");
            System.exit(-1);
        }
        final String inputPath = args[0];
        // GrayU8 input = UtilImageIO.loadImage(inputPath, GrayU8.class);
        BufferedImage input = UtilImageIO.loadImage(inputPath);
        Planar<GrayU8> imagein = ConvertBufferedImage.convertFromPlanar(input, null, true, GrayU8.class);
        Planar<GrayU8> imageout = ConvertBufferedImage.convertFromPlanar(input, null, true, GrayU8.class);

        if (input == null) {
            System.err.println("Cannot read input file '" + inputPath);
            System.exit(-1);
        }
        

        // color_luminosity(image, 30);
        // int[][] kaiser = {{1,2,3,2,1},{2,6,8,6,2},{3,8,10,8,3},{2,6,8,6,2},{1,2,3,2,1}};
        // color_convolution(imagein, imageout, kaiser);
        // fromColorToGray(imagein, imageout);
        // float[] hsv = {0,0,0};
        // rgbToHsv(71, 41, 77, hsv);
        // int[] rgb = {0,0,0};
        // hsvToRgb(290, 0.47f, 0.30f, rgb);
        // changeHue(imagein, imageout, 70);
        colorcontrast(imagein, imageout);


        final String outputPath = args[1];
        UtilImageIO.saveImage(imageout, outputPath);
        System.out.println("Image saved in: " + outputPath);
        
    }
}