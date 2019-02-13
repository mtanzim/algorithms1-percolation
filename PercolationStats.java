
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;;

/* *****************************************************************************
 *  Name: PercolationStates
 *  Date: Feb 12, 2018
 *  Description: Class to test Percolation.java using the Monte Carlo
 *  simulation method.
 **************************************************************************** */

public class PercolationStats {

    Percolation perc;
    int size;
    int trials;
    int[] shuffledIndices;
    double[] resultRatios;

    double mean;
    double stddev;
    double confHi;
    double confLo;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        // trials = trials;
        shuffledIndices = new int[size * size];
        resultRatios = new double[trials];

        for (int j = 0; j < size * size; j++) {
            // StdOut.println(j);
            shuffledIndices[j] = j;
        }

        /*
         * for (int j = 0; j < size * size; j++) { StdOut.println(shuffledIndices[j]);
         * shuffledIndices[j] = j; }
         */

        // StdOut.println("Now we here!!! with n: " + n + " trials: " + trials);

        for (int i = 0; i < trials; i++) {
            StdRandom.shuffle(shuffledIndices);
            perc = new Percolation(n);
            // while (!perc.percolates()) {
            for (int j = 0; j < size * size; j++) {
                int[] curIndexArr = invertFieldIndex(shuffledIndices[j]);
                int curRow = curIndexArr[0];
                int curCol = curIndexArr[1];

                // StdOut.println("Opening i:" + shuffledIndices[j] + ",row:" + curRow + ",col:"
                // + curCol);
                perc.open(curRow, curCol);
                if (perc.percolates()) {
                    break;
                }
            }
            // }
            // StdOut.println("Took this many opens: " + perc.openCount);
            // StdOut.println("i:"+ i + ",Ratio: " + ((float)perc.openCount/(size*size)));
            resultRatios[i] = ((double) perc.openCount / (size * size));
        }

        mean = StdStats.mean(resultRatios);
        stddev = StdStats.stddev(resultRatios);
        confLo = mean - ((1.96 * stddev) / Math.sqrt(trials));
        confHi = mean + ((1.96 * stddev) / Math.sqrt(trials));
    }

    private int[] invertFieldIndex(int index) {
        if (index < 0) {
            throw new IllegalArgumentException();
        }

        int row = ((index + 1) / size) + 1;
        int col = ((index + 1) % size);

        if (col == 0) {
            row--;
            col = size;
        }

        // StdOut.println("index: " + index);
        // StdOut.println("row: " + row);
        // StdOut.println("col: " + col);

        int[] retArr = { row, col };

        return retArr;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confLo;

    }

    public double confidenceHi() {
        return confHi;

    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean\t\t\t\t= " + percStats.mean());
        StdOut.println("stddev\t\t\t\t= " + percStats.stddev());
        StdOut.println(
                "95% confidence interval\t\t= [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
        // StdOut.println("confHi: " + percStats.confidenceHi());

    }
}
