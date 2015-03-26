package com.example.sketchmatching;

import com.example.sketchmatching.DrawingView;
import com.example.sketchmatching.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;





public class MainActivity extends Activity {

	private DrawingView drawView;
	private float smallBrush, mediumBrush, largeBrush;
	private long start,end;
	private Button btnDisplay ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        //Button function call
        nextButton();
        
        
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
    }

    // Save image on button press
    // saveImage function is in different class
	// Added by bhupkas on 24/3/15 
    private void nextButton()
	{
		
		btnDisplay = (Button) findViewById(R.id.next);

		btnDisplay.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Log.e("a","b");
			drawView.saveImage();
			}
			});
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
