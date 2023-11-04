package com.my.mdmd

import androidx.appcompat.app.AppCompatActivity
import com.my.mdmd.databinding.ActivityAboutBinding
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView

public class AboutActivity: AppCompatActivity() {
	
	private lateinit var binding: ActivityAboutBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityAboutBinding.inflate(layoutInflater)
         val view = binding.root
		 setContentView(view)
		 
		 //activate hyper link on page
		 setupHyperlink()
	
	}
	 // end of onCreate method
	
	 
	fun setupHyperlink() { // turn textview  into a hyperlink
        val linkTextView = findViewById<TextView>(R.id.about_github)
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
     }

} 
// end of activity class