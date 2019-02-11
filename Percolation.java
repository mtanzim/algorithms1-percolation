import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

import java.util.Arrays;

public class Percolation {
    UF uf;
    boolean[] fieldMap;
    int size;
    int openCount = 0;

    // constructor
    public Percolation(int n) {
        size = n;
        // 2 additional array members to store virtual top and bottom
        uf = new UF((n * n) + 2);
        // connect top row to virtual top, which is uf[n*n]
        for (int i = 0; i < n; i++) {
            StdOut.println("Connecting top row memeber to virtual top: " + i);
            uf.union(i, n * n);
        }
        // connect bottom row to virtual bottom, which is uf[n*n+1]
        for (int i = n * n - 1; i > n * n - 1 - n; i--) {
            StdOut.println("Connecting bottom row memeber to virtual top: " + i);
            uf.union(i, n * n + 1);
        }
        fieldMap = new boolean[n * n];
        StdOut.println("Created field with size: " + size);
        // StdOut.println(uf.count());
    }

    private int getFieldIndex(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    private int[] generateAdj(int row, int col) {
        // int myIndex = getFieldIndex(row, col);
        // no top
        int topIndex = getFieldIndex(row + 1, col);
        int bottomIndex = getFieldIndex(row - 1, col);
        int leftIndex = getFieldIndex(row, col - 1);
        int rightIndex = getFieldIndex(row, col + 1);
        int[] allIndices = { topIndex, bottomIndex, leftIndex, rightIndex };
        // int[] filteredIndices = Arrays.stream(allIndices).filter(x -> x > -1 && x < size * size)
        //                               .toArray();
        StdOut.println(Arrays.toString(allIndices));
        // StdOut.println(Arrays.toString(filteredIndices));
        return allIndices;
    }


    public void open(int row, int col) {
        // code to connect items
        int mappedI = getFieldIndex(row, col);
        StdOut.println("Opening: " + mappedI);
        fieldMap[mappedI] = true;
        openCount++;
        generateAdj(row, col);
    }

    public boolean isOpen(int row, int col) {
        return fieldMap[getFieldIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        return false;
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return false;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation perc = new Percolation(n);
        // assumes rows and cols start a 1
        perc.open(1, 1);
    }
}
