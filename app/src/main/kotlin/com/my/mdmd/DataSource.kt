package com.my.mdmd


import android.os.Bundle
import com.my.mdmd.fileItem
import com.jaredrummler.ktsh.*




class DataSource() {
	



fun loadFileTree(fileOrDirectoryString: String): List<fileItem> {
    
	//sanity check if string is empty or not
	if(fileOrDirectoryString.isEmpty()){
		
		return emptyList<fileItem>()
	}
	
	
	var fileItemListObj =  mutableListOf<fileItem>()
	
	var  fileOrDirectoryList  = fileOrDirectoryString.lines()
	val fileOrDirectoryListSize: Int = fileOrDirectoryList.size
	val dot= "."
	var isFile : Boolean
	//var i: Int
	for(i in 0..fileOrDirectoryListSize-1){
		
		
		isFile = fileOrDirectoryList[i].contains(dot)
		
		if(isFile){
			
			
			fileItemListObj.add(
			     fileItem(fileOrDirectoryList[i], R.drawable.file_image)
			)
		}else{
			fileItemListObj.add(
			     fileItem(fileOrDirectoryList[i], R.drawable.my_image)
			)
		}
		
		
		
	}
	
	
	return fileItemListObj
	
   }
	
}