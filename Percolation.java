
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    // constructor
    public Percolation(int n) {
        UF uf = new UF(n);
        StdOut.println("Hello from the constructor!");
        StdOut.println(uf.count());
    }

    public void open(int row, int col) {
        // code to connect items
    }

    public boolean isOpen(int row, int col) {
        return false;
    }

    public boolean isFull(int row, int col) {
        return false;
    }

    public int numberOfOpenSites() {
        return 0;
    }

    public boolean percolates() {
        return false;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);
    }
}