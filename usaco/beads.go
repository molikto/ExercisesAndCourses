package main

import (
	"os"
	"fmt"
)


const (
	EITHER = byte('w')
	RED = byte('r')
	BLUE = byte('b')
)

func check(color *byte, c byte) bool {
	if (*color == EITHER) {
		*color = c
		return true
	} else if (*color == c || c == EITHER) {
		return true
	} 
	*color = EITHER
	return false
}
func main() {
	fin, _ := os.Open("beads.in")
	defer fin.Close()
	fout, _ := os.Create("beads.out")
	defer fout.Close()
	n := 0
	fmt.Fscan(fin, &n)
	str := make([]byte, 0)
	fmt.Fscan(fin, &str)
	max := 0
	for i := 0; i < n; i++ {
		color := EITHER
		cur := 0
		str = append(str[1:], str[0])
		for j := 0; j < n; j++ {
			if check(&color, str[j]) {
				cur++
			} else {
				break
			}
		}
		for j := n - 1; j >= 0; j-- {
			if check(&color, str[j]) {
				cur++
			} else {
				break
			}
		}
		if max < cur {
			max = cur
			if max > n {
				max = n
				break
			}
		}
	}

	fout.WriteString(fmt.Sprintf("%d\n", max))
}