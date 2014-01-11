package main

/* 1.1.1 checked

Compared with the solution, I mixed different abstract level codes
together. What a shame.

*/
import (
	"os"
	"fmt"
)

func main() {
	fin, _ := os.Open("ride.in")
	defer fin.Close()
	fout, _ := os.Create("ride.out")
	defer fout.Close()

	// this is ok
	// string? []rune?
	// if it changes, must rune
	// it it from input, rune or string
	// or string
	sco, sgr := make([]byte, 0), make([]byte, 0)

	// use fmt
	fmt.Fscan(fin, &sco, &sgr)

	co, gr := int64(1), int64(1)
	for _, c := range sco {
		co = co * int64(c - 'A' + 1)
	}
	for _, c := range sgr {
		gr = gr * int64(c - 'A' + 1)
	}
	if co % int64(47) == gr % int64(47) {
		fout.WriteString("GO\n")
	} else {
		fout.WriteString("STAY\n")
	}
}