package main

import (
	"os"
	"crypto/sha256"
	"fmt"
)

func main() {
	fin, _  := os.Open("pa3.mp4")
	defer fin.Close()
	stat, _ := fin.Stat()
	leng := stat.Size()
	hasher := sha256.New()
	cyt := make([]byte, 0)
	end := leng
	for s := ((leng-1)/1024)*1024; s >=0; s = s - 1024 {
		fin.Seek(s, 0)
		block := make([]byte, end-s)
		fin.Read(block)
		hasher.Write(block)
		hasher.Write(cyt)
		cyt = hasher.Sum(nil)
		hasher.Reset()
		end = s
	}
	fmt.Printf("%x\n", cyt)
}