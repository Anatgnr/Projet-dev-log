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
    // int taille = 2*size+1;
    // int[][] K = new int[taille][taille];
    for (int y = size/2; y < input.height - size/2; ++y)
			for (int x = size/2; x < input.width - size/2; ++x)
        {
          int sum = 0;
          for(int i = - size/2; i <= size/2; i++)
            for(int j = - size/2; j <= size/2; j++)
              sum += input.get(x+i,y+j);
          output.set(x,y,sum/((size*size))); 
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

    meanFilterSimple(input, output, 11);

    // save output image
    final String outputPath = args[1];
    UtilImageIO.saveImage(output, outputPath);
    System.out.println("Image saved in: " + outputPath);
  }

}