package main

import (
    "fmt"
)

// This program prints "Hello, world." 5 times.
func main() {
    n := 5

    if n > 0 {
        for i := 0; i < n; i++ {
            fmt.Println("Hello, World!")
        }
    }

    var a = 1.1

    fmt.Println(a)

    var b = 0x22

    const c string = "Some string"

    fmt.Println(b, c)

    charSymbol := 'a'

    fmt.Println(charSymbol)
}