import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Polynomial {
  private double[] coefficients;
  private int[] exponents;
  private int length;

  public Polynomial() {
    this.coefficients = new double[] {};
    this.exponents = new int[] {};
    this.length = 0;
  }

  public Polynomial(double[] coeff, int[] exp) {
    if (coeff.length != exp.length) {
      throw new IllegalArgumentException(
          "Coefficients and exponents arrays must have the same length.");
    }
    this.coefficients = coeff.clone();
    this.exponents = exp.clone();
    this.length = coeff.length;
  }

  public Polynomial(File f) {
    try {
      Scanner scanner = new Scanner(f);
      if (scanner.hasNextLine()) {
        ArrayList<Double> coeffList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();
        String line = scanner.nextLine();

        line = line.replaceAll("\\-", "+-");
        String[] terms = line.split("\\+");

        for (String x : terms) {
          String s[] = x.split("[a-zA-Z]");
          coeffList.add(Double.parseDouble(s[0]));
          if (s.length == 1) {
            expList.add(0);
          } else {
            expList.add(Integer.parseInt(s[1]));
          }
        }

        this.coefficients = coeffList.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = expList.stream().mapToInt(Integer::intValue).toArray();
        this.length = coeffList.size();
      } else {
        this.coefficients = new double[] {};
        this.exponents = new int[] {};
        this.length = 0;
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (NumberFormatException e) {
      throw new RuntimeException(e);
    }
  }

  public Polynomial add(Polynomial p) {
    TreeMap<Integer, Double> map = new TreeMap<>();

    for (int i = 0; i < this.length; i++) {
      map.merge(this.exponents[i], this.coefficients[i], Double::sum);
    }
    for (int i = 0; i < p.length; i++) {
      map.merge(p.exponents[i], p.coefficients[i], Double::sum);
    }

    map.values().removeIf(v -> v == 0.0);

    int[] newExp = map.keySet().stream().mapToInt(Integer::intValue).toArray();
    double[] newCoeff = map.values().stream().mapToDouble(Double::doubleValue).toArray();

    return new Polynomial(newCoeff, newExp);
  }

  public double evaluate(double x) {
    double result = 0;

    for (int i = 0; i < this.length; i++) {
      result += coefficients[i] * Math.pow(x, exponents[i]);
    }

    return result;
  }

  public boolean hasRoot(double x) {
    return this.evaluate(x) == 0;
  }

  private Polynomial multiply(double coeff, int exp) {
    double[] newCoeff = new double[this.length];
    int[] newExp = new int[this.length];

    for (int i = 0; i < this.length; i++) {
      newCoeff[i] = this.coefficients[i] * coeff;
      newExp[i] = this.exponents[i] + exp;
    }

    return new Polynomial(newCoeff, newExp);
  }

  public Polynomial multiply(Polynomial p) {
    Polynomial result = new Polynomial();

    for (int i = 0; i < p.length; i++) {
      Polynomial temp = this.multiply(p.coefficients[i], p.exponents[i]);
      result = result.add(temp);
    }

    return result;
  }

  public String serialize() {
    if (length == 0) return "0";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      double coeff = coefficients[i];
      int exp = exponents[i];
      if (coeff == 0) continue;
      if (i > 0 && coeff > 0) sb.append("+");
      sb.append(coeff);
      if (exp != 0) {
        sb.append("x");
        if (exp != 1) sb.append(exp);
      }
    }
    return sb.toString();
  }

  public void saveToFile(String name) {
    try {
      FileWriter writer = new FileWriter(name);
      writer.write(serialize());
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
