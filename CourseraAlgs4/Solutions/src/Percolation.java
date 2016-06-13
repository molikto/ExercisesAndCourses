
public class Percolation {
    private WeightedQuickUnionUF wqu;
    private boolean[][] map;
    private int N;
    public Percolation(int N) {
        wqu = new WeightedQuickUnionUF(N*N*2+2);
        map = new boolean[N][N];
        this.N = N;
    }
    public void open(int ii, int jj) {
        int i = ii - 1;
        int j = jj - 1;
        if (i < 0 || i >= N || j < 0 || j >= N)
            throw new IndexOutOfBoundsException();
        int k = 2 * N - i - 1;
        map[i][j] = true;
        int currentIndex = i * N + j + 1;
        int currentKndex = k * N + j + 1;
        if (i == 0) {
            wqu.union(0, currentIndex);
            wqu.union(2*N*N+1, currentKndex);
        } else if (map[i-1][j]) {
            wqu.union(currentIndex, (i-1) * N + j + 1);
            wqu.union(currentKndex, (k+1) * N + j + 1);
        }
        if (i == N-1) {
            wqu.union(currentKndex, currentIndex);
        } else if (map[i+1][j]) {
                wqu.union(currentIndex, (i+1) * N + j + 1);
                wqu.union(currentKndex, (k-1) * N + j + 1);
        }
        if (j > 0 && map[i][j-1]) {
            wqu.union(currentIndex, i * N + j);
            wqu.union(currentKndex, k * N + j);
        }
        if (j < N-1 && map[i][j+1]) {
            wqu.union(currentIndex, i * N + j + 2);
            wqu.union(currentKndex, k * N + j + 2);
        }
    }
    public boolean isOpen(int ii, int jj) {
        int i = ii - 1;
        int j = jj - 1;
        if (i < 0 || i >= N || j < 0 || j >= N)
            throw new IndexOutOfBoundsException();
        return map[i][j];
    }
    public boolean isFull(int ii, int jj) {
        int i = ii - 1;
        int j = jj - 1;
        if (i < 0 || i >= N || j < 0 || j >= N)
            throw new IndexOutOfBoundsException();
        return wqu.connected(i * N + j + 1, 0);
    }
    public boolean percolates() {
        return wqu.connected(N*N*2+1, 0);
    }
}