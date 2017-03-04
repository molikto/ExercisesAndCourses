package main

import (
	"os"
	"fmt"
)

// this is rather a bad idea here
// it simele makes theta(n) to theta(n^2)


const (
	SAT = iota
	SUN
	MON
	TUE
	WEN
	TUR
	FRI
)

func leap(y int) bool {
	return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0
}

func day(d int) int {
	return (d + 2) % 7
}

func newDate(year, month, monthDay int) (dt int) {
	for y := 1900; y < year; y++ {
		if leap(y) {
			dt = dt + 366
		} else {
			dt = dt + 365
		}
	}
	for m := 1; m < month; m++ {
		switch m {
		case 1, 3, 5, 7, 8, 10, 12:
			dt = dt + 31
		case 4, 6, 9, 11:
			dt = dt + 30
		case 2:
			if leap(year) {
				dt = dt + 29
			} else {
				dt = dt + 28
			}
		}
	}
	dt = dt + monthDay - 1
	return
}

func main() {
	fin, _ := os.Open("friday.in")
	defer fin.Close()
	fout, _ := os.Create("friday.out")
	defer fout.Close()
	// wish I can write like this
	// n := fin.Fscan("%d")
	n := 0
	fmt.Fscan(fin, &n)

	var cs = new([7]int)
	for y := 1900; y < 1900 + n; y++ {
		for m := 1; m <= 12; m++ {
			cs[day(newDate(y, m, 13))]++
		}
	}

	fout.WriteString(fmt.Sprintf("%d %d %d %d %d %d %d\n",
		cs[0], cs[1], cs[2], cs[3], cs[4], cs[5], cs[6]))
}