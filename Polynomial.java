public class Polynomial {
  private double[] coefficients;
  private int length;

  public Polynomial() {
    this.coefficients = new double[] {0};
    this.length = 1;
  }

  public Polynomial(double[] args) {
    this.coefficients = args.clone();
    this.length = args.length;
  }

  private double get(int i) {
    if (i >= this.length) {
      return 0;
    }

    return this.coefficients[i];
  }

  public Polynomial add(Polynomial p) {
    int longest = Math.max(this.length, p.length);
    double[] result = new double[longest];

    for (int i = 0; i < longest; i++) {
      result[i] = this.get(i) + p.get(i);
    }

    return new Polynomial(result);
  }

  public double evaluate(double x) {
    double result = 0;

    for (int i = 0; i < this.length; i++) {
      result += this.get(i) * Math.pow(x, i);
    }

    return result;
  }

  public boolean hasRoot(double x) {
    return this.evaluate(x) == 0;
  }
}
