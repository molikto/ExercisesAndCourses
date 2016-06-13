import java.util.Arrays;


public class Board {


    private static StringBuilder sb = new StringBuilder();
   private int[][] blocks;
    public Board(int[][] blocks) {
       this.blocks = blocks;
       this.blocks = copyBoard();
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
       return blocks.length;
    }
    public int hamming() {
       int res = 0;
       int shouldBe = 0;
       for (int[] row : blocks)
          for (int item : row) {
              shouldBe++;
              shouldBe %= dimension()*dimension();
             if (item != 0 && item != shouldBe)
                res++;
          }
       return res;
    }
    
    public int manhattan() {
       int res = 0;
       int should;
       for (int row = 0; row < dimension(); row++)
          for (int col = 0; col < dimension(); col++) {
              if (blocks[row][col] == 0)
                  continue;
             should = blocks[row][col]-1;
             res += Math.abs(should/dimension()-row)
                + Math.abs(should % dimension()-col);
          }
       return res;
    }
    
    public boolean isGoal() {
    //   System.out.println(toString());
       return hamming() == 0;
    }
    
    
    private int[][] copyBoard() {
       int[][] neu = new int[blocks.length][blocks.length];
       for (int i = 0; i < neu.length; i++)
          neu[i] = Arrays.copyOf(blocks[i], blocks.length);
       return neu;
    }
    public Board twin() {
       int[][] blo = copyBoard();
       if (dimension() > 1) {
           int ex;
           if (blo[0][0] == 0 || blo[0][1] == 0) {
              ex = blo[1][0];
              blo[1][0] = blo[1][1];
              blo[1][1] = ex;
           } else {
              ex = blo[0][0];
              blo[0][0] = blo[0][1];
              blo[0][1] = ex;
           }
       }
       return new Board(blo);
    }
    
    public boolean equals(Object y) {
       if (y == this)
          return true;
       if (!(y instanceof Board))
          return false;
       Board that = (Board) y;
       if (dimension() != that.dimension())
           return false;
       for (int row = 0; row < dimension(); row++)
          for (int col = 0; col < dimension(); col++)
             if (blocks[row][col] != that.blocks[row][col])
                return false;
       return true;
    }

    public Iterable<Board> neighbors() {
       int row = 0;
       int col = 0;
    out : for (; row < dimension(); row++)
          for (col = 0; col < dimension(); col++)
             if (blocks[row][col] == 0)
                break out;
       Stack<Board> bds = new Stack<Board>();
       if (row > 0)
          bds.push(getNewBoard(row, col, -1, 0));
       if (row < dimension()-1)
          bds.push(getNewBoard(row, col, 1, 0));
       if (col > 0)
          bds.push(getNewBoard(row, col, 0, -1));
       if (col < dimension()-1)
          bds.push(getNewBoard(row, col, 0, 1));
       return bds;
    }
    
    
    private Board getNewBoard(int row, int col, int dr, int dc) {
       Board newBd = new Board(blocks);
       newBd.blocks[row][col] = newBd.blocks[row+dr][col+dc];
       newBd.blocks[row+dr][col+dc] = 0;
    //   System.out.println(newBd);
       return newBd;
    }
    
    public String toString() {
       sb.setLength(0);
       sb.append(dimension());
       sb.append('\n');
       for (int[] row : blocks) {
          for (int item : row)
             sb.append(' ').append(item).append(' ');
          sb.append('\n');
       }
       return sb.toString();
    }
}