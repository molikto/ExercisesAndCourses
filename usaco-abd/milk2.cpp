/*
ID: pirripe1
PROG: milk2
LANG: C++
*/
#include <fstream>
#include <iostream>
#include <algorithm>

using namespace std;

class Interval {
public:
	Interval(int s, int e) {start = s; end = e;}
	Interval() {}
	int start;
	int end;
	int len() {return end - start;}
	bool overlap(int s) {return start <= s && end > s;}
};

int main() {
	// input
    ofstream fout ("milk2.out");
    ifstream fin ("milk2.in");
	int n;
	fin >> n;
	Interval* intervals = new Interval[n];
	for (int i = 0; i < n; i++) {
		fin >> intervals[i].start >> intervals[i].end;
	}


	// start algrithm
	int* points = new int[2 * n];
	for (int i = 0; i < n; i++) {
		points[i * 2] = intervals[i].start;
		points[i * 2 + 1] = intervals[i].end;
	}
	sort(points,points+2*n); 
	bool isMilking = false;
	Interval curInterval (points[0], points[0]);
	int maxMilk = 0, maxNot = 0;
	for (int i = 0; i < 2 * n; i++) {
		int point = points[i];
		curInterval.end = point;
		if (!isMilking) {
			if (maxNot < curInterval.len()) {
				maxNot = curInterval.len();
			}
			isMilking = true;
			curInterval.start = point;
		} else {
			bool overlap = false;
			for (int j = 0; j < n; j++) {
				Interval* interval = &intervals[j];
				if (interval->overlap(point)) {
					overlap = true;
					break;
				}
			}
			if (!overlap) {
				if (maxMilk < curInterval.len()) {
					maxMilk = curInterval.len();
				}
				isMilking = false;
				curInterval.start = point;
			}
		}
	}
	// write
	fout << maxMilk << " " << maxNot << endl;
	fin.close();
	fout.close();
	return 0;
}