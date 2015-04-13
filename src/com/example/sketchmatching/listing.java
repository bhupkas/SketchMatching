package com.example.sketchmatching;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class listing extends ListActivity{

	private String str1,str2,str3,str4,str5;
	String[] videoFileList = new String[5];
/*	
String[] videoFileList = {
	Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/v1.mp4",
	Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/v3.mp4"
};
*/

public class MyThumbnaildapter extends ArrayAdapter<String>{

 public MyThumbnaildapter(Context context, int textViewResourceId,
   String[] objects) {
  super(context, textViewResourceId, objects);
  // TODO Auto-generated constructor stub
  
  
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
  // TODO Auto-generated method stub
 
  View row = convertView;
  if(row==null){
   LayoutInflater inflater=getLayoutInflater();
   row=inflater.inflate(R.layout.row, parent, false);
  }
 
  TextView textfilePath = (TextView)row.findViewById(R.id.FilePath);
  textfilePath.setText(videoFileList[position]);
  ImageView imageThumbnail = (ImageView)row.findViewById(R.id.Thumbnail);
 
  Bitmap bmThumbnail;
        bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoFileList[position], Thumbnails.MICRO_KIND);
        imageThumbnail.setImageBitmap(bmThumbnail);
 
  return row;
 }

}


  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      str1 = getIntent().getExtras().getString("name1");
      str2 = getIntent().getExtras().getString("name2");
      str3 = getIntent().getExtras().getString("name3");
      str4 = getIntent().getExtras().getString("name4");
      str5 = getIntent().getExtras().getString("name5");
      videoFileList[0] = str1;
      videoFileList[1] = str2;
      videoFileList[2] = str3;
      videoFileList[3] = str4;
      videoFileList[4] = str5;
      setListAdapter(new MyThumbnaildapter(listing.this, R.layout.row, videoFileList));
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {

  	//get selected items
	final Context context = this;    
  	String selectedValue = (String) getListAdapter().getItem(position);
  	Intent intent = new Intent(context, videoclass.class);
  	intent.putExtra("name",selectedValue);
    startActivity(intent);
  	//Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

  }
}
