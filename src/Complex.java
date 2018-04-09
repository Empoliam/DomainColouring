import java.lang.Math;

public class Complex {

	public double r;
	public double i;

	public Complex(double r, double i) {

		this.r = r;
		this.i = i;		
	}

	public Complex multiply(Complex zA) {

		Complex zB = new Complex(this.r, this.i);

		double a = zA.r * zB.r;
		double b = zA.r * zB.i;
		double c = zA.i * zB.r;
		double d = (zA.i * zB.i) * -1;

		zB.r = a + d;
		zB.i = b + c;

		return zB;	
	}

	public Complex add(Complex zA) {

		Complex zB = new Complex(this.r, this.i);

		zB.r = this.r + zA.r;
		zB.i = this.i + zA.i;

		return zB;

	}

	public Complex divide(Complex zA) {

		Complex zB = new Complex(0,0);

		double x = this.r;
		double y = this.i;
		double u = zA.r;
		double v = zA.i;

		double fraction = Math.pow(u,2) + Math.pow(v,2);

		zB.r = ((u*x) + (v*y))/fraction;
		zB.i = ((u*y)-(v*x))/fraction;

		return zB;

	}

	public double arg() {		

		return Math.atan2(i,r);

	}

	public double twoPiArg() {		

		double a = Math.atan2(i,r);
		if(a<0.0) a += 2*Math.PI;
		return a;
	}

	public double abs() {
		return Math.sqrt(Math.pow(r,2) + Math.pow(i,2));
	}

	/**
	 * Raises the complex number to the power a
	 *
	 * @param a index
	 * @return Complex number
	 */
	public Complex pow(double a) {

		Complex E = new Complex(0,0);
		Complex Z = this;

		double mod = Math.pow(Math.sqrt(Math.pow(this.r, 2) + Math.pow(this.i,2)),a);

		E.r = mod * Math.cos(a * Z.arg());
		E.i = mod * Math.sin(a * Z.arg());

		return E;
	}

}
