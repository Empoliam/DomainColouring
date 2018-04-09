import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;

import javax.imageio.ImageIO;

public class Main {

	static public double XMIN = -3.0;
	static public double XMAX = 3.0;
	static public double YMIN = -3.0;
	static public double YMAX = 3.0;

	//Must be whole numbers
	static public double XRES = 1000;
	static public double YRES = 1000;

	static boolean ZGRID = false;
	static int ZGRIDDIV = 1;

	static boolean WGRID = true;
	static double WGRIDDIV = 1.0;
	static double WGRIDTOLERANCE = 0.01;

	static BufferedImage output = new BufferedImage((int)XRES,(int)YRES,BufferedImage.TYPE_INT_RGB);

	static final Complex ONE = new Complex(1,0);
	static final Complex I = new Complex(0,1);

	public static void main(String[] args) {

		double xbase = (XMAX - XMIN) / XRES;
		double ybase = (YMAX - YMIN) / YRES;

		double originx = XRES / 2;
		double originy = YRES / 2;

		for (int frame = 0; frame < 90; frame++) {

			for (int x = 0; x < XRES; x++) {

				for (int y = 0; y < YRES; y++) {

					Complex Z = new Complex((x - originx) * (double) xbase, (y - originy) * (double) ybase);

					Complex J = Z.add(new Complex(0.0+(frame/90.0), 0));
					Complex K = Z.add(new Complex(0.0-(frame/90.0), 0));

					Complex W = J.multiply(K);

					double s = MoreMath.logAB(2.0, W.abs());
					if (s > 0)
						s = s % 1;
					else
						s = 1 - (Math.abs(s) % 1);

					s = s * 0.7 + 0.3;

					Color D = Color.getHSBColor((float) (W.twoPiArg() / (Math.PI * 2)), (float) s, (float) 1.0);

					output.setRGB(x, (int) YRES - 1 - y, D.getRGB());

					if (ZGRID) {
						if (Math.abs(Z.r) % ZGRIDDIV < 0.01)
							output.setRGB(x, (int) YRES - 1 - y, 0);
						else if (Math.abs(Z.i) % ZGRIDDIV < 0.01)
							output.setRGB(x, (int) YRES - 1 - y, 0);
					}

					if (WGRID) {
						double modr = Math.abs(W.r) % WGRIDDIV;
						double modi = Math.abs(W.i) % WGRIDDIV;
						if (modr < WGRIDTOLERANCE)
							output.setRGB(x, (int) YRES - 1 - y, 0);
						else if (modi < WGRIDTOLERANCE)
							output.setRGB(x, (int) YRES - 1 - y, 0);
					}

				}

			}
			File f = new File("output" + frame  + ".png");
			try {
				ImageIO.write(output, "PNG", f);
			} catch (IOException e) {
				System.out.println("Failed to print");
			};
			
		}

	}

}
