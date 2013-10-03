package my.com.cnm;



import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Details extends Activity {
	
	public static ArrayList <Integer> selectedEmail = new ArrayList<Integer>();
	EditText sub,msg;
	public static AutoCompleteTextView eids;
	Button selcnt,enterid,next,clear;
	public static boolean FLAG_MANUALENTRY;
	 static boolean[] selctEmail; 
	public static  ArrayAdapter<String> adapter;
	//public static String [] mails = {"raj@gmail.com","pixma@gmail.com","annim@gajh.com","kiran@gmail.com"};
	ArrayList<String>mails= new ArrayList<String>();
	private static Details  AI;
	
	/*
	public void onBackPressed() {
		if(FLAG_MANUALENTRY){
			selcnt.setVisibility(View.VISIBLE);
			enterid.setVisibility(View.VISIBLE);
			eids.setVisibility(View.GONE);
			FLAG_MANUALENTRY=false;
			}
		else
			finish();
		
	}*/

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        setListeners();
        
        AI = this;
        new BgWorker(AI).execute();
        
        Details.adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.txtview,DataHolder.Autocmplt); 
		Details.adapter.setNotifyOnChange(true);
			Details.eids.setAdapter(Details.adapter);
			Details.adapter.notifyDataSetChanged();
		
      
    }
    
	public void init(){
		sub =(EditText)findViewById(R.id.sub);
		msg =(EditText)findViewById(R.id.msg);
		eids =(AutoCompleteTextView)findViewById(R.id.eids);
		eids.setThreshold(1);
		selcnt =(Button)findViewById(R.id.selcnt);
		enterid =(Button)findViewById(R.id.add);
		next=(Button)findViewById(R.id.nxt);
		clear = (Button)findViewById(R.id.clear);
	}
   
    	public void setListeners(){
    		  
    		eids.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					
					return false;
				}
			});
    		
    		
    		clear.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DataHolder.clearAll();
					sub.setText(null);
					msg.setText(null);
					eids.setText(null);
					int Sel_len = selctEmail.length;
					for(int i = 0;i<Sel_len;i++){
						selctEmail[i] = false;
					}
				}
			});
    		
            next.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				
    				if(!sub.getText().toString().trim().equalsIgnoreCase("") && !msg.getText().toString().trim().equalsIgnoreCase("")){
    					DataHolder.SUB =sub.getText().toString().trim();
    					DataHolder.MSG = msg.getText().toString().trim();
    					DataHolder.Email_list.clear();
    					
    					
    					
    					if(DataHolder.conEmail.size()>0)
    						DataHolder.Email_list.addAll(DataHolder.conEmail);
    					if(DataHolder.manEmail.size()>0)
    						DataHolder.Email_list.addAll(DataHolder.manEmail);
    					
    					
    					if(DataHolder.Email_list.size()>0){
    						/*Intent bgmail = new Intent(getApplicationContext(),BgMailer.class);
    						bgmail.putStringArrayListExtra("recievers", DataHolder.Email_list);
    						bgmail.putExtra("sub", DataHolder.SUB);
    						bgmail.putExtra("msg", DataHolder.MSG);
    						startService(bgmail);*/
    						
    						startActivity(new Intent(getApplicationContext(),EmailList.class));
    					}else
    							Toast.makeText(getApplicationContext(), "Please Select Email_ids" , 3000).show();
    									
    				}else
    					Toast.makeText(getApplicationContext(), "Please Enter Subject and Message" , 3000).show();
    				
    			}
    		});
            
            
            enterid.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				if(!sub.getText().toString().trim().equalsIgnoreCase("") && !msg.getText().toString().trim().equalsIgnoreCase("")){
    				
    					if(!eids.getText().toString().trim().equalsIgnoreCase("")){
    						//DataHolder.emailExtractor(eids.getText().toString());
    						String crudeval = eids.getText().toString().trim();
    						if(crudeval.contains("@")){
    							if(!DataHolder.manEmail.contains(crudeval)){
    								DataHolder.manEmail.add(crudeval);
    								Toast.makeText(getApplicationContext(), eids.getText().toString()+" added " , 3000).show();
    							//	Toast.makeText(getApplicationContext(), ""+DataHolder.manEmail.size() , 3000).show();
    	    						eids.setText("");
    							}
    						}
        					
    						
    					}else{
        					Toast.makeText(getApplicationContext(), "Please Enter Email_id" , 3000).show();

    					}
    				}else
    					Toast.makeText(getApplicationContext(), "Please Enter Subject and Message" , 3000).show();
    				
    			}
    		});
            
            
            
            selcnt.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				if(!sub.getText().toString().trim().equalsIgnoreCase("") && !msg.getText().toString().trim().equalsIgnoreCase("")){
    					//new Wait().execute();
    					showDialog();
    				
    				}else
    					Toast.makeText(getApplicationContext(), "Please Enter Subject and Message" , 3000).show();
    				
    			}
    		});
    	}
       
    
    	
    	public void showDialog(){
    		
    		//DataHolder.Email_list.clear();
    		final Dialog show_cntct = new Dialog(Details.this);
    		show_cntct.setTitle("Select Recievers");
    		show_cntct.setContentView(R.layout.dialog);
    		show_cntct.setCancelable(true);
    		ListView list = (ListView)show_cntct.findViewById(R.id.list);
    		Button ok = (Button)show_cntct.findViewById(R.id.ok);
    		
    		list.setAdapter(new MailAdapter());
    		
    		ok.setOnClickListener(new OnClickListener() {
    		
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				DataHolder.conEmail.clear();
    				int len = DataHolder.eList.toArray().length;
    				for(int i = 0;i<len ;i++){
    					if(selctEmail[i]==true){
    						if(!DataHolder.conEmail.contains(DataHolder.eList.get(i).getEmail())){
    						DataHolder.conEmail.add(DataHolder.eList.get(i).getEmail());
    						//Toast.makeText(getApplicationContext(), DataHolder.Email_list.get(i), 1000).show();
    						}
    					}
    				}
    				show_cntct.dismiss();
    			}
    		});
    	
    		
    		show_cntct.show();
    	}
    	
    	public class MailAdapter extends BaseAdapter{

    			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return DataHolder.eList.toArray().length;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {

				View v=LayoutInflater.from(getApplicationContext()).inflate(R.layout.emails, null);
				CheckBox chek = (CheckBox)v.findViewById(R.id.chck);
				TextView name = (TextView)v.findViewById(R.id.dname);
				TextView email = (TextView)v.findViewById(R.id.demail);
				
				name.setText(DataHolder.eList.get(position).getName().toString());
				email.setText(DataHolder.eList.get(position).getEmail().toString());
				chek.setChecked(selctEmail[position]);
				
				chek.setOnCheckedChangeListener( new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if(selctEmail[position]==true)
							selctEmail[position]=false;
						else
							selctEmail[position]=true;
						
					}
				});
			
				return v;
			}
    		
    	}
    	
    
    	
}