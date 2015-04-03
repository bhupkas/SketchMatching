package com.example.sketchmatching;

import com.example.sketchmatching.DrawingView;
import com.example.sketchmatching.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private DrawingView drawView;
	private float smallBrush, mediumBrush, largeBrush;
	private long start,end;
	private Button btnDisplay ;
	private Button resbtn; 
	private Client sender1,sender2;
	private sndrcv getval;
	private int cnt = 0;
	// Ip value is stored in resultText
	private String resultText;
	//Value we received
	private String res;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        
        //Adding the functionalities
        resButton();
        
        //Button function call
        nextButton();
        
        sender1 = new Client();
        sender2 = new Client();
        getval = new sndrcv();
        drawView = (DrawingView)findViewById(R.id.drawing);
        
        //To change the size of view .
        // Number dont work , change size manually
        
    	LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
    	drawView.getLayoutParams();
    	params.height = 50;
    	drawView.setLayoutParams(params);
        
       //   Generate gen = new Generate();
       //   gen.generate();
    
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        drawView.setBrushSize(smallBrush);
        drawView.setLastBrushSize(smallBrush);
        //drawView.startNew();
     // Calling popup
        showInputDialog();
        
    }
	
	
	// Fetches the value
	private void resButton()
	{
		resbtn = (Button)findViewById(R.id.fetch);
		
		resbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				res = getval.getanswer();
				resbtn.setText(res);
				btnDisplay.setText("Draw 1");
			}
			});
		
	}

    // Save image on button press
    // saveImage function is in different class
	// Added by bhupkas on 24/3/15 
    private void nextButton()
	{
		
		btnDisplay = (Button) findViewById(R.id.next);
		btnDisplay.setText("Draw 1");
		btnDisplay.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.e("a","b");
			drawView.saveImage();
			cnt ++;
			if(cnt%3 == 1)
			{
				resbtn.setText("Result");
				sender1.send("sketchpad1.jpg",resultText);
				btnDisplay.setText("Draw 2");
			}
			else if(cnt%3 == 2)
			{
				sender2.send("sketchpad2.jpg",resultText);
				btnDisplay.setText("Query");
			}
			else
			{
				getval.send("send val",resultText);
				drawView.clearfornext();
				btnDisplay.setText("Wait !");
			}
		}
		});
	}
    
    // pop up dialog boz to get ip
    
	protected void showInputDialog() {

		// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
		alertDialogBuilder.setView(promptView);

		final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
		// setup a dialog window
		alertDialogBuilder.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						resultText = editText.getText().toString();
						Log.e("Value",resultText);
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
