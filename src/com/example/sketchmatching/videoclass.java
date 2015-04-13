package com.example.sketchmatching;

import com.example.sketchmatching.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class videoclass extends Activity {
		
	private String name;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videolayout);
		name = getIntent().getExtras().getString("name");
		show();
	}
	
	public void show()
	{

	     VideoView videoView =(VideoView)findViewById(R.id.videoView1);  
	       
	             //Creating MediaController  
	     MediaController mediaController= new MediaController(this);  
	         mediaController.setAnchorView(videoView);          
	      
	           //specify the location of media file  
	        Uri uri=Uri.parse(name);          
	             
	           //Setting MediaController and URI, then starting the videoView  
	        videoView.setMediaController(mediaController);  
	        videoView.setVideoURI(uri);          
	        videoView.requestFocus();  
	        videoView.start();  
	}
	
}
