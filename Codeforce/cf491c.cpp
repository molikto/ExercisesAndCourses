
#include <iostream>
#include <cstdlib>
#include <vector>

#define K_MAX 55


using namespace std;
int n, k;
string a, b;

int w[K_MAX][K_MAX];
int u[K_MAX], v[K_MAX], p[K_MAX], way[K_MAX], minv[K_MAX];
bool used[K_MAX];
int ans[K_MAX];

int toInt(char ch) {
    if (ch >= 'a' && ch <= 'z') return ch - 'a' + 1;
    else return ch - 'A' + 27;
}

char toChar(int i) {
    if (i > 26) return i - 27 + 'A';
    else return i - 1 + 'a';
}

//hungarian algorithm
void solve() {
    for (int i = 1; i <= k; i++) {
        p[0] = i;
        int j0 = 0;
        for (int j = 0; j <= k; j++) {
            minv[j] = INF;
            used[j] = 0;
        }
        do {
            used[j0] = 1;
            int i0 = p[j0], delta = INF, j1;
            for (int j = 1; j <= k; j++) {
                if (!used[j]) {
                    int cur = w[i0][j] - u[i0] - v[j];
                    if (cur < minv[j]) {
                        minv[j] = cur;
                        way[j] = j0;
                    }
                    if (minv[j] < delta) {
                        delta = minv[j];
                        j1 = j;
                    }
                }
            }
            for (int j = 0; j <= k; j++) {
                if (used[j]) {
                    u[p[j]] += delta;
                    v[j] -= delta;
                }
                else minv[j] -= delta;
            }
            j0 = j1;
        } while (p[j0]);
        do {
            int j1 = way[j0];
            p[j0] = p[j1];
            j0 = j1;
        } while (j0);
    }
    for (int j = 1; j <= k; j++) ans[p[j]] = j;
}



int main() {
    ios::sync_with_stdio(0);
    cin >> n >> k >> a >> b;
    for (int i; i < n; i++) {
        w[toInt(a[i])][toInt(b[i])]--;
    }
    solve();
    cout << v[0] << endl;
    for (int i = 1; i <= k; i++) {
        cout << toChar(ans[i]);
    }
    cout << endl;
}



// copycode
// http://codeforces.com/contest/491/status?csrf_token=48567f08c84d3b7bd7dbgdfcb1fh4fca&order=BY_CONSUMED_TIME_ASC



/*
 
 previous scala code, super slow

object Main {

  def line = readLine()

  case class Match(ans: Int, cip: Int, score: Int)
  case class State(score: Int, matchs: List[Match])


  def dp(remain: Stream[Match], current: State, max: State, k: Int): State = {
    if (remain.take(k).map(_.score).sum + current.score <= max.score) {
      max
    } else {
      val h = remain.head
      val nc = State(current.score + h.score, h +: current.matchs)
      // YOU SHOULD BE GREEDY HERE, FILTER RESULTS NOT USED
      val dp2 = dp(remain.tail.filter(a => a.ans != h.ans && a.cip != h.cip), nc, if (nc.score > max.score) nc else max, k - 1)
      // YOU SHOULD BE GREEDY HERE
      val nmax = if (dp2.score > max.score) dp2 else max
      dp(remain.tail, current, nmax, k)
    }
  }
  def main(args: Array[String]) = {
    val Array(n, k) = line.split(" ").map(_.toInt)
    val cip = line
    val ans = line


    val ff = {
      val a = new Array[Int](k)
      var i = 0
      while (i < ans.length) {
        a(ans(i) - 'a') += 1
        i += 1
      }
      a
    }

    val ss = {
      val s = new Array[Array[Int]](k)
      for (i <- 0 until k) {
        s(i) = new Array[Int](k)
      }
      var i = 0
      while (i < n) {
        s(ans(i) - 'a')(cip(i) - 'a') += 1
        i += 1
      }
      s
    }


    val mm = ss.zipWithIndex.flatMap { p =>
      val cs = p._1
      val ia = p._2
      cs.zipWithIndex.map(q => Match(ia, q._2, q._1))
    }.sortBy(-_.score).toList


    val aaa = dp(mm.toStream, State(0, List.empty), State(0, List.empty), k)
    val s = {
      val vvv = aaa.matchs.sortBy(_.cip).map(_.ans)
      if (vvv.size < k) {
        vvv ++ ((0 until k).toSet -- vvv)
      } else vvv
    }
    println(aaa.score)
    println(s.map(i => (i + 'a').toChar).mkString)
  }
}

 
 */

