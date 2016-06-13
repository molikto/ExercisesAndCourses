/*
ID: pirripe1
PROG: gift1
LANG: C++
*/
#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <map>
#include <queue>

using namespace std;

int main() {
    ofstream fout("gift1.out");
    ifstream fin("gift1.in");

    int n;
    fin >> n;

    map<string, int> friends;

    queue<string> names;
    for (int i = 0; i < n; i++) {
        string str;
        fin >> str;
        names.push(str);
        friends[str] = 0;
    }


    for (int i = 0; i < n; i++) {
        string name;
        int money, part;
        fin >> name >> money >> part;
        if (part == 0) {
            continue;
        }
        friends[name] = friends[name] - money + money % part;
        for (int j = 0; j < part; j++) {
            string name;
            fin >> name;
            friends[name] = friends[name] + money / part;
        }
    }
    for (int i = 0; i < n; i++) {
        string name = names.front();
        names.pop();
        fout << name << ' ' << friends[name] << endl;
    }

    fin.close();
    fout.close();
    return 0;
}