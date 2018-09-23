package main

import (
	"flag"
	"fmt"
	"os"
)
  

var name string
var cmdLine = flag.NewFlagSet("", flag.ExitOnError)
func init() {
	
	cmdLine.Usage = func() {
		fmt.Fprintf(os.Stderr, "Usage of %s:\n,", "question")
		cmdLine.PrintDefaults()
	}
	cmdLine.StringVar(&name, "name", "everyone", "the greetings")
}

func f(i int) {
	fmt.Println(i)
}
func main() {
	//cmdLine.Parse(os.Args[1:])
	//fmt.Printf("Hello, %s!\n", name)
	i := 1
	if i == 1 {
		f(i -1)
	}else if i == 2{
		f(i)
	}
	
}