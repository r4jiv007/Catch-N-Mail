package my.com.cnm;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EmailList extends Activity {

	private ListView email_list;
	Button remove,next2grid;
	//private static ArrayList<Boolean> selction;
	// private static ArrayList<Integer>Indexs= new ArrayList<Integer>();
	public static boolean [] selction;
	static MailAdapter2 adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_list);
		DataHolder.RemovedMails.clear();
		selction = new boolean[DataHolder.Email_list.size()];
	//	Indexs.clear();
		init();
	}
	
	private void init(){
		email_list= (ListView)findViewById(R.id.tosendlist);
		remove = (Button)findViewById(R.id.remove);
		next2grid=(Button)findViewById(R.id.nxt2);
		adapter=new MailAdapter2();
		int len = DataHolder.Email_list.size();
		for(int i=0;i<len;i++){
			selction[i]=false;
		}
		email_list.setAdapter(adapter);
		
		setListeners();
	
			
	}
	
	
	private void setListeners(){
		
		next2grid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Grid.class));
			}
		});
		
		
		remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int len = DataHolder.Email_list.size();
				
				removeMails();
				//int len = DataHolder.Email_list.size();
				for(int i=0;i<len;i++){
					selction[i]=false;
				}
				Toast.makeText(getApplicationContext(), " "+DataHolder.RemovedMails.size()+" "+ DataHolder.Email_list.size(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	public void removeMails(){
		
		String emailtoremove=null;
		int len = DataHolder.RemovedMails.size();
		
		for(int i = 0;i <len;i++){
		
			
			emailtoremove=DataHolder.RemovedMails.get(i);
			if(DataHolder.Autocmplt.contains(emailtoremove)){
				Details.selctEmail[DataHolder.Autocmplt.indexOf(emailtoremove)]=false;
			}
			if(DataHolder.Email_list.contains(emailtoremove)){
				selction[DataHolder.Email_list.indexOf(emailtoremove)]=false;
						
				DataHolder.Email_list.remove(emailtoremove);
			}
			//selction.remove(indx);
			if(DataHolder.manEmail.contains(emailtoremove)){
				DataHolder.manEmail.remove(emailtoremove);
			}
			if(DataHolder.conEmail.contains(emailtoremove)){
				DataHolder.conEmail.remove(emailtoremove);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	
	
	
	class MailAdapter2 extends BaseAdapter{
		
		MailAdapter2(){
			//selction= new ArrayList<Boolean>();
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return DataHolder.Email_list.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		/*	View v = convertView;
        	if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.emails, null);
            }
			TextView name = (TextView)v.findViewById(R.id.dname);
        	TextView email= (TextView)v.findViewById(R.id.demail);
        	
        	name.setText((CharSequence)DataHolder.eList.get(position).getName());
        	email.setText((CharSequence)DataHolder.eList.get(position).getEmail());
        	
			return v;*/
			View v=LayoutInflater.from(getApplicationContext()).inflate(R.layout.txtview2, null);
			TextView mail_list = (TextView)v.findViewById(R.id.allemail);
			CheckBox echck = (CheckBox)v.findViewById(R.id.echck);
			
			echck.setChecked(selction[position]);
			mail_list.setText(DataHolder.Email_list.get(position).toString());
			
			echck.setOnCheckedChangeListener( new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if(selction[position]==true){
						selction[position]=false;
						//Indexs.remove(Integer.valueOf(position));
						DataHolder.RemovedMails.remove(DataHolder.Email_list.get(position).toString());
					}else{
						selction[position]=true;
						//Indexs.add(Integer.valueOf(position));
						DataHolder.RemovedMails.add(DataHolder.Email_list.get(position).toString());
					}
				}
			});
			
			return v;
			
		}
		
	}

}
