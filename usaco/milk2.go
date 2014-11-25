package  main

import (
	"os"
	"fmt"
	"sort"
)



/*
I see an answer in USACO Solutions

it use start, end for whole sequence
and then check for every integer, this is much compact code
but is every inefficent.

I think the curtial point pointer algorithm is always better~!


*/

type Interval struct {
	start int
	end int
}

func (in Interval) Len() int {
	return in.end - in.start
}

func (in Interval) Overlap(s int) bool {
	return in.end > s && in.start <= s
}


func main () {
	fin, _ := os.Open("milk2.in")
	defer fin.Close()
	fout, _ := os.Create("milk2.out")
	defer fout.Close()
	// inputing
	n := 0
	fmt.Fscan(fin, &n)
	intervals := make([]*Interval, n)
	for i := 0; i < n; i++ {
		s, e := 0, 0
		fmt.Fscan(fin, &s, &e)
		intervals[i] = &Interval{s, e}
	}

	// start algrithm
	points := make([]int, n * 2)
	for index, interval := range intervals {
		points[index * 2] = interval.start
		points[index * 2 + 1] = interval.end
	}
	sort.Sort(sort.IntSlice(points))
	isMilking := false
	curInterval := Interval{points[0], points[0]}
	maxMilk, maxNot := 0, 0
	for _, point := range points {
		curInterval.end = point
		if !isMilking {
			if maxNot < curInterval.Len() {
				maxNot = curInterval.Len()
			}
			isMilking = true
			curInterval.start = point
		} else {
			overlap := false
			for _, interval := range intervals {
				if interval.Overlap(point) {
					overlap = true
					break
				}
			}
			if !overlap {
				if maxMilk < curInterval.Len() {
					maxMilk = curInterval.Len()
				}
				isMilking = false
				curInterval.start = point
			}
		}
	}

	// write
	fout.WriteString(fmt.Sprintf("%d %d\n", maxMilk, maxNot))
}


