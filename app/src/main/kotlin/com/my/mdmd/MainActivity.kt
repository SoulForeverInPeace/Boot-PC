package com.my.mdmd

import androidx.appcompat.app.AppCompatActivity
import com.my.mdmd.databinding.ActivityMainBinding
import android.os.Bundle
import android.widget.EditText


import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.chibatching.kotpref.*
import android.util.Log
import androidx.cardview.widget.CardView
import android.widget.TextView
import java.io.File
import android.provider.OpenableColumns
import android.app.Activity
import android.widget.Button
import com.jaredrummler.ktsh.*
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
public class MainActivity : AppCompatActivity() {
    
    object Profile : KotprefModel() {
       var name: String by stringPref(default = "h")
       var saveFilePath : String by stringPref(default = "")
       var massStorage : String by stringPref(default = "")
    }

	
	private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
         val view = binding.root
		 
		 //start preferance library
		 Kotpref.init(this)
		 
		 if(Profile.name != "u"){
		   val intent = Intent(this, DetailActivity::class.java)
		   startActivity(intent)
		 }
		 
		 //start active view
         setContentView(view)
		 
		 
		
		
		
		
		
		 
		 
    } 
	 //end of set content braces 
	
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		      
	 menuInflater.inflate(R.menu.layout_menu, menu)
     val layoutButton = menu?.findItem(R.id.action_switch_layout)
        
     return true    
	}
	 //end of set onCreateOptionsMenu braces 
	
	
	/**
    * Determines how to handle interactions with the selected [MenuItem]
    */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v("item","check")
		return when (item.itemId) {
			
            R.id.action_switch_layout -> {
				
				
				Log.v("item","working")
				val intent = Intent(this, AboutActivity::class.java)
		        startActivity(intent)
                
                return true
            }
            //  Otherwise, do nothing and use the core event handling

            // when clauses require that all possible paths be accounted for explicitly,
            //  for instance both the true and false cases if the value is a Boolean,
            //  or an else to catch all unhandled cases.
            else -> super.onOptionsItemSelected(item)
        }
    }
	 ////end of set onOptionsItemSelected braces 
	 
	 
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)

       if (requestCode == 565 && resultCode == Activity.RESULT_OK) {
          val uri = data?.data
          val cursor = contentResolver.query(uri!!, null, null, null, null)
          cursor!!.moveToFirst()
          val nameIndex = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
		
	  	val pathIndex = cursor!!.getColumnIndex("_data")
          val name = cursor!!.getString(nameIndex)
          val path = cursor!!.getString(pathIndex)
          val filePathTextView = findViewById<TextView>(R.id.edittext_view)     
		  
		
		  //set the path in edit text and save in prefereanve		
          filePathTextView.text = path
          Profile.saveFilePath = path
		  cursor.close()		
	   }
    }
	 ////end of set onActivityResult braces 
	 

     
  
	 
}