package jp.gr.java_conf.walktan;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import jp.gr.java_conf.walktan.R;
import android.widget.ImageView;
import android.widget.TextView; 
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RadioGroup;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

public class MainActivity extends Activity {
	
	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("BusChukyo", "onCreate");

		//タイトル非表示
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//レイアウトXML開始
		setContentView(R.layout.activity_main);

		//DB初期化
		Dao dao = new Dao(this);
		db = dao.getWritableDatabase();

		//フォント変更
		Typeface face = Typeface.createFromAsset(getAssets(), "yasashisa.ttf");
		TextView date = (TextView)findViewById(R.id.date);
		TextView time = (TextView)findViewById(R.id.time);
		TextView dia  = (TextView)findViewById(R.id.dia);
		TextView one = (TextView)findViewById(R.id.one);
		TextView two = (TextView)findViewById(R.id.two);
		TextView three = (TextView)findViewById(R.id.three);
		TextView four = (TextView)findViewById(R.id.four);
		TextView five = (TextView)findViewById(R.id.five);
		TextView six = (TextView)findViewById(R.id.six);
		TextView Result0 = (TextView)findViewById(R.id.Result0);
		TextView Result1 = (TextView)findViewById(R.id.Result1);
		TextView Result2 = (TextView)findViewById(R.id.Result2);
		TextView Result3 = (TextView)findViewById(R.id.Result3);
		TextView Result4 = (TextView)findViewById(R.id.Result4);
		TextView Result5 = (TextView)findViewById(R.id.Result5);
		TextView yet1 = (TextView)findViewById(R.id.yet1);
		TextView yet2 = (TextView)findViewById(R.id.yet2);
		TextView yet3 = (TextView)findViewById(R.id.yet3);
		TextView yet4 = (TextView)findViewById(R.id.yet4);
		TextView yet5 = (TextView)findViewById(R.id.yet5);
		TextView yet6 = (TextView)findViewById(R.id.yet6);
		date.setTypeface(face);
		time.setTypeface(face);
		dia.setTypeface(face);
		one.setTypeface(face);
		two.setTypeface(face);
		three.setTypeface(face);
		four.setTypeface(face);
		five.setTypeface(face);
		six.setTypeface(face);
		Result0.setTypeface(face);
		Result1.setTypeface(face);
		Result2.setTypeface(face);
		Result3.setTypeface(face);
		Result4.setTypeface(face);
		Result5.setTypeface(face);
		yet1.setTypeface(face);
		yet2.setTypeface(face);
		yet3.setTypeface(face);
		yet4.setTypeface(face);
		yet5.setTypeface(face);
		yet6.setTypeface(face);
		

		//画面サイズ取得
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		int width = disp.getWidth();

		//ラジオボタンサイズ調整
		RadioButton bt0 = (RadioButton)findViewById(R.id.radiobutton0);
		RadioButton bt1 = (RadioButton)findViewById(R.id.radiobutton1);
		bt0.setWidth(width/2);
		bt1.setWidth(width/2);

		//大学発選択結果取得（初期設定）
		TextView forward = (TextView)findViewById(R.id.forward);
		forward.setText("");
		forward.setBackgroundResource(R.drawable.dforward);
		SearchResult sResult =new SearchResult();
		int place = 0;
		sResult.result(place);

		//ラジオボタンのボタンの変化を反映
		RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				SearchResult sResult =new SearchResult();
				
				//大学前を選択時
				if(checkedId == R.id.radiobutton0){
					TextView forward = (TextView)findViewById(R.id.forward);
					forward.setText("");
					forward.setBackgroundResource(R.drawable.dforward);
					int place = 0;
					sResult.result(place);               
					//浄水前選択時
				}else if(checkedId == R.id.radiobutton1){
					TextView forward = (TextView)findViewById(R.id.forward);
					forward.setText("");
					forward.setBackgroundResource(R.drawable.jforward);
					int place =1 ;
					sResult.result(place);  
				}
			}
		}
				);
	}
	//時刻表検索結果取得クラス
	public class SearchResult {
		public void result(int place) {
			
			SearchTime sTime = new SearchTime(db);
			int flg = sTime.getDia();
			int hour = sTime.getHour();
			int min = sTime.getMin();
			
			TextView date = (TextView)findViewById(R.id.date);
			date.setText(sTime.getNichi());
			TextView now = (TextView)findViewById(R.id.time);
			now.setText(sTime.getJi());

			Line[] time = new Line[100];
			
			TextView time0 = (TextView)findViewById(R.id.Result0);
			
			if(place==0){
				switch(flg){
				case 1://大学発・日曜ダイヤ（運休日）
					time0.setText("本日は運休です");
					TextView dia1 = (TextView)findViewById(R.id.dia);
					dia1.setText("運休日");
					break;
				case 2://大学発・土曜ダイヤ
					TextView dia2 = (TextView)findViewById(R.id.dia);
					dia2.setText("土曜ダイヤ");
					time = sTime.getDSatDia();
					break;
				case 3://大学発・平日ダイヤ
					TextView dia3 = (TextView)findViewById(R.id.dia);
					dia3.setText("平日ダイヤ");//
					time = sTime.getDWeekDia();
					break;
				case 4://大学発・ 平日補講・集中講義・追再試験日・休講期間
					TextView ddia4 = (TextView)findViewById(R.id.dia);
					ddia4.setText("Bダイヤ(補講・休講期間等)");
					time = sTime.getDHokouDia();
					break;
				case 5://大学発・臨時ダイヤ
					TextView ddia5 = (TextView)findViewById(R.id.dia);
					ddia5.setText("臨時ダイヤ");
					time0.setText("本日は臨時ダイヤです\n当アプリには臨時ダイヤの情報がありません\nALBO等でご確認ください");
					break;
				}
			}
			if(place==1){
				switch(flg){
				case 1:	//浄水発・日曜ダイヤ（運休日）
					TextView dia1 = (TextView)findViewById(R.id.dia);
					dia1.setText("運休日");
					time0.setText("本日は運休です");
					break;
				case 2://浄水発・土曜ダイヤ
					TextView dia2 = (TextView)findViewById(R.id.dia);
					dia2.setText("土曜ダイヤ");
					time = sTime.getJSatDia();
					break;
				case 3://浄水発・平日ダイヤ
					TextView dia3 = (TextView)findViewById(R.id.dia);
					dia3.setText("平日ダイヤ");
					time = sTime.getJWeekDia();
					break;
				case 4://浄水発・ 平日補講・集中講義・追再試験日・休講期間
					TextView dia4 = (TextView)findViewById(R.id.dia);
					dia4.setText("Bダイヤ(補講・休講期間等)");
					time = sTime.getJHokouDia();
					break;
				case 5://浄水発・臨時ダイヤ
					TextView dia5 = (TextView)findViewById(R.id.dia);
					dia5.setText("臨時ダイヤ");
					time0.setText("本日は臨時ダイヤです\n当アプリには臨時ダイヤの情報がありません\nALBO等でご確認ください");
					break;
				}
			}
			if(flg==2 || flg==3 || flg==4){
				int d1 = 0;
				int d2 = 0;
				int d3 = 0;
				int d4 = 0;
				int d5 = 0;
				int d6 = 0;
				int bushour1 = 999999;
				int busmin1 = 999999;
				int bushour2 = 999999;
				int busmin2 = 999999;
				int bushour3 = 999999;
				int busmin3 = 999999;
				int bushour4 = 999999;
				int busmin4 = 999999;
				int bushour5 = 999999;
				int busmin5 = 999999;
				int bushour6 = 999999;
				int busmin6 = 999999;

				for(int n = hour*60 + min; n < 1441; n++){
					for(int i=0; i < time.length; i++){
						if(n == time[i].h*60 + time[i].m){
							d1 = time[i].h*60 + time[i].m;
						}
						if(d1!=0)break;
					}
				}
				bushour1 = d1/60;
				busmin1 = d1%60;

				for(int m = bushour1*60 + busmin1 +1; m < 1442; m++){
					for(int i = 0; i < time.length; i++){
						if(m == time[i].h*60 + time[i].m){
							d2 = time[i].h*60 + time[i].m;
						}
						if(d2!=0)break;
					}
				}
				bushour2 = d2/60;
				busmin2 = d2%60;

				for(int m = bushour2*60 + busmin2 +1; m < 1443; m++){
					for(int i = 0; i < time.length; i++){
						if(m == time[i].h*60 + time[i].m){
							d3 = time[i].h*60 + time[i].m;
						}
						if(d3!=0)break;
					}
				}
				bushour3 = d3/60;
				busmin3 = d3%60;

				for(int m = bushour3*60 + busmin3 +1; m < 1444; m++){
					for(int i = 0; i < time.length; i++){
						if(m == time[i].h*60 + time[i].m){
							d4 = time[i].h*60 + time[i].m;
						}
						if(d4!=0)break;
					}
				}
				bushour4 = d4/60;
				busmin4 = d4%60;

				for(int m = bushour4*60 + busmin4 +1; m < 1445; m++){
					for(int i = 0; i < time.length; i++){
						if(m == time[i].h*60 + time[i].m){
							d5 = time[i].h*60 + time[i].m;
						}
						if(d5!=0)break;
					}
				}
				bushour5 = d5/60;
				busmin5 = d5%60;

				for(int m = bushour5*60 + busmin5 +1; m < 1446; m++){
					for(int i = 0; i < time.length; i++){
						if(m == time[i].h*60 + time[i].m){
							d6 = time[i].h*60 + time[i].m;
						}
						if(d6!=0)break;
					}
				}
				bushour6 = d6/60;
				busmin6 = d6%60;

				int yet1 = bushour1*60 + busmin1 - hour*60 - min;
				int yet2 = bushour2*60 + busmin2 - hour*60 - min;
				int yet3 = bushour3*60 + busmin3 - hour*60 - min;
				int yet4 = bushour4*60 + busmin4 - hour*60 - min;
				int yet5 = bushour5*60 + busmin5 - hour*60 - min;
				int yet6 = bushour6*60 + busmin6 - hour*60 - min;
				TextView y1 = (TextView)findViewById(R.id.yet1);
				TextView y2 = (TextView)findViewById(R.id.yet2);
				TextView y3 = (TextView)findViewById(R.id.yet3);
				TextView y4 = (TextView)findViewById(R.id.yet4);
				TextView y5 = (TextView)findViewById(R.id.yet5);
				TextView y6 = (TextView)findViewById(R.id.yet6);
				TextView one = (TextView)findViewById(R.id.one);
				TextView two = (TextView)findViewById(R.id.two);
				TextView three = (TextView)findViewById(R.id.three);
				TextView four = (TextView)findViewById(R.id.four);
				TextView five = (TextView)findViewById(R.id.five);
				TextView six = (TextView)findViewById(R.id.six);
				TextView time1 = (TextView)findViewById(R.id.Result1);
				TextView time2 = (TextView)findViewById(R.id.Result2);
				TextView time3 = (TextView)findViewById(R.id.Result3);
				TextView time4 = (TextView)findViewById(R.id.Result4);
				TextView time5 = (TextView)findViewById(R.id.Result5);

				if(bushour1==9 && busmin1==21){
					one.setText("");
					one.setBackgroundResource(R.drawable.f1);
					time0.setText("\n・浄水駅発繰り返し運行\n・");
					y1.setText("");
				}else if(bushour2==17 && busmin2==10){
					one.setText("");
					one.setBackgroundResource(R.drawable.f1);
					time0.setText("\n・大学発繰り返し運行\n・");
					y1.setText("");
				}else{
					one.setText("先発");
					String result1 =String.format("%1$2d",bushour1)+"時"+String.format("%1$02d",busmin1)+"分";
					time0.setText(result1);
					String R1 = String.format("%1$2d",yet1)+" min later";
					y1.setText(R1);
				}
				if(bushour2==9 && busmin2==21){
					two.setText("");
					two.setBackgroundResource(R.drawable.f1);
					time1.setText("・\n浄水駅発繰り返し運行\n・");
					y2.setText("");
				}else if(bushour2==17 && busmin2==10){
					two.setText("");
					two.setBackgroundResource(R.drawable.f1);
					time1.setText("・\n大学発繰り返し運行\n・");
					y2.setText("");
				}else{
					two.setText("次発");
					String result2 =String.format("%1$2d",bushour2)+"時"+String.format("%1$02d",busmin2)+"分";
					time1.setText(result2);
					String R2 = String.format("%1$2d",yet2)+" min later";
					y2.setText(R2);
				}
				if(bushour2==17 && busmin2==10){
					three.setText("");
					three.setBackgroundResource(R.drawable.f1);
					time2.setText("\n・大学発繰り返し運行\n・");
					y3.setText("");
				}else{  	            	
					three.setText("3rd");
					String result3 =String.format("%1$2d",bushour3)+"時"+String.format("%1$02d",busmin3)+"分";
					time2.setText(result3);
					String R3 = String.format("%1$2d",yet3)+" min later";
					y3.setText(R3);
				}

				if(bushour2==17 && busmin2==10){
					four.setText("");
					four.setBackgroundResource(R.drawable.f1);
					time3.setText("\n・大学発繰り返し運行\n・");
					y4.setText("");
				}else{
					four.setText("4th");
					String result4 =String.format("%1$2d",bushour4)+"時"+String.format("%1$02d",busmin4)+"分";
					time3.setText(result4);
					String R4 = String.format("%1$2d",yet4)+" min later";
					y4.setText(R4);
				}

				if(bushour2==17 && busmin2==10){
					five.setText("");
					five.setBackgroundResource(R.drawable.f1);
					time4.setText("\n・大学発繰り返し運行\n・");
					y5.setText("");
				}else{
					five.setText("5th");
					String result5 =String.format("%1$2d",bushour5)+"時"+String.format("%1$02d",busmin5)+"分";
					time4.setText(result5);
					String R5 = String.format("%1$2d",yet5)+" min later";
					y5.setText(R5);
				}

				if(bushour2==17 && busmin2==10){
					six.setText("");
					six.setBackgroundResource(R.drawable.f1);
					time5.setText("\n・大学発繰り返し運行\n・");
					y6.setText("");
				}else{ 
					six.setText("6th");
					String result6 =String.format("%1$2d",bushour6)+"時"+String.format("%1$02d",busmin6)+"分";
					time5.setText(result6);
					String R6 = String.format("%1$2d",yet6)+" min later";
					y6.setText(R6);
				}

				if(d1==0){
					one.setText("");
					time0.setText("本日の運行は終了です");
					y1.setText("");
					one.setBackgroundResource(R.drawable.f1);
					two.setText("");
					time1.setText("");
					y2.setText("");
					two.setBackgroundResource(R.drawable.f1);
					three.setText("");
					time2.setText("");
					y3.setText("");
					three.setBackgroundResource(R.drawable.f1);
					four.setText("");
					time3.setText("");
					y4.setText("");
					four.setBackgroundResource(R.drawable.f1);
					five.setText("");
					time4.setText("");
					y5.setText("");
					five.setBackgroundResource(R.drawable.f1);
					six.setText("");
					time5.setText("");
					y6.setText("");
					six.setBackgroundResource(R.drawable.f1);
				}
				if(d1!=0 && d2==0){
					two.setText("");
					time1.setText("本日の運行は以上です");
					y2.setText("");
					two.setBackgroundResource(R.drawable.f1);
					three.setText("");
					time2.setText("");
					y3.setText("");
					three.setBackgroundResource(R.drawable.f1);
					four.setText("");
					time3.setText("");
					y4.setText("");
					four.setBackgroundResource(R.drawable.f1);
					five.setText("");
					time4.setText("");
					y5.setText("");
					five.setBackgroundResource(R.drawable.f1);
					six.setText("");
					time5.setText("");
					y6.setText("");
					six.setBackgroundResource(R.drawable.f1);
				}
				if(d1!=0 && d2!=0 && d3==0){
					three.setText("");
					time2.setText("本日の運行は以上です");
					y3.setText("");
					three.setBackgroundResource(R.drawable.f1);
					four.setText("");
					time3.setText("");
					y4.setText("");
					four.setBackgroundResource(R.drawable.f1);
					five.setText("");
					time4.setText("");
					y5.setText("");
					five.setBackgroundResource(R.drawable.f1);
					six.setText("");
					time5.setText("");
					y6.setText("");
					six.setBackgroundResource(R.drawable.f1);
				}
				if(d1!=0 && d2!=0 && d3!=0 && d4==0){
					four.setText("");
					time3.setText("本日の運行は以上です");
					y4.setText("");
					four.setBackgroundResource(R.drawable.f1);
					five.setText("");
					time4.setText("");
					y5.setText("");
					five.setBackgroundResource(R.drawable.f1);
					six.setText("");
					time5.setText("");
					y6.setText("");
					six.setBackgroundResource(R.drawable.f1);
				}
				if(d1!=0 && d2!=0 && d3!=0 && d4!=0 && d5==0){
					five.setText("");
					time4.setText("本日の運行は以上です");
					y5.setText("");
					five.setBackgroundResource(R.drawable.f1);
					six.setText("");
					time5.setText("");
					y6.setText("");
					six.setBackgroundResource(R.drawable.f1);
				}
				if(d1!=0 && d2!=0 && d3!=0 && d4!=0 && d5!=0 && d6==0){
					six.setText("");
					time5.setText("本日の運行は以上です");
					y6.setText("");
					six.setBackgroundResource(R.drawable.f1);
				}

			}
		}
	}
	@Override
	public void onStart(){
		super.onStart();
		Log.v("BusChukyo", "onStart");
	}

	@Override
	public void onResume(){
		super.onResume();
		Log.v("BusChukyo", "onResume");
	}

	@Override
	public void onPause(){
		super.onPause();
		Log.v("BusChukyo", "onPause");
	}

	@Override
	public void onRestart(){
		super.onRestart();
		Log.v("BusChukyo", "onRestart");
	}

	@Override
	public void onStop(){
		super.onStop();
		Log.v("BusChukyo", "onStop");
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.v("BusChukyo", "onDestroy");
	}
}
