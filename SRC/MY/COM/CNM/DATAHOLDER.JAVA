package my.com.cnm;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class DataHolder {

	
public static final String Catcheds = null;
public static ArrayList <Emails> eList= new ArrayList <Emails>();
public static ArrayList<String> Email_list = new ArrayList<String>();
public static ArrayList<String> manEmail = new ArrayList<String>();
public static ArrayList<String> conEmail = new ArrayList<String>();
public static ArrayList<String>Catched = new ArrayList<String>();
public static ArrayList<String>Autocmplt = new ArrayList<String>();
public static ArrayList<String>RemovedMails = new ArrayList<String>();
public static ArrayList<String> UserDetails = new ArrayList<String>();
public static ArrayList <Bitmap> Images = new ArrayList<Bitmap>();
public static boolean[] selctEmail; 
public static String SUB,MSG,usrname,password;


/**
 * method emailExtractor
 * @param String (from which email has to be extracted)
 */

public static void clearAll(){
	
	Email_list.clear();
	manEmail.clear();
	conEmail.clear();
	Catched.clear();
	RemovedMails.clear();
	Images.clear();
	SUB=null;
	MSG=null;

}

public static void emailExtractor(String crude){
	
String crudeVal= crude;
	
/*	if(crudeVal.charAt(crudeVal.length()-1)!=','){
		crudeVal.append(",");
//		System.out.println(crudeVal);
		
	}		
	int length= crudeVal.length();
	
	for(int j=0, i =0;i<length;i++){
		if(crudeVal.charAt(i)==','){
			if(crudeVal.substring(j,i).contains("@")){
				if(!DataHolder.manEmail.contains(crudeVal.substring( j, i))){
					DataHolder.manEmail.add(new String(crudeVal.substring( j, i)));
				}
				j=i+1;
			}else
				Toast.makeText(null,"Check email_ids you entered" , 5000).show();
		}
	}*/
		

	}
}