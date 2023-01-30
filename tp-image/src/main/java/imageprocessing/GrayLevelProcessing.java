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
		luminosity(input, 100);

		// save output image
		final String outputPath = args[1];
		UtilImageIO.saveImage(input, outputPath);
		System.out.println("Image saved in: " + outputPath);
	}

}