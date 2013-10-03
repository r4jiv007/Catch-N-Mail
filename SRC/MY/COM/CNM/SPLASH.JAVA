package my.com.cnm;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Splash extends Activity {

	ImageView image;
	
	String email_id,name,id,lastid=null;
	public static Splash Active_Instance;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        DataHolder.clearAll();
        
        
        image = (ImageView)findViewById(R.id.image);
        Active_Instance = this;
        	//new BgWorker(Active_Instance).execute();
        	/*try {
				//Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
       	//	new Invoke().start();
       		showPDialog();
    }

	/*public void killservice(){
		
	        final ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
	        final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

	        for (RunningServiceInfo runningServiceInfo : services) {
	            if (runningServiceInfo.service.getClassName().equals("BgMailer")){
	                runningServiceInfo
	            }
	        }
	     
	}*/
	
	public void showPDialog(){
		
		//DataHolder.Email_list.clear();
		final Dialog get_login = new Dialog(this);
		get_login.setTitle("Username_Password");
		get_login.setContentView(R.layout.pdialog);
		get_login.setCancelable(false);

		final EditText username = (EditText)get_login.findViewById(R.id.username);
		final EditText password = (EditText)get_login.findViewById(R.id.password);
		
		Button cancel = (Button)get_login.findViewById(R.id.exitapp);
		Button next = (Button)get_login.findViewById(R.id.nxt2detl);
		
		
		next.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataHolder.usrname = username.getText().toString();
				DataHolder.password = password.getText().toString();
				Intent in = new Intent(Splash.this,Details.class);
				startActivity(in);
				get_login.dismiss();
			}
		});
	
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
		
		get_login.show();
	}
	
	
	
	public void storeSecret(){
		
	}
	
	
	
	
	
	
	
	
	
   class Invoke extends Thread{
	   public void run(){
		   try {
			Thread.sleep(1000);
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }
   }

   
   
}
