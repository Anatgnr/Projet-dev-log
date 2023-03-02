package imageprocessing;

import boofcv.abst.filter.derivative.ImageGradient_SB.Sobel;
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
    if(borderType == BorderType.SKIP){meanFilterSimple(input,output,size);return;}
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
            if(x + tmpi< 0){tmpi = -i;}
            if(x + i > input.width -1){tmpi = (input.width -1) - (x+i);}
            if(y + tmpj < 0){tmpj = -j;}
            if(y + j > input.height -1){tmpj = (input.height - 1) - (y+j);}
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
    int size = kernel.length;
    int total = 0;
    for(int i = 0; i < size; i++)
      for(int j = 0; j<size; j++)
        total += kernel[i][j];
    for (int y = size/2; y < input.height - size/2; ++y)
			for (int x = size/2; x < input.width - size/2; ++x)
        {
          int sum = 0;
          for(int i = - size/2; i <= size/2; i++)
            for(int j = - size/2; j <= size/2; j++)
              sum += input.get(x+i,y+j)*kernel[i+(size/2)][j+(size/2)]; 
          output.set(x,y,sum/(total)); 
        }
  }

  public static void gradientImageSobel(GrayU8 input, GrayU8 output){
    int fullsize = input.width * input.height;
    int[] h1 =  new int [fullsize];
    int[] h2 =  new int [fullsize];
    int size = 3;
    int[] kaiser1 = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
    int[] kaiser2 = {-1, -2, -1, 0, 0, 0, 1, 2, 1};
    for (int y = size/2; y < input.height - size/2; ++y)
			for (int x = size/2; x < input.width - size/2; ++x)
        {
          int cpt=-1;
          int sum = 0;
          for(int i = - size/2; i <= size/2; i++)
            for(int j = - size/2; j <= size/2; j++)
              sum += input.get(x+i,y+j)*kaiser1[++cpt]; 
              h1[x+y*input.width] = sum; 
        }   
    for (int y = size/2; y < input.height - size/2; ++y)
			for (int x = size/2; x < input.width - size/2; ++x)
        {
          int cpt=-1;
          int sum = 0;
          for(int i = - size/2; i <= size/2; i++)
            for(int j = - size/2; j <= size/2; j++)
              sum += input.get(x+i,y+j)*kaiser2[++cpt]; 
          h2[x+y*input.width] = sum;
        }
    for(int i = 0; i< fullsize; i++)
      output.set(i%input.width,i/input.width, (int) Math.sqrt((h1[i]*h1[i])+(h2[i]*h2[i])));
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

    int[][] kaiser = {{1,2,3,2,1},{2,6,8,6,2},{3,8,10,8,3},{2,6,8,6,2},{1,2,3,2,1}};
    // int[] kaiser2 = {1,2,3,2,1,2,6,8,6,2,3,8,10,8,3,2,6,8,6,2,1,2,3,2,1};
    // Kernel2D_S32 kernel = new Kernel2D_S32 (5,kaiser2);
    Kernel2D_S32 kernel = new Kernel2D_S32(5, new int[] { 1, 2, 3, 2, 1, 2, 6, 8, 6, 2, 3, 8, 10, 8, 3, 2, 6, 8, 6, 2, 1, 2, 3, 2, 1 });

    //processing

    // meanFilterWithBorders(input, output, 11, BorderType.EXTENDED); 
    // meanFilterSimple(input, output, 9);
    // GConvolveImageOps.convolveNormalized(kernel, input, output); 
    // convolution(input, output, kaiser);
    long begin = System.nanoTime();
    for(int i = 0; i < 1000; i++)
      gradientImageSobel(input, output);
		long end = System.nanoTime();
		long duration = (end - begin);
    System.out.println( "Temps d'éxécution :" + duration / 1e9 );


    // Kernel2D_S32 kernel = new Kernel2D_S32(5, new int[] { 1, 2, 3, 2, 1, 2, 6, 8, 6, 2, 3, 8, 10, 8, 3, 2, 6, 8, 6, 2, 1, 2, 3, 2, 1 });
    // gradientImageSobel(input, output);

    // save output image
    final String outputPath = args[1];
    UtilImageIO.saveImage(output, outputPath);
    System.out.println("Image saved in: " + outputPath);
  }

}