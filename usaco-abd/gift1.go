package main

import (
	"os"
	"fmt"
)

/*

compared to the solution, two things is bad:
no oo at all, and no symbol abstraction of people name at all
and you should parpare for string reuse things


*/


func main() {
	temp := make([]byte, 0)

	fin, _ := os.Open("gift1.in")
	defer fin.Close()
	fout, _ := os.Create("gift1.out")
	defer fout.Close()

	n := 0
	fmt.Fscanf(fin, "%d", &n)
	friends := make(map[string]int)
	names := make([]string, n, n)

	for i := 0; i < n; i++ {
		fmt.Fscan(fin, &temp)
		names[i] = string(temp)
		friends[names[i]] = 0
	}

	for i := 0; i < n; i++ {
		money, part := 0, 0
		fmt.Fscan(fin, &temp, &money, &part)
		name := string(temp)
		if part == 0 {
			continue
		}
		friends[name] = friends[name] - money + money % part
		for j := 0 ; j < part; j++ {
			fmt.Fscan(fin, &temp)
			name := string(temp)
			friends[name] = friends[name] + money / part
		}
	}
	for _, val := range names {
		fout.WriteString(fmt.Sprintf("%s %d\n", val, friends[val]))
	}
}