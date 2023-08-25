package com.my.mdmd

import com.jaredrummler.ktsh.*

object ShellInterface {
	
	val shell = Shell("sh") 
	lateinit var fileOrDirectory: String  
			
fun runShell(command:String): String{                      // create a shell
//var listTree: String
var result:Shell.Command.Result = shell.run(command)  // execute a command
if (result.isSuccess) {                         // check if the exit-code was 0
    fileOrDirectory = result.stdout().toString()  
	return   result.stdout().toString()     // prints "Hello, World!"
}
   fileOrDirectory =  result.stderr().toString() 
return result.stderr().toString()
}
}