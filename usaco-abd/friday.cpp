/*
ID: pirripe1
PROG: friday
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>

using namespace std;


bool leap(int y) {
    return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0;
}

int day(int d) {
    return (d + 2) % 7;
}

int newDate(int year, int month, int monthDay) {
    int dt = 0;
    for (int y = 1900; y < year; y++) {
        if (leap(y)) {
            dt = dt + 366;
        } else {
            dt = dt + 365;
        }
    }
    for (int m = 1; m < month; m++) {
        switch (m) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            dt = dt + 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            dt = dt + 30;
            break;
        case 2:
            if (leap(year)) {
                dt = dt + 29;
            } else {
                dt = dt + 28;
            }
        }
    }
    dt = dt + monthDay - 1;
    return dt;
}



int main() {
    ofstream fout("friday.out");
    ifstream fin("friday.in");

    int n;
    fin >> n;

    int counts[7];
    for (int i = 0; i < 7; i++) {
        counts[i] = 0;
    }
    for (int y = 1900; y < 1900 + n; y++) {
        for (int m = 1; m <= 12; m++) {
            counts[day(newDate(y, m, 13))]++;
        }
    }
    for (int i = 0; i < 6; i++) {
        fout << counts[i] << " ";
    }
    fout << counts[6] << endl;

    fin.close();
    fout.close();
    return 0;
}