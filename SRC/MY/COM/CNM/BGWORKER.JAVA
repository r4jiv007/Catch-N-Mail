package my.com.cnm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;

public class BgWorker extends AsyncTask<Void, Void, Void>{
	
	Context context;
	private ObjectOutputStream obs;
	private FileOutputStream fos;
	private File file;
	private static File dir,idList;
	private FileInputStream fis;
	private ObjectInputStream obsi;

	
	 public BgWorker(Context context){
			this.context=context;
			//file = f;
		}
	 
	 ProgressDialog pd;
	 
	 protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			
   	  pd=ProgressDialog.show(context,"Please Wait", "Loading Contacts");
  	 
  	 pd.setCancelable(false);
			super.onPreExecute();
		}
	 
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		 dir = context.getFilesDir();
	        idList = new File(dir,"emails");
		if(idList.exists() && idList.length()>0){
			deserializeList();
			Details.selctEmail = new boolean[DataHolder.eList.toArray().length]; 
		}else{
			loadMails();
			serializeList();
			Details.selctEmail = new boolean[DataHolder.eList.toArray().length]; 
		}
	        
		return null;
	}
	
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
	//	Toast.makeText(context, ""+DataHolder.Autocmplt.size(),Toast.LENGTH_LONG).show();
		pd.dismiss();
		fill_autocmpt();
		
		super.onPostExecute(result);
	}

	
	public void loadMails(){
		Cursor contacts,emails;
		ContentResolver cr = context.getContentResolver() ;
		int mailcount;
		String email_id=null,name=null,lastid =null,id=null,eid=null;
		Uri cUri=ContactsContract.Contacts.CONTENT_URI,eUri=ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		contacts=cr.query(cUri,null,null,null,null);
	       // System.out.println(System.currentTimeMillis());
	   if(contacts.getCount()>0 ){
		  while(contacts.moveToNext()){
	        	//String emailAddress = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			  name =null;
			   email_id = null;
			  name = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	        	id = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
	        
	        	if(!name.contains("@") ){
	        		emails= cr.query(eUri, new String[]{ContactsContract.CommonDataKinds.Email.DATA}, ContactsContract.CommonDataKinds.Email.CONTACT_ID+" = "+id, null,null);
	        		
	        		if(emails.getCount()>0){
	        			mailcount = emails.getCount();
	        			//while(emails.moveToNext()){
	        			emails.moveToLast();
	        				email_id=emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
	        			//	eid = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email._ID));
	        				
	        	
	        			emails.close();
	        		}
	       		
	        	}
	        	if(name!=null&& email_id!=(null) && email_id.contains("@"))
        		   DataHolder.eList.add(new Emails(name,email_id));
	        	//	DataHolder.Autocmplt.add(email_id);
	        		
        		  
	        	}
	   }
		  contacts.close();
	     //   System.out.println(System.currentTimeMillis());
		
	}
	
 	public void serializeList()  {
		
		try {
			// fos = openFileOutput(fname, MODE_PRIVATE);
			
			fos = new FileOutputStream(idList);
			obs = new ObjectOutputStream(fos);
			obs.writeObject(DataHolder.eList);
			obs.flush();
			obs.close();
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				obs.close();
				fos.close();
			}catch(Exception e){}
			
		}
	}
	

	
	public void deserializeList(){
		Object tolist =null;
		/*String filename ="filelist.bin";
		File file = new File(getFilesDir(), filename);*/

		try {
			//fis = context.openFileInput("emails");
			fis = new FileInputStream(idList);
			obsi = new ObjectInputStream(fis);
			tolist = obsi.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<Emails> tolist2 = (ArrayList<Emails>)tolist;
			DataHolder.eList = tolist2;
			/*int len = DataHolder.eList.size();
			DataHolder.Autocmplt.clear();
			for(int i =0;i<len;i++){
				DataHolder.Autocmplt.add(DataHolder.eList.get(i).getEmail());
			}
			*/
			obsi.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fill_autocmpt(){
		int len = DataHolder.eList.size();
		DataHolder.Autocmplt.clear();
		for(int i =0;i<len;i++){
			DataHolder.Autocmplt.add(DataHolder.eList.get(i).getEmail());
		}
		
	}
}
