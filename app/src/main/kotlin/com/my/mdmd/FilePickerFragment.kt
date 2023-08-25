package com.my.mdmd	



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.mdmd.databinding.FragmentFilePickerBinding
import android.widget.EditText


import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat

import android.util.Log
import androidx.cardview.widget.CardView
import android.widget.TextView
import java.io.File
import android.provider.OpenableColumns
import android.app.Activity
import android.widget.Button
import com.my.mdmd.R

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable	

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaredrummler.ktsh.*
import com.my.mdmd.MyInterfaces	
import com.my.mdmd.myClass
import com.my.mdmd.ShellInterface
import com.my.mdmd.DataSource

class FilePickerFragment : Fragment(), MyInterfaces  {
		
	private var _binding: FragmentFilePickerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
	
	private val myClass: myClass = myClass(this)
	
	private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
	
	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		
    }
	
	
	
	override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentFilePickerBinding.inflate(inflater, container, false)
        
		
	
		
		return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		
	
       
	   
	  // binding.textView2.append(ShellInterface.fileOrDirectory.toString())
	   
	    recyclerView = binding.myRecyclerView
		recyclerView.layoutManager = LinearLayoutManager(context)
		recyclerView.adapter = myClass  //update later with adpater class
        recyclerView.setHasFixedSize(true)   
		
		
		
		 
	 	myClass.setData(DataSource().loadFileTree(
		        ShellInterface.runShell("ls")
		 ))
	
	//	binding.textView2.append(ShellInterface.runShell("ls"))
		


		
	}
	
	
	
	override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
	}
	
	
        
override fun onItemClick(item: String) {
        // Do something with the item
		binding.textView.text = item
    }
		
		
		fun rn(): String{
        val shell = Shell("sh")                         // create a shell
		val result: Shell.Command.Result = shell.run("ls")  // execute a command
		if (result.isSuccess) {                         // check if the exit-code was 0
		return result.stdout().toString()                    // prints "Hello, World!"
		}
		return result.stderr().toString()
	}

   override fun goBackToMainActivityByIntent(){
val intent = Intent(requireActivity(), MainActivity::class.java)
startActivity(intent)
}
		
}