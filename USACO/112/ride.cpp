/*
ID: pirripe1
PROG: ride
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int main() {
    ofstream fout("ride.out");
    ifstream fin("ride.in");
    string com, gro;
    fin >> com >> gro;
    long co = 1, gr = 1;
    for (int i = 0; com[i] != 0; i++)
    	co *= com[i] - 'A' + 1;
    for (int i = 0; gro[i] != 0; i++)
    	gr *= gro[i] - 'A' + 1;
    if (co % 47 == gr % 47)
    	fout << "GO";
    else
    	fout << "STAY";
    fout << endl;
    fin.close();
    fout.close();
    return 0;
}