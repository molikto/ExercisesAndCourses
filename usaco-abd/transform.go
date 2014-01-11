package main

import (
	"os"
	"fmt"
	"io"
)


type Matrix [][]byte

func NewMatrix(in io.ReadSeeker, n int) *Matrix {
	matrix := Matrix(make([][]byte, n))
	buf := make([]byte, 1)
//	for ; buf[0] != '@' && buf[0] != '-'; in.Read(buf) {
//	}
//	in.Seek(-1, 1)
	for i := 0; i < matrix.Len(); i++ {
		matrix[i] = make([]byte, n)
		in.Read(matrix[i])
		in.Read(buf)
	}
	return &matrix
}

func (mat *Matrix) Len() int {
	return len(*mat)
}

func (mat *Matrix) Rotate(deg int) *Matrix {
	matrix := Matrix(make([][]byte, mat.Len()))
	for i := 0; i < mat.Len(); i++ {
		matrix[i] = make([]byte, mat.Len())
	}
	if (deg == 270) {
		for i, row := range matrix {
			for j := 0; j < mat.Len(); j++ {
				row[j] = (*mat)[j][mat.Len()-1-i]
			}
		}
	} else if (deg == 180) {
		for i, row := range matrix {
			for j := 0; j < mat.Len(); j++ {
				row[j] = (*mat)[mat.Len()-1-i][mat.Len()-1-j]
			}
		}
	} else if (deg == 90) {
		for i, row := range matrix {
			for j := 0; j < mat.Len(); j++ {
				row[j] = (*mat)[mat.Len()-1-j][i]
			}
		}
	}
	return &matrix
}

func (mat *Matrix) Reflect() *Matrix {
	matrix := Matrix(make([][]byte, mat.Len()))
	for i := 0; i < mat.Len(); i++ {
		matrix[i] = make([]byte, mat.Len())
	}
	for i, row := range matrix {
		for j := 0; j < mat.Len(); j++ {
			row[j] = (*mat)[i][mat.Len()-1-j]
		}
	}
	return &matrix
}

func (matrix *Matrix) Equal(another *Matrix) bool {
	for i, row := range *matrix {
		for j, val := range row {
			if val != (*another)[i][j] {
				return false
			}
		}
	}
	return true
}

func (matrix *Matrix) String() string {
	str := make([]byte, 0)
	for _, row := range *matrix {
		str = append(str, row...)
		str = append(str, '\n')
	}
	return string(str)
}



func main() {
	fin, _ := os.Open("transform.in")
	defer fin.Close()
	fout, _ := os.Create("transform.out")
	defer fout.Close()

	n := 0
	fmt.Fscan(fin, &n)
	origMat := NewMatrix(fin, n)
	aftMat := NewMatrix(fin, n)
	which := 7
	if origMat.Equal(aftMat) {
		which = 6
	} else if aftMat.Equal(origMat.Rotate(90)) {
		which = 1
	} else if aftMat.Equal(origMat.Rotate(180)) {
		which = 2
	} else if aftMat.Equal(origMat.Rotate(270)) {
		which = 3
	} else if aftMat.Equal(origMat.Reflect()) {
		which = 4
	} else if aftMat.Equal(origMat.Rotate(90).Reflect()) ||
			aftMat.Equal(origMat.Rotate(180).Reflect()) ||
			aftMat.Equal(origMat.Rotate(270).Reflect()) {
		which = 5
	}
	fmt.Println(origMat.String())
	fmt.Println(aftMat.String())
	fout.WriteString(fmt.Sprintf("%d\n", which))
}