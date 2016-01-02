package jp.gr.java_conf.walktan;

import java.util.Calendar;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;


//時刻表検索結果取得クラス
public class SearchTime {
	
	public SearchTime(SQLiteDatabase db){
		this.db = db;
	}
	
	private String nichi;
	private String ji;
	private int hour;
	private int min;
	private SQLiteDatabase db;
	
	public int getHour() {
		return hour;
	}

	public int getMin() {
		return min;
	}

	public String getNichi() {
		return nichi;
	}

	public String getJi() {
		return ji;
	}

	public int getDia() {
		int flg = 0;
		//現在時刻取得（端末時間）
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		min = cal.get(Calendar.MINUTE);
		nichi = year + "/" + (month+1) + "/" + day ;
		ji = hour + ":" + String.format("%1$02d", min);
		
		//土曜ダイヤ
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			flg=2;
		}
		//平日ダイヤ
		if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
			flg = 3;
		}
		//日曜ダイヤ(運休)
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)flg=1;
		SQLiteCursor cursor = (SQLiteCursor) db.query("OffDay"
				,new String[] {"year","month","day"}
				,null
				,null
				,null
				,null
				,null
				);
		boolean next = cursor.moveToFirst();
	    while (next) {
	    	if(year==cursor.getInt(0) && month == cursor.getInt(1)-1 && day == cursor.getInt(2) ){
	    		flg = 1;
	    	}
	        next = cursor.moveToNext();
	    }
		cursor.close();

		//平日補講・集中講義・追再試験日・休講期間
		SQLiteCursor cursor2 = (SQLiteCursor) db.query("HokouDay"
				,new String[] {"year","month","day"}
				,null
				,null
				,null
				,null
				,null
				);
		boolean next2 = cursor2.moveToFirst();
	    while (next2) {
	    	if(year==cursor2.getInt(0) && month == cursor2.getInt(1)-1 && day == cursor2.getInt(2) ){
	    		flg = 4;
	    	}
	        next2 = cursor2.moveToNext();
	    }
		cursor2.close();
	
		//臨時ダイヤ
		SQLiteCursor cursor3 = (SQLiteCursor) db.query("ExDay"
				,new String[] {"year","month","day"}
				,null
				,null
				,null
				,null
				,null
				);
		boolean next3 = cursor3.moveToFirst();
	    while (next3) {
	    	if(year==cursor3.getInt(0) && month == cursor3.getInt(1)-1 && day == cursor3.getInt(2) ){
	    		flg = 5;
	    	}
	        next3 = cursor3.moveToNext();
	    }
		cursor3.close();
		
		return flg;
	}
	
	/**
	 * 大学発土曜ダイヤ
	 */
	public Line[] getDSatDia(){
		Line[] time = new Line[100];
		for(int i=0; i<time.length; i++){
			time[i] = new Line();
		}
		
		SQLiteCursor DSatCursor = (SQLiteCursor) db.query("DSatDia"
				,new String[] {"hour","minute"}
				,null
				,null
				,null
				,null
				,"hour, minute"
				,null
				);
		boolean next = DSatCursor.moveToFirst();
		int i=0;
	    while (next) {
	    	time[i].h = DSatCursor.getInt(0);
	    	time[i].m = DSatCursor.getInt(1);
	    	i++;
	        next = DSatCursor.moveToNext();
	    }
		DSatCursor.close();
		return time;
	}
	/**
	 * 大学発平日ダイヤ
	 */
	public Line[] getDWeekDia(){
		Line[] time = new Line[100];
		for(int i=0; i<time.length; i++){
			time[i] = new Line();
		}
		
		SQLiteCursor Cursor = (SQLiteCursor) db.query("DWeekDia"
				,new String[] {"hour","minute"}
				,null
				,null
				,null
				,null
				,"hour, minute"
				,null
				);
		boolean next = Cursor.moveToFirst();
		int i=0;
	    while (next) {
	    	time[i].h = Cursor.getInt(0);
	    	time[i].m = Cursor.getInt(1);
	    	i++;
	        next = Cursor.moveToNext();
	    }
		Cursor.close();
		return time;
	}
	
	/**
	 * 大学発補講ダイヤ
	 */
	public Line[] getDHokouDia(){
		Line[] time = new Line[100];
		for(int i=0; i<time.length; i++){
			time[i] = new Line();
		}
		SQLiteCursor Cursor = (SQLiteCursor) db.query("DHokouDia"
				,new String[] {"hour","minute"}
				,null
				,null
				,null
				,null
				,"hour, minute"
				,null
				);
		boolean next = Cursor.moveToFirst();
		int i=0;
	    while (next) {
	    	time[i].h = Cursor.getInt(0);
	    	time[i].m = Cursor.getInt(1);
	    	i++;
	        next = Cursor.moveToNext();
	    }
		Cursor.close();
		return time;
	}
	/**
	 * 浄水発土曜ダイヤ
	 */
	public Line[] getJSatDia(){
		Line[] time = new Line[100];
		for(int i=0; i<time.length; i++){
			time[i] = new Line();
		}
		SQLiteCursor Cursor = (SQLiteCursor) db.query("JSatDia"
				,new String[] {"hour","minute"}
				,null
				,null
				,null
				,null
				,"hour, minute"
				,null
				);
		boolean next = Cursor.moveToFirst();
		int i=0;
	    while (next) {
	    	time[i].h = Cursor.getInt(0);
	    	time[i].m = Cursor.getInt(1);
	    	i++;
	        next = Cursor.moveToNext();
	    }
		Cursor.close();
		return time;
	}
	/**
	 * 浄水発平日ダイヤ
	 */
	public Line[] getJWeekDia(){
		Line[] time = new Line[100];
		for(int i=0; i<time.length; i++){
			time[i] = new Line();
		}
		SQLiteCursor Cursor = (SQLiteCursor) db.query("JWeekDia"
				,new String[] {"hour","minute"}
				,null
				,null
				,null
				,null
				,"hour, minute"
				,null
				);
		boolean next = Cursor.moveToFirst();
		int i=0;
	    while (next) {
	    	time[i].h = Cursor.getInt(0);
	    	time[i].m = Cursor.getInt(1);
	    	i++;
	        next = Cursor.moveToNext();
	    }
		Cursor.close();
		return time;
	}
	/**
	 * 浄水発補講ダイヤ
	 */
	public Line[] getJHokouDia(){
		Line[] time = new Line[100];
		for(int i=0; i<time.length; i++){
			time[i] = new Line();
		}
		SQLiteCursor Cursor = (SQLiteCursor) db.query("JHokouDia"
				,new String[] {"hour","minute"}
				,null
				,null
				,null
				,null
				,"hour, minute"
				,null
				);
		boolean next = Cursor.moveToFirst();
		int i=0;
	    while (next) {
	    	time[i].h = Cursor.getInt(0);
	    	time[i].m = Cursor.getInt(1);
	    	i++;
	        next = Cursor.moveToNext();
	    }
		Cursor.close();
		return time;
	}
}
