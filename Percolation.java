import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    WeightedQuickUnionUF uf;
    boolean[] fieldMap;
    int size;
    int openCount = 0;
    int virtualTopIndex;
    int virtualBottomIndex;

    // constructor
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        // 2 additional array members to store virtual top and bottom
        uf = new WeightedQuickUnionUF((n * n) + 2);
        // connect top row to virtual top, which is uf[n*n]
        virtualTopIndex = n * n;
        for (int i = 0; i < n; i++) {
            StdOut.println("Connecting top row memeber to virtual top: " + i);
            uf.union(i, virtualTopIndex);
        }
        virtualBottomIndex = n * n + 1;
        // connect bottom row to virtual bottom, which is uf[n*n+1]
        for (int i = n * n - 1; i > n * n - 1 - n; i--) {
            StdOut.println("Connecting bottom row memeber to virtual bottom: " + i);
            uf.union(i, virtualBottomIndex);
        }
        fieldMap = new boolean[n * n];
        StdOut.println("Created field with size: " + size);
        // StdOut.println(uf.count());
    }

    private int getFieldIndex(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        return (row - 1) * size + (col - 1);
    }

    private int[] generateAdj(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        // int myIndex = getFieldIndex(row, col);
        // no top
        int bottom = (row != size) ? getFieldIndex(row + 1, col) : -1;
        int top = (row != 1) ? getFieldIndex(row - 1, col) : -1;
        int left = (col != 1) ? getFieldIndex(row, col - 1) : -1;
        int right = (col != size) ? getFieldIndex(row, col + 1) : -1;
        int[] allIndices = { top, bottom, left, right };
        int[] filteredIndices = Arrays.stream(allIndices).filter(x -> x > -1).toArray();
        // StdOut.println(Arrays.toString(allIndices));
        // StdOut.println("Neighbors are: " + Arrays.toString(filteredIndices));
        return filteredIndices;
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            StdOut.println("Already Open!");
            return;
        }
        // code to connect items
        int mappedI = getFieldIndex(row, col);
        StdOut.println("Opening: " + mappedI);
        fieldMap[mappedI] = true;
        openCount++;
        int[] neighbors = generateAdj(row, col);
        for (int i = 0; i < neighbors.length; i++) {
            if (fieldMap[neighbors[i]] && !uf.connected(mappedI, neighbors[i])) {
                StdOut.println("Unioning " + neighbors[i] + " and " + mappedI);
                uf.union(mappedI, neighbors[i]);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        return fieldMap[getFieldIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }
        int mappedI = getFieldIndex(row, col);
        return uf.connected(virtualTopIndex, mappedI) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.connected(virtualTopIndex, virtualBottomIndex);
        // return false;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        // n = 10;
        Percolation perc = new Percolation(n);
        // assumes rows and cols start a 1
        perc.open(1, 1);
    }
}
