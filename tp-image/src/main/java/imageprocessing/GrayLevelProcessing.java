package imageprocessing;

import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class GrayLevelProcessing {

	public static void threshold(GrayU8 input, int t) {
		// parcours ligne par ligne
		for (int y = 0; y < input.height; ++y) {
			for (int x = 0; x < input.width; ++x) {
				int gl = input.get(x, y);
				// on vérifie que la valeur gl (le pixel à l'emplacement (x,y)) soit inferieur
				// au treshold
				if (gl < t) {
					// si gl inferieur gl recoit noir
					gl = 0;
				} else {
					// blanc sinon
					gl = 255;
				}
				// set la nouvelle valeur de gl
				input.set(x, y, gl);
			}
		}
	}

	public static void luminosity(GrayU8 input, int l) {
		// on va ajouté l à chaque pixel de l'image pour modifier la luminosité
		for (int y = 0; y < input.height; ++y)
			for (int x = 0; x < input.width; ++x) {
				// on trouve le pixel
				int gl = input.get(x, y);
				// on lui ajoute l
				if (gl + l > 255) {
					gl = 255;
				} else if (gl + l < 0) {
					gl = 0;
				} else {
					gl += l;
				}
				// on le reinjecte dans la matrice de pixel
				input.set(x, y, gl);
			}
	}

	public static void dynamique(GrayU8 input){
		int min = 255; int max = 0;
		for (int y = 0; y < input.height; ++y) 
            for (int x = 0; x < input.width; ++x) 
				{
					int gl = input.get(x, y);
                    if(gl>max)
                        max = gl;
                    if(gl<min)
                        min = gl;
				}

		for (int y = 0; y < input.height; ++y) {
            for (int x = 0; x < input.width; ++x) {
				int gl = input.get(x, y);
				gl = (255/(max-min))*(gl-min);
				if(gl>255)
					gl = 255;
				if(gl<0)
                    gl = 0;
                input.set(x, y, gl);
            }
        }
	}

	public static void dynamique2(GrayU8 input,int Vmax, int Vmin){
		int Fmin = 255; int Fmax = 0;
		int sub;
		for (int y = 0; y < input.height; ++y) 
            for (int x = 0; x < input.width; ++x) 
				{
					int gl = input.get(x, y);
                    if(gl>Fmax)
                        Fmax = gl;
                    if(gl<Fmin)
                        Fmin = gl;
				}
		sub = Fmax-Fmin;
		if(Fmax == Fmin)
			sub = 1;
		for (int y = 0; y < input.height; ++y) {
            for (int x = 0; x < input.width; ++x) {
				int gl = input.get(x, y);
				gl = (gl - Fmin)*((Vmax-Vmin)/(sub)) + Vmin;
				if(gl>255)
					gl = 255;
				if(gl<0)
                    gl = 0;
                input.set(x, y, gl);
            }
        }
	}

	public static void dynamique3(GrayU8 input){
		int Fmin = 255; int Fmax = 0;
		for (int y = 0; y < input.height; ++y) 
            for (int x = 0; x < input.width; ++x) 
				{
					int gl = input.get(x, y);
                    if(gl>Fmax)
                        Fmax = gl;
                    if(gl<Fmin)
                        Fmin = gl;
				}
		int[] LUT = new int[256];
		for(int i = 0; i<256; i++)
			LUT[i] = (255 * (i - Fmin)/(Fmax - Fmin));
		for (int y = 0; y < input.height; ++y) 
        	for (int x = 0; x < input.width; ++x)
				input.set(x, y,LUT[input.get(x,y)]); 
	}

	// public static void histogram(GrayU8 input){
	// 	//initialisation de hist
	// 	int []hist = new int[256];
	// 	for(int i = 0; i<256; i++)
	// 		hist[i]=0;
	// 	for (int y = 0; y < input.height; ++y) 
    //         for (int x = 0; x < input.width; ++x)
	// 		{
	// 			int gl=input.get(x, y);
	// 			hist[gl]++;
	// 		}
	// 	int cumule = 0;
	// 	for(int i = 0; i<256; i++)
	// 		cumule += hist[i];
	// 	for(int i = 0; i<256; i++)
	// 		hist[i] = (truc * 255)/(input.width * input.height);
	// }

	

	public static void main(String[] args) {

		// load image
		if (args.length < 2) {
			System.out.println("missing input or output image filename");
			System.exit(-1);
		}
		final String inputPath = args[0];
		GrayU8 input = UtilImageIO.loadImage(inputPath, GrayU8.class);
		if (input == null) {
			System.err.println("Cannot read input file '" + inputPath);
			System.exit(-1);
		}

		// processing

		// threshold(input, 128);
		// luminosity(input, 100);
		// dynamique(input);
		// dynamique3(input);

		//time test
		for(int i = 0; i<1000; i++)
			dynamique2(input,190,10);

		// save output image
		final String outputPath = args[1];
		UtilImageIO.saveImage(input, outputPath);
		System.out.println("Image saved in: " + outputPath);
	}

}