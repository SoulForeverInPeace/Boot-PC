package com.my.mdmd

import androidx.appcompat.app.AppCompatActivity
import com.my.mdmd.databinding.ActivityPickerBinding
import android.os.Bundle
import com.jaredrummler.ktsh.*
import android.content.Intent

public class PickerActivity : AppCompatActivity() {

	private lateinit var binding: ActivityPickerBinding
    
	
    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityPickerBinding.inflate(layoutInflater)
         val view = binding.root
         setContentView(view)
    }
	
	
	init{
	
	    ShellInterface.runShell("su")  
		
	}		

  

}
