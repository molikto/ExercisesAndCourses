
public class PercolationStats {
    
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    
    
    // Why only use map not work?? Way finding is not good
    // for down-up-down way...
    
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        run(N, T);
    }
    private void run(int N, int T) {
        double[] results = new double[T];
        int i, j;
        for (int t = 0; t < T; t++) {
            Percolation pc = new Percolation(N);
            int opennum = 0;
            while (!pc.percolates()) {
                i = StdRandom.uniform(N)+1;
                j = StdRandom.uniform(N)+1;
                if (!pc.isOpen(i, j)) {
                    pc.open(i, j);
                    opennum++;
                }
            }
            results[t] = 1.0*opennum/(N*N);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        double var = 1.96*stddev/Math.sqrt(T);
        confidenceLo = mean - var;
        confidenceHi = mean + var;
    }
    
    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return confidenceLo;
    }
    public double confidenceHi() {
        return confidenceHi;
    }
  /*  public static void main(String[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException();
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0])
                , Integer.parseInt(args[1]));
        new Out().print("mean\t\t\t= "+ps.mean()
                +"\nstddev\t\t\t= "+ps.stddev()
                +"\n95% confidence interval\t= "+ps.confidenceLo()
                +", "+ps.confidenceHi());
    } */
}
