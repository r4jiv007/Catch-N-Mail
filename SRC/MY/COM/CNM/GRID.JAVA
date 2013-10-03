package my.com.cnm;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Grid extends Activity{
	
	static int counter;
	Button bck,ctch;
	GridView grid1;
	private static String iname;
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	//public static ArrayList <Bitmap> DataHolder.Images = new ArrayList<Bitmap>();
	private Uri fileUri; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);
		init();
		
		if(DataHolder.Images.size()==0){
			Toast.makeText(getApplicationContext(), "No DataHolder.Images to Mail", Toast.LENGTH_LONG).show();
		}else{
			//Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
			grid1.setAdapter(new ImgAdapter());
				
		}
		
		ctch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
			    // create a file to save the image
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
		
	}
	
	public void init(){
		bck = (Button)findViewById(R.id.back);
		ctch  = (Button)findViewById(R.id.cth);
		grid1 =(GridView)findViewById(R.id.grid);
				
	}
	
	
	public void runService(String path){
		Intent bgmail = new Intent(getApplicationContext(),BgMailer.class);
		bgmail.putStringArrayListExtra("recievers", DataHolder.Email_list);
		bgmail.putExtra("image", path);
		bgmail.putExtra("iname", iname);
		bgmail.putExtra("sub", DataHolder.SUB);
		bgmail.putExtra("msg", DataHolder.MSG);
		bgmail.putExtra("usrname", DataHolder.usrname);
		bgmail.putExtra("password", DataHolder.password);
		//bgmail.putStringArrayListExtra("usr", DataHolder.UserDetails);
		startService(bgmail);
	}
	
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	        	
	        	
	        	runService(fileUri.getPath());
	        	
	        	
	        	 Intent mediaScanIntent =new Intent (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	        	 mediaScanIntent.setData (fileUri);
	        	 this.sendBroadcast (mediaScanIntent);  
				    DataHolder.Images.add(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(fileUri.getPath()), 100,100));
	        		grid1.setAdapter(new ImgAdapter());
	        	
	            Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        	 Toast.makeText(this, "Image Capture stoped", Toast.LENGTH_LONG).show();
	        	 DataHolder.Catched.remove(counter-1);
	        	 counter--;
	        } else {
	        	 Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();
	        }
	    }
	}
	
	
	private static Uri getOutputMediaFileUri(int type){
		
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File( Environment.getExternalStorageDirectory().getPath(), "CnM");
	    // This location works best if you want the created DataHolder.Images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	        iname = timeStamp+".jpg";
	        DataHolder.Catched.add(mediaFile.getAbsolutePath());
	        counter++;
	    }else {
	        return null;
	    }

	    return mediaFile;
	}
	
	public class ImgAdapter extends  BaseAdapter
    {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return DataHolder.Images.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v=LayoutInflater.from(getApplicationContext()).inflate(R.layout.img, null);
			
			ImageView img =(ImageView)v.findViewById(R.id.img);
			

			//BitmapFactory.Options options=new BitmapFactory.Options();
			 //options.inJustDecodeBounds=true;
			 //img.setImageBitmap(BitmapLoader.loadBitmap(DataHolder.Catched.get(position).getPath(), 50,100));
			img.setImageBitmap(DataHolder.Images.get(position));
			
			/*img.setMaxHeight(100);
			img.setMaxWidth(100);
			img.setMinimumHeight(100);
			img.setMinimumWidth(100);
			img.setScaleType(ScaleType.FIT_XY); */
			return v;
		}
    	
    }
	
}
