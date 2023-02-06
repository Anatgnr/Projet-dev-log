package imageprocessing;

import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.border.BorderType;
import boofcv.struct.convolve.Kernel1D_S32;
import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayU8;

public class Convolution {

  public static void meanFilterSimple(GrayU8 input, GrayU8 output, int size) {
    for(int y = 0; y < input.height; y++)
        for(int x = 0; x < input.width; x ++)
        {
            int r = 0;
            for(int u = 0; u < 2*size + 1; u++)
                for(int v = 0; v < 2*size + 1; v++)
                    r = r + input.get(x + u,y + v) * output.get(u+size,v+size);
        }
  }

  public static void meanFilterWithBorders(GrayU8 input, GrayU8 output, int size, BorderType borderType) {
    
  }
  
  public static void convolution(GrayU8 input, GrayU8 output, int[][] kernel) {
    
  }

  public static void main(final String[] args) {
    // load image
    if (args.length < 2) {
      System.out.println("missing input or output image filename");
      System.exit(-1);
    }
    final String inputPath = args[0];
    GrayU8 input = UtilImageIO.loadImage(inputPath, GrayU8.class);
    GrayU8 output = input.createSameShape();

    //processing

    meanFilterSimple(input, output, 3);

    // save output image
    final String outputPath = args[1];
    UtilImageIO.saveImage(output, outputPath);
    System.out.println("Image saved in: " + outputPath);
  }

}