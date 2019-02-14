
/**
 * PercolationStatTester
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStatTester {

  public static void main(String[] args) {

    for (int i = 0; i < 2; i++) {

      Stopwatch stopwatch = new Stopwatch();
      PercolationStats percStats = new PercolationStats(10 * (i + 1), 1000);
      double percStatTime = stopwatch.elapsedTime();

      StdOut.println("mean\t\t\t\t= " + percStats.mean());
      StdOut.println("stddev\t\t\t\t= " + percStats.stddev());
      StdOut
          .println("95% confidence interval\t\t= [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");

      StdOut.println("It took " + percStatTime + " seconds.");
    }

  }
}