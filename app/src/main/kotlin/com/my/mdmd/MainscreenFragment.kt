package com.my.mdmd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.mdmd.databinding.FragmentMainscreenBinding
import android.widget.EditText

import android.content.Context
//import androidx.appcompat.widget.AppBarLayout

import android.widget.Toast
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
import com.my.mdmd.R
import android.content.res.Configuration
import com.jaredrummler.ktsh.*

import androidx.appcompat.widget.Toolbar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.content.Intent
import android.app.AlertDialog
import android.content.DialogInterface

class MainscreenFragment: Fragment() {
	
	private var _binding: FragmentMainscreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
	private val appbar get() = binding.topAppBar
	
	private var showDialog = true
	private var selectedFilePicker = 0  // 0 means yet tk select, 1 means dsafult, 2 means fiolc
	
	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		
    }
	
	
	
	override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentMainscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
		//t appbar in white mode
		
		
	//	changeAppBarColorToDark(appbar, requireContext())
	
		// handle textinput view	
	 	var textEdit =  binding.edittextView // findViewById<EditText>(R.id.edittext_view)
	     if(MainActivity.Profile.saveFilePath.isEmpty()){
			Log.v("preff","empty")
		 }
		 
		 else{
			Log.v("preff",MainActivity.Profile.saveFilePath)
			 textEdit.setText(MainActivity.Profile.saveFilePath)			
		 } 
		 
		 
		 //browse button for picking file
	 	val browseBtn = binding.browseButton
	 	browseBtn.setOnClickListener{
	 	
		   if(showDialog){
		   showDefaultDialog()
           if(selectedFilePicker==2){textEdit.setText(MainActivity.Profile.saveFilePath)}
		   }else{
  
if(selectedFilePicker == 1){
		   val intent = Intent(Intent.ACTION_PICK)
           intent.type = "*/*"
           startActivityForResult(intent, 565)}
else if (selectedFilePicker==2){
selectedFilePicker = 2
             val intent = Intent(requireActivity(), PickerActivity::class.java)
startActivity(intent)
//textEdit.setText(MainActivity.Profile.saveFilePath)
}
else{
Toast.makeText(activity,"Something went wrong", Toast.LENGTH_SHORT).show()
}	 			 		 		
		}

 }
		 
		 
		 //cardview and its items
	 	val cardView = binding.infoCardview		 
	     val cardViewText = cardView.findViewById<TextView>(R.id.info_textview)		
       //  cardViewText.append("\n  Initiating")
		  
		 // check shell path if sh or system
		 var shellType = 0
		 try{
			 var shell = Shell("#!/system/bin/sh")	 
	     }
         catch(e: Shell.NotFoundException){
			 shellType = 1
			 cardViewText.append("\n    ::: shell not found warning[negligiable]")
	     } 
		 
		 //start button to turn on gadget
         var shellb = Shell("sh")
		 var btnText = 1  //1 means start, 0 means stop is showing
		 var startBtn = binding.startButton
		 startBtn.setOnClickListener { 
         
        	//test stuff	
			 
             fun getMassStorage(list: List<String>): String {
    for ((index, value) in list.withIndex()) {
        if (value.contains("mass_storage")) {
            return list[index]
        }
    }
    return "1"
}
//get mass stuff
if(MainActivity.Profile.massStorage.isEmpty()){
ShellInterface.runShell("su")  
var tempList = ShellInterface.runShell("ls /config/usb_gadget/g1/functions/").lines()


var stri: String = getMassStorage(tempList)
      
      
      MainActivity.Profile.massStorage = stri     
			   
                
}                
         //check if we have retrived  MainActivity.Profile.massStorage
         if( MainActivity.Profile.massStorage == "1"){
         cardViewText.append("\n error on retriving masss value. ${MainActivity.Profile.massStorage}")
         cardView.setVisibility(View.VISIBLE)
         }else{
			
            
			 // Stop button showing impliment
			 if(btnText==0){
				 revertChanges("updateLater", cardViewText )
				 startBtn.text = "Start"
                 
                  cardView.setVisibility(View.INVISIBLE)
               btnText = 1
		 	}
             
              else if(btnText==1){
              // start button showing -> clicked1 implemetation
              	
                  	 //save whatever is in edittextview
			 MainActivity.Profile.saveFilePath = textEdit.text.toString()
             	          
			 cardView.setVisibility(View.VISIBLE)
	         var fileNameInQuote = textEdit.getText().toString()
		
		     
			   var shell = Shell("sh")
		   	val cdEmu: Shell.Command.Result = shell.run("su setprop sys.usb.config cdrom\n" +
              "setprop sys.usb.configfs 1\n" +
              "cd /config/usb_gadget/g1\n" +
              "getprop sys.usb.config >configs/b.1/strings/0x409/configuration\n" +
              "for f in configs/b.1/f*; do rm \$f; done\n" +
              "echo 0x1d6b > idVendor\n" +
              "echo 0x0104 > idProduct\n" +
              "ln -s /config/usb_gadget/g1/functions/${MainActivity.Profile.massStorage}  /config/usb_gadget/g1/configs/b.1/f1\n" +
              "echo \"\" > /sys/class/android_usb/android0/f_mass_storage/lun0/file\n" +
              "echo 1 > /sys/class/android_usb/android0/f_mass_storage/lun0/cdrom\n" +
              "echo 1 > /sys/class/android_usb/android0/f_mass_storage/lun0/ro\n" +
              "echo -n \"$fileNameInQuote\" >configs/b.1/f1/lun.0/file\n" +
              "getprop sys.usb.controller >/config/usb_gadget/g1/UDC\n" +
              "setprop sys.usb.state cdrom" )
		
	      	if (cdEmu.isSuccess) {
                 cardViewText.append("\n   :::All is ok. Plug and boot")
				 btnText = 0
              }   
			  else{
                cardViewText.append("\n    :::Something went wrong[all cmd]")
               }
		
	           cardViewText.append("\nNot working? Contact the author")
       
		   
		  
		  startBtn.text = "Stop"
       btnText=0
		   
		   
                }//end braces of start button showing
                
         } //end brace  of if MainActivity.Profile.massStorage got value       
                
                
         }
		 //end of startButton
		 
		 
		
        
    }
 	// end of onViewCreated

	
	
	
	
	
	
	/**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
	}
	
	
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)

       if (requestCode == 565 && resultCode == Activity.RESULT_OK && data != null) {
          val uri = data?.data
          val cursor = requireActivity().contentResolver.query(uri!!, null, null, null, null)
          cursor!!.moveToFirst()
          val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
		
	  	val pathIndex = cursor!!.getColumnIndex("_data")
          val name = cursor!!.getString(nameIndex)
          val path = cursor!!.getString(pathIndex)
          val filePathTextView = binding.edittextView     
		  
		  
		  Log.v("preff", path.toString() )
		
		  //set the path in edit text and save in prefereanve		
          filePathTextView.setText( path.toString() )
          MainActivity.Profile.saveFilePath = path
		  cursor.close()		
	   }
    }
	 ////end of set onActivityResult braces 
	 
	fun revertChanges(state: String, cardViewText: TextView){ // need to modify later
		var tempShell = Shell("sh")
	     var revertChangesResult1 = tempShell.run("su" +
                        "setprop sys.usb.config adb\n" +
                        "setprop sys.usb.configfs 1\n" +
                        "cd /config/usb_gadget/g1\n" +
                        "getprop sys.usb.config >configs/b.1/strings/0x409/configuration\n" +
                        "for f in configs/b.1/f*; do rm \$f; done\n" +
                        "echo 0x1d6b > idVendor\n" +
                        "echo 0x0104 > idProduct\n" +
                        "ln -s /config/usb_gadget/g1/functions/ffs.adb  /config/usb_gadget/g1/configs/b.1/f1\n" +
                        "getprop sys.usb.controller >/config/usb_gadget/g1/UDC\n" +
                        "setprop sys.usb.state adb")

                if (revertChangesResult1.isSuccess) {                         // check if the exit-code was 0
                    println(revertChangesResult1.stdout())}
				else{
					Toast.makeText(activity,"Something went wrong while stopping", Toast.LENGTH_SHORT).show()
				}
                
				tempShell.shutdown()
	
	 
	 
	 }
	 
	 private fun showDefaultDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
           // setIcon(R.drawable.ic_hello)
            setTitle("Select a File Picker Type")
            setMessage("If you dont have a third-party file manager(i.e MixExplorer), selecting deafult may result in garbage. In this case, select \"Fiolc\"(not the best looking)")
            setPositiveButton("Fiolc") { _, _ ->
               // toast("clicked positive button")
			   selectedFilePicker = 2
             val intent = Intent(getActivity(), PickerActivity::class.java)
		     startActivity(intent)
            }
            setNegativeButton("Default") { _, _ ->
               
		    val intent = Intent(Intent.ACTION_PICK)
           intent.type = "*/*"
           startActivityForResult(intent, 565)	 			 		 		
			  selectedFilePicker = 1 
            }
            setNeutralButton("Dont Show this again") { _, _ ->
                
			     showDialog = false
            }
        }.create().show()
      }

    override fun onResume() {
    super.onResume()

    // Update the dark mode state
    
	val isDarkMode = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    if (!isDarkMode) {
        appbar.setBackgroundColor(requireContext().getColor(R.color.black))
    } 
	
	}
	
/*
fun changeAppBarColorToDark(appBar: AppBar, context: Context) {
    val isDarkMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    if (!isDarkMode) {
        appBar.setBackgroundColor(context.getColor(R.color.black))
    } 
}

*/
	 
}