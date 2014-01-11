/*
ID: pirripe1
PROG: beads
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

const int EITHER = 'w';
const int RED = 'r';
const int BLUE = 'b';

bool check(int *color, int c) {
	if (*color == EITHER) {
		*color = c;
		return true;
	} else if (*color == c || c == EITHER) {
		return true;
	} else {
		*color = EITHER;
		return false;
	}
}

int main() {
    ofstream fout("beads.out");
    ifstream fin("beads.in");
	int n;
	string str;
	fin >> n >> str;

	int max = 0;
	for (int i = 0; i < n; i++) {
		int color = EITHER;
		int cur = 0;
		str = str.substr(1).append(str.substr(0, 1));
		for (int j = 0; j < n; j++) {
			if (check(&color, str[j])) {
				cur++;
			} else {
				break;
			}
		}
		for (int j = n - 1; j >= 0; j--) {
			if (check(&color, str[j])) {
				cur++;
			} else {
				break;
			}
		}
		if (max < cur) {
			max = cur;
			if (max > n) {
				max = n;
				break;
			}
		}
	}
	fout << max << endl;
	fin.close();
	fout.close();
	return 0;
}