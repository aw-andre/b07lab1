public class Driver {
  public static void main(String[] args) {
    // default constructor
    Polynomial p1 = new Polynomial();
    System.out.println("Empty polynomial: " + p1.serialize());

    // array constructor
    double[] coeffs = {5, -3, 7};
    int[] exps = {0, 2, 8};
    Polynomial p2 = new Polynomial(coeffs, exps);
    System.out.println("Array polynomial: " + p2.serialize());

    // file constructor
    try {
      java.io.File tempFile = java.io.File.createTempFile("fileconstructor", ".txt");
      java.io.FileWriter writer = new java.io.FileWriter(tempFile);
      writer.write("5-3x2+7x8");
      writer.close();
      Polynomial p3 = new Polynomial(tempFile);
      System.out.println("File polynomial: " + p3.serialize());
      tempFile.delete();
    } catch (java.io.IOException e) {
      System.out.println("File test failed: " + e.getMessage());
    }

    // add
    Polynomial p4 = new Polynomial(new double[] {1, 2}, new int[] {1, 3});
    Polynomial p5 = new Polynomial(new double[] {-1, 3}, new int[] {1, 2});
    Polynomial sum = p4.add(p5);
    System.out.println("Add result: " + sum.serialize());

    // evaluate
    double val = p2.evaluate(2);
    System.out.println("Evaluate p2 at 2: " + val);

    // hasRoot
    boolean root = p2.hasRoot(1);
    System.out.println("p2 has root at 1: " + root);

    // multiply
    Polynomial prod = p4.multiply(p5);
    System.out.println("Multiply result: " + prod.serialize());

    // saveToFile
    try {
      p2.saveToFile("savetofile.txt");
      System.out.println("Saved to savetofile.txt");
    } catch (Exception e) {
      System.out.println("Save failed: " + e.getMessage());
    }

    // edge cases
    Polynomial zeroCoeff = new Polynomial(new double[] {0, 5}, new int[] {1, 2});
    System.out.println("Zero coeff removed: " + zeroCoeff.serialize());

    Polynomial singleTerm = new Polynomial(new double[] {4}, new int[] {5});
    System.out.println("Single term: " + singleTerm.serialize());

    Polynomial constant = new Polynomial(new double[] {10}, new int[] {0});
    System.out.println("Constant: " + constant.serialize());
  }
}
