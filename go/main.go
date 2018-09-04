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

func main() {
	cmdLine.Parse(os.Args[1:])
	fmt.Printf("Hello, %s!\n", name)
	fmt.Println
}