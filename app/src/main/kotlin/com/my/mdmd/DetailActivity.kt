package com.my.mdmd

import androidx.activity.OnBackPressedCallback
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.mdmd.databinding.ActivityDetailBinding
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jaredrummler.ktsh.*
import android.text.method.LinkMovementMethod
import android.widget.Toast


class DetailActivity : AppCompatActivity() {
    
     var su_granted = false
	
	
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
        // Retrieve a binding object that allows you to refer to views by id name
        // Names are converted from snake case to camel case.
        // For example, a View with the id word_one is referenced as binding.wordOne
        var binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
		
		setupHyperlink()
		
		
		
		//disable back buttom on a13
		onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button press here
            }
               })
	
	
		// Disable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Disable back button on device
        overridePendingTransition(0, 0)
		
		
		
		//su access button diapatcher
		var su_btn = findViewById<Button>(R.id.su_access_button)
		su_btn.setOnClickListener{
			
		 	 //Toast.makeText(this,"Inside btn", Toast.LENGTH_SHORT).show()
			
			//check su
		   var shell = Shell("sh")                        // create a shell
            var result: Shell.Command.Result = shell.run("su\n" +
                                                                 "ls")  // execute a command
            if (result.isSuccess) {   
	         
            //Toast.makeText(this,"Suuuui", Toast.LENGTH_SHORT).show()
			 su_granted = true              
            }
            

			
		    //use su return value
			var go_to_mainActivily = !su_granted // update later
			if(go_to_mainActivily){
				
				// stay on maimactuvity only
				MainActivity.Profile.name = "u"
				val context = applicationContext
				val intent = Intent(this, MainActivity::class.java)
				startActivity(intent)
		    }
			
			
			if(go_to_mainActivily == false){
				val bt = findViewById<TextView>(R.id.gap_text_view)
				bt.text = "oPps"
				val textbid  = findViewById<TextView>(R.id.activity_main_link)
				textbid.setVisibility(View.VISIBLE)
				
				}
			
			
		}
		
		
		
		
		
		
  
    }
    
	
fun setupHyperlink() {
        val linkTextView = findViewById<TextView>(R.id.activity_main_link)
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }


	
	
}