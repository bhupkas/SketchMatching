package com.example.sketchmatching;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import com.example.sketchmatching.R;


import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.widget.Button;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

public class DrawingView extends View {
		  
	//drawing path
	private Path drawPath,temp;
	//drawing and canvas paint
	private Paint drawPaint, drawPaint2 ;
	private Paint  canvasPaint2,canvasPaint;
	//initial color
	private int paintColor = this.getResources().getColor(R.color.Black);//0xFF660000;
	//canvas
	private Canvas drawCanvas,projection,canvas3;
	//canvas bitmap
	private Bitmap canvasBitmap,canvasBitmap2,canvasBitmap3;
	
	private boolean draw=false;
	
	private float brushSize, lastBrushSize;
	
	private AssetManager assetManager;
	
	private Context context;
	//private nativeShadow ns;
	boolean nahi=false;
	
	public DrawingView(Context context_, AttributeSet attrs){
		super(context_, attrs);
		setupDrawing();
	    context = context_;
	    assetManager = context.getAssets();
	  //  ns = new nativeShadow(assetManager);
	
	}
	private void setupDrawing(){
		//get drawing area setup for interaction 
		brushSize = getResources().getInteger(R.integer.small_size);
		lastBrushSize = brushSize;
		drawPath = new Path();
		temp = new Path();
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(brushSize);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		
		drawPaint2 = new Paint();
		drawPaint2.setColor(Color.BLACK);
		drawPaint2.setAntiAlias(true);
		drawPaint2.setDither(true);
		drawPaint2.setStrokeWidth(brushSize);
		drawPaint2.setStyle(Paint.Style.STROKE);
		drawPaint2.setStrokeJoin(Paint.Join.ROUND);
		drawPaint2.setStrokeCap(Paint.Cap.ROUND);
		
		canvasPaint = new Paint(Paint.DITHER_FLAG);
		canvasPaint2 = new Paint(Paint.DITHER_FLAG);
		//canvasPaint2.setColor(Color.WHITE);
		//canvasPaint.setColor(Color.WHITE);
	}
	public void startNew(){
	    drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
	    projection.drawColor(0, PorterDuff.Mode.CLEAR);
	    canvas3.drawColor(0, PorterDuff.Mode.CLEAR);
	    invalidate();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	//view given size
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		canvasBitmap.eraseColor(Color.WHITE);
		drawCanvas = new Canvas(canvasBitmap);
		canvasBitmap2 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		canvasBitmap2.eraseColor(Color.WHITE);
		projection = new Canvas(canvasBitmap2);
		canvasBitmap3 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		canvasBitmap3.eraseColor(Color.WHITE);
		canvas3 = new Canvas(canvasBitmap3);
	//	projectShadow();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	//draw view
		if(draw == false){
			canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
			canvas.drawPath(drawPath, drawPaint);
			//saveImage(canvasBitmap);
		}
		else{
			canvas.drawBitmap(canvasBitmap2, 0, 0, canvasPaint2);
			canvas.drawPath(drawPath, drawPaint2);
			//saveImage(canvasBitmap2);
		}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	//detect user touch     
		float touchX = event.getX();
		float touchY = event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(draw == true)
			{
				temp.moveTo(touchX, touchY);
			}
		    drawPath.moveTo(touchX, touchY);
		    break;
		case MotionEvent.ACTION_MOVE:
		    drawPath.lineTo(touchX, touchY);
		    if(draw == true)
			{
				temp.lineTo(touchX, touchY);
			}
		    //projectShadow(touchX,touchY);
		    break;
		case MotionEvent.ACTION_UP:
		    //drawCanvas.drawPath(drawPath, drawPaint);
		    //draw = true;
		    if(draw == false)
	    	{
		    	drawCanvas.drawPath(drawPath, drawPaint);
	    	}
		    else
		    {
		    	projection.drawPath(drawPath, drawPaint);
		    	canvas3.drawPath(temp, drawPaint);
		    }
		   
		    //saveImage(canvasBitmap2);
		    //draw = false;
		    //drawPath.reset();
		    break;
		default:
		    return false;
		}
		invalidate();
		return true;
	}
	
	
	public void setBrushSize(float newSize){
		
		//update size
		float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
			    newSize, getResources().getDisplayMetrics());
			brushSize=pixelAmount;
			drawPaint.setStrokeWidth(brushSize);
	}
	
	public void setLastBrushSize(float lastSize){
	    lastBrushSize=lastSize;
	}
	public float getLastBrushSize(){
	    return lastBrushSize;
	}

	
	//Added  by bhupkas
	// Convert to binary image
	// http://stackoverflow.com/questions/16375471/binarize-image-in-android

	// Global variables
	private static final boolean TRASNPARENT_IS_BLACK = false;
    /**
     * This is a point that will break the space into Black or white
     * In real words, if the distance between WHITE and BLACK is D;
     * then we should be this percent far from WHITE to be in the black region.
     * Example: If this value is 0.5, the space is equally split.  
     */
    private static final double SPACE_BREAKING_POINT = 13.0/30.0;
	
	public static Bitmap convertToMutable(Bitmap imgIn) {
        try {
            //this is the file going to use temporally to save the bytes. 
            // This file will not be a image, it will store the raw image data.
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

            //Open an RandomAccessFile
            //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
            //into AndroidManifest.xml file
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // get the width and height of the source bitmap.
            int width = imgIn.getWidth();
            int height = imgIn.getHeight();
            Config type = imgIn.getConfig();

            //Copy the byte to the file
            //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
            imgIn.copyPixelsToBuffer(map);
            //recycle the source bitmap, this will be no longer used.
            imgIn.recycle();
            System.gc();// try to force the bytes from the imgIn to be released

            //Create a new bitmap to load the bitmap again. Probably the memory will be available. 
            imgIn = Bitmap.createBitmap(width, height, type);
            map.position(0);
            //load it back from temporary 
            imgIn.copyPixelsFromBuffer(map);
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();

            // delete the temp file
            file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 

        return imgIn;
    }

    private static boolean shouldBeBlack(int pixel) {
        int alpha = Color.alpha(pixel);
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);
        if(alpha == 0x00) //if this pixel is transparent let me use TRASNPARENT_IS_BLACK
            return TRASNPARENT_IS_BLACK;
        // distance from the white extreme
        double distanceFromWhite = Math.sqrt(Math.pow(0xff - redValue, 2) + Math.pow(0xff - blueValue, 2) + Math.pow(0xff - greenValue, 2));
        // distance from the black extreme //this should not be computed and might be as well a function of distanceFromWhite and the whole distance
        double distanceFromBlack = Math.sqrt(Math.pow(0x00 - redValue, 2) + Math.pow(0x00 - blueValue, 2) + Math.pow(0x00 - greenValue, 2));
        // distance between the extremes //this is a constant that should not be computed :p
        double distance = distanceFromBlack + distanceFromWhite;
        // distance between the extremes
        return ((distanceFromWhite/distance)>SPACE_BREAKING_POINT);
    }

	private Bitmap convertToBinary(Bitmap b)
	{
		 Bitmap binarizedImage = convertToMutable(b);
        // I will look at each pixel and use the function shouldBeBlack to decide 
        // whether to make it black or otherwise white
        for(int i=0;i<binarizedImage.getWidth();i++) {
            for(int c=0;c<binarizedImage.getHeight();c++) {
                int pixel = binarizedImage.getPixel(i, c);
                if(shouldBeBlack(pixel))
                    binarizedImage.setPixel(i, c, Color.BLACK);
                else
                    binarizedImage.setPixel(i, c, Color.WHITE);
            }
        }
        return binarizedImage;
	}

	// End of conversion to Binary Image
	
	public void saveImage(){
		String imgname,bimgname;
		Bitmap b;
		if(draw == false)
		{
			draw = true;
			//drawPath.reset();
			b = canvasBitmap;
			imgname = "sketchpad1";
			bimgname = "binary1";
		}
		else
		{
			b = canvasBitmap3;
			imgname = "sketchpad2";
			bimgname = "binary2";
		}
		
		Bitmap bmp = Bitmap.createScaledBitmap(b, (int)(b.getWidth()/2), (int)(b.getHeight()/2), false);
		
		String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + 
                "/Download";
		File dir = new File(file_path);
		if(!dir.exists())
		dir.mkdirs();
		File file = new File(dir, imgname  + ".jpg");
		FileOutputStream fOut;
		try {
			this.setDrawingCacheEnabled(true);
			//this.buildDrawingCache();
			fOut = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
			this.setDrawingCacheEnabled(false);
			Log.d("This is the output", file_path);
			try {
				fOut.flush();
				fOut.close();
			}catch (IOException e) {
		        e.printStackTrace();
		    }
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Saving in binary format
		//String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + 
        //        "/Download/test.jpg";
		//Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		//b = bitmap;
		b = convertToBinary(b);		

		Bitmap bmp_binary = Bitmap.createScaledBitmap(b, (int)(b.getWidth()/2), (int)(b.getHeight()/2), false);
		
		File file_binary = new File(dir, bimgname  + ".jpg");
		FileOutputStream fOut_binary;
		try {
			this.setDrawingCacheEnabled(true);
			//this.buildDrawingCache();
			fOut_binary = new FileOutputStream(file_binary);
			bmp_binary.compress(Bitmap.CompressFormat.JPEG, 85, fOut_binary);
			this.setDrawingCacheEnabled(false);
			Log.d("This is the output", file_path);
			try {
				fOut_binary.flush();
				fOut_binary.close();
			}catch (IOException e) {
		        e.printStackTrace();
		    }
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
