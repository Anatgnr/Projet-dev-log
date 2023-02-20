package imageprocessing;

import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.border.BorderType;
import boofcv.struct.convolve.Kernel1D_S32;
import boofcv.struct.convolve.Kernel2D_S32;
import boofcv.struct.image.GrayU8;


//Stones 640 * 426

public class Convolution {

  public static void meanFilterSimple(GrayU8 input, GrayU8 output, int size) {
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
    for(int y = 0; y<input.height; y++)
      for(int x = 0; x<input.width; x ++)
      switch(borderType){
      case NORMALIZED:
        {
          int sum = 0;
          int xmin = x-size/2;
          int xmax = x+size/2;
          int ymin = y - size/2;
          int ymax = y + size/2;
          while(xmax >= input.width){xmax --;}
          while(xmin < 0){xmin ++;}
          while(ymin < 0){ymin ++;}
          while(ymax >= input.height){ymax --;}
          for(int i = ymin; i <= ymax; i++)
            for(int j = xmin; j <= xmax; j++)
              sum += input.get(j,i);
          output.set(x,y,sum/((xmax - xmin +1)*(ymax - ymin +1))); 
        }
        break;
      case EXTENDED:
      {
        int sum = 0;
        for(int i = -size/2; i <= size/2; i++)
          for(int j = -size/2; j <= size/2; j++)
          {
            int tmpi = i;
            int tmpj = j;
            if(i < 0 || x + i >= input.width -1){tmpi = 0;}
            if(j<0 || y + j >= input.height -1){tmpj = 0;}
            sum += input.get(x + tmpi,y + tmpj);
          }
          output.set(x,y,sum/(size*size));
      }
        break;
      case REFLECT:
      {
        int sum = 0;
        for(int i = -size/2; i <= size/2; i++)
          for(int j = -size/2; j <= size/2; j++)
          {
            int tmpi = i;
            int tmpj = j;
            if(i< 0){tmpi = (-1)*(i);}
            if(x + i == input.width -1){tmpi = 0;}
            if(x + i > input.width -1){tmpi = (input.width -1) - i;}
            
            if(j < 0){tmpj = (-1)*j;}
            // if(y + j > input.height -1){tmpj = (-1)*(j - (input.height - 1) );}
            System.out.println("x:" + x + " tmpi:" + tmpi);
            sum += input.get(x + tmpi,y + tmpj);
          }
          output.set(x, y, sum/(size*size));
      }
        break;
      default:
          break;
      }
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

    // meanFilterSimple(input, output, 3);
    meanFilterWithBorders(input, output, 11, BorderType.REFLECT);

    // save output image
    final String outputPath = args[1];
    UtilImageIO.saveImage(output, outputPath);
    System.out.println("Image saved in: " + outputPath);
  }

}