package my.com.cnm;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class BgMailer extends Service{

	 static final int SUCCESS =1;
	 static final int FAILURE =2;
	 private static int fcount,scount,counter;
	private static ArrayList<String>Recievers;
	private String msg,sub,Image,name,usrname,password;
	private static boolean sent = false;
	private static boolean wrngdtl=false;
	private BgMailer ActiveInstance;
	public void onCreate() {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "service created",5000).show();
		
		
		super.onCreate();
		
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//Toast.makeText(this, DataHolder., duration)
		//sendMail();
		 
		ActiveInstance=this;
		Recievers = intent.getStringArrayListExtra("recievers");
		Image = intent.getStringExtra("image");
		name = intent.getStringExtra("iname");
		sub = intent.getStringExtra("sub");
		msg = intent.getStringExtra("msg");
		usrname = intent.getStringExtra("usrname").trim().toString();
		password = intent.getStringExtra("password").trim().toString();
		
		counter++;
		new MailTask().execute();
		return super.onStartCommand(intent, flags, startId);
		
	}


	private static void clear(){
		
		fcount=0;
		scount=0;
		counter=0;
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "service destroyed",5000).show();
		super.onDestroy();
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public  void sendMail(ArrayList<String>recvr,String sub,String msg){
  		 Mail m = new Mail(usrname, password);
  		 	
  		 String [] address = new String[recvr.size()];
  			address = recvr.toArray(address);
  	        m.set_to(address);
  	        m.set_from(usrname);
  	        m.set_subject(sub);
  	        m.setBody(msg);
  	       
  	        try {
  	        	 m.addAttachment(Image,name);
  	  	       //m.addAttachment("/sdcard/1.txt");
  	     //     m.addAttachment("/sdcard/bday.jpg");
  	          if(m.send()) {
  	        	
  	        	sent=true;
  	          } else {
  	        	
				sent=false;
  	          }
  	        } catch(javax.mail.AuthenticationFailedException e) {
  	        	//fcount++;
  	        	sent=false;
  	        	wrngdtl=true;
  	        	//Toast.makeText(getApplicationContext(), "UserName/Password Incorrect", Toast.LENGTH_LONG).show();
  	        } catch(Exception e) {
  	      	//fcount++;
	        	sent=false;
  	        	//Toast.makeText(getApplicationContext(), "Email was not sent.", Toast.LENGTH_LONG).show();
    	        } 
  	}


	private class MailTask extends AsyncTask<Void,Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Runtime.getRuntime().gc();
			super.onPreExecute();
		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub\
			counter--;
			if(sent == true){
				Toast.makeText(getApplicationContext(), "Email was sent.", Toast.LENGTH_LONG).show();
				scount++;
 	        	displayNotification(scount, "Successfull", SUCCESS); 
				
			}else if(sent == false ){
				Toast.makeText(getApplicationContext(), "Email was not sent.", Toast.LENGTH_LONG).show();
				fcount++;
				
				if( wrngdtl==true){
					Toast.makeText(getApplicationContext(), "Wrong UserName/Password", Toast.LENGTH_LONG).show();
				//fcount++;
					//displayNotification(fcount, "Failed", FAILURE);
					wrngdtl=false;
				}
				displayNotification(fcount, "Failed", FAILURE);
			}
		/*	if(counter==0){
				clear();
				//ActiveInstance.stopSelf();
			}*/
			super.onPostExecute(result);
		}
		

		@Override
		protected Void doInBackground(Void... params) {
			sendMail(Recievers, sub, msg);
			return null;
		}
		
	}
	
	
	public void displayNotification(int Counter,String msg,int key){
		getApplicationContext();
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Intent notificationIntent = new Intent() ;
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
		
			Notification nf = new Notification(R.drawable.ic_launcher,"Catch n Mail\n"+msg+"\nno. of Images "+Counter,System.currentTimeMillis());
			
			nf.setLatestEventInfo(getApplicationContext(), "Catch n Mail", msg+" "+"\nno. of Images "+ Counter,pi);
			nf.flags |= Notification.FLAG_AUTO_CANCEL;
			nf.vibrate = new long[]{100,250,100,500};
			nm.notify(key,nf);
				
			
	}
}
