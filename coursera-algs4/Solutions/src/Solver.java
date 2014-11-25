import java.util.Comparator;


public class Solver {

    private Node finalNode;
   private class Node {
      private Board bd;
      private Node parent;
      private int mc;
      private Node() { throw new IllegalArgumentException(); };
      private Node(Board bd, int move) {
         this.bd = bd;
         this.mc = move;
      }
      public Node(Board bd, Node mn) {
         this.bd = bd;
         this.parent = mn;
         this.mc = mn.mc+1;
      }
   }
    public Solver(Board initial) {
       Comparator<Node> compar = new Comparator<Node>() {
         @Override
         public int compare(Node b1, Node b2) {
            return b1.bd.manhattan()+b1.mc-b2.bd.manhattan()-b2.mc;
         }
       };
       MinPQ<Node> pq = new MinPQ<Node>(compar);
       MinPQ<Node> pk = new MinPQ<Node>(compar);
       pq.insert(new Node(initial, 0));
       pk.insert(new Node(initial.twin(), 0));
       while (!pq.min().bd.isGoal() && !pk.min().bd.isGoal()) {
           work(pq);
           work(pk);
       }
       if (pq.min().bd.isGoal())
          finalNode = pq.min();
    }
    
    private void work(MinPQ<Node> pq) {
      Node mn = pq.delMin();
      for (Board ne : mn.bd.neighbors())
         if (mn.parent == null || !ne.equals(mn.parent.bd))
            pq.insert(new Node(ne, mn));
    }
    public boolean isSolvable() {
       return finalNode != null;
    }
    public int moves() {
        if (finalNode == null)
            return -1;
       return finalNode.mc;
    }
    public Iterable<Board> solution() {
        if (finalNode == null)
            return null;
       Stack<Board> bds = new Stack<Board>();
       for (Node i = finalNode; i != null; i = i.parent) {
          bds.push(i.bd);
       }
       return bds;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}