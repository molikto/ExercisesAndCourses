/*
ID: pirripe1
PROG: transform
LANG: C++
*/
#include <fstream>
#include <iostream>

using namespace std;


class Matrix {
public:
	Matrix(int n) {	
		mat = new char*[n];
		for (int i = 0; i < n; i++) {
			mat[i] = new char[n];
		}
	}
	Matrix(istream& in, int n);
	char** mat;
	int lent;
	Matrix* rotate(int deg);
	Matrix* reflect();
	bool equal(Matrix& mat);
	int len() {return lent;}
};

Matrix::Matrix(istream& in, int n) {
	lent = n;
	mat = new char*[n];
	for (int i = 0; i < n; i++) {
		mat[i] = new char[n];
		in.getLine(mat[i], n);
	}
}

Matrix* Matrix::rotate(int deg) {
	Matrix* matrix = new Matrix(len());
	if (deg == 270) {
		for (int i = 0; i < len(); i++) {
			for (int j = 0; j < len(); j++) {
				matrix->mat[i][j] = mat[j][len()-1-i];
			}
		}
	} else if (deg == 180) {
		for (int i = 0; i < len(); i++) {
			for (int j = 0; j < len(); j++) {
				matrix->mat[i][j] = mat[len()-1-i][len()-1-j];
			}
		}
	} else if (deg == 90) {
		for (int i = 0; i < len(); i++) {
			for (int j = 0; j < len(); j++) {
				matrix->mat[i][j] = mat[len()-1-j][i];
			}
		}
	}
	return matrix;
}

Matrix* Matrix::reflect() {
	Matrix* matrix = new Matrix(len());
	for (int i = 0; i < len(); i++) {
		for (int j = 0; j < len(); j++) {
			matrix->mat[i][j] = mat[i][len()-1-j];
		}
	}
	return matrix;
}

 bool Matrix::equal(Matrix& another) {
	for (int i = 0; i < len(); i++) {
		for (int j = 0; j < len(); j++) {
			if (mat[i][j] != another.mat[i][j]) {
				return false;
			}
		}
	}
	return true;
}

int main() {
	// input
    ofstream fout ("transform.out");
    ifstream fin ("transform.in");
	int n;
	fin >> n;
	Matrix origMat (fin, n);
	Matrix aftMat (fin, n);

	int which = 7;
	if (origMat.equal(aftMat)) {
		which = 6;
	} else if (aftMat.equal(*origMat.rotate(90))) {
		which = 1;
	} else if (aftMat.equal(*origMat.rotate(180))) {
		which = 2;
	} else if (aftMat.equal(*origMat.rotate(270))) {
		which = 3;
	} else if (aftMat.equal(*origMat.reflect())) {
		which = 4;
	} else if (aftMat.equal(*origMat.rotate(90)->reflect()) ||
			aftMat.equal(*origMat.rotate(180)->reflect()) ||
			aftMat.equal(*origMat.rotate(270)->reflect())) {
		which = 5;
	}
	fout << which << endl;

	fin.close();
	fout.close();
	return 0;
}