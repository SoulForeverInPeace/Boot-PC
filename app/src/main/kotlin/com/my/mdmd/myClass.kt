package com.my.mdmd

import android.content.Intent
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.mdmd.R
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.my.mdmd.MyInterfaces
import androidx.recyclerview.widget.DiffUtil
import java.util.*
import kotlin.concurrent.schedule


class myClass(
    private val listenerInterface: MyInterfaces
    ): RecyclerView.Adapter<myClass.LetterViewHolder>() {
	
	
private var oldList = emptyList<fileItem>()
	
var buildPath: String=""	//for building path url as user selscts folders


class LetterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.my_text_view)
		val imageView: ImageView = view.findViewById(R.id.my_image_view)
	//	val cardView  = view.findViewById<CardView>(R.id.card_hold)
    }
	
	
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false)
        
		
        return LetterViewHolder(adapterLayout)
    }
	
  
  
  override fun getItemCount(): Int {
        return oldList.size
    }
	
	override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
       
	    val item = oldList.get(position)
        holder.textView.text = item.fileName.toString()
		holder.imageView.setImageResource(item.fileImage)
       
	   
	  var clickedOnce = false
	  holder.view.setOnClickListener{
		   
		   
		   if(clickedOnce == false){			       
				   
				  val isFile = holder.textView.text.contains(".") 
				  if(isFile){
					  
					  setData(emptyList<fileItem>()) // once file selwcted, empty tge recycler view
					  
					  Toast.makeText(holder.view.context,"clicked ${holder.textView.text}", Toast.LENGTH_SHORT).show()
				     buildPath += holder.textView.text.toString()				 
				     listenerInterface.onItemClick(buildPath)
				     clickedOnce = true
					 MainActivity.Profile.saveFilePath = buildPath
 listenerInterface.goBackToMainActivityByIntent()
					  Timer().schedule(1000){
                        // do something after 1 second
                       }
					   
				   }else{
				   
		           Toast.makeText(holder.view.context,"clicked ${holder.textView.text}", Toast.LENGTH_SHORT).show()
				   buildPath += "/"
				   buildPath += holder.textView.text.toString()				 
				   listenerInterface.onItemClick(buildPath)
				   
				  // listenerInterface.onItemClick( ShellInterface.runShell("cd ${holder.textView.text}") )
				  
			  	
				   
				  ShellInterface.runShell("cd ${holder.textView.text}")
				  // val testString: String = ShellInterface.runShell("ls")
				 //  listenerInterface.onItemClick( testString )
				   setData(DataSource().loadFileTree(
				      ShellInterface.runShell("ls")
					 // testString
				   ))
				   
				   // test stuff
				   
				   clickedOnce = true //dont process click more than once
  
		  } }
		   
	  }
	   
    }
	
	fun setData(newList: List<fileItem>) {
        val diffUtil = MyDiffUtil(oldList, newList)
        val diffUtilResults = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffUtilResults.dispatchUpdatesTo(this)
    }
	
	

}