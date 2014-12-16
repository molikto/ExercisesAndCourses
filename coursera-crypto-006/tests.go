package main
import (
	"fmt"
	"strconv"
)

func parseBytes (str string) []byte {
	out := make([]byte, len(str)/2)
	for i := 0; i < len(str); i = i + 2 {
		t, _ := strconv.ParseUint(str[i:i+2], 16, 8)
		out[i/2] = byte(t)
	}
	return out
}

func main() {

	msg0 := []byte("Pay Bob 100$")
	msg1 := []byte("Pay Bob 500$")
	iv := parseBytes("20814804c1767293b99f1d9cab3bc3e7")

	for i := 0; i < len(iv) && i < len(msg0); i++ {
		iv[i] = msg0[i] ^ msg1[i] ^ iv[i]
	}

	fmt.Printf("%x\n", iv)
}