package app.xzone.storyline;



import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class FormActivity extends Activity implements OnClickListener{

	private Button startDateButton;
	private Button startTimeButton;
	private Button endDateButton;
	private Button endTimeButton;
	private DateTime dt = new DateTime();
	
	static final int DATE_DIALOG_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		
		Log.d("info", "testing entrance form activity");
		
		startDateButton = (Button) findViewById(R.id.startDateButton);
		startTimeButton = (Button) findViewById(R.id.startTimeButton);
		endDateButton 	= (Button) findViewById(R.id.endDateButton);
		endTimeButton 	= (Button) findViewById(R.id.endTimeButton);
		
		startDateButton.setOnClickListener(this);
		startTimeButton.setOnClickListener(this);
		endDateButton.setOnClickListener(this);
		endTimeButton.setOnClickListener(this);
		
	}

	public void showCustomDateTimeDialog(){
		DateTime dt = new DateTime();
		
//		
		
//		DatePickerDialog dp = new DatePickerDialog(this, new OnDateSetListener() {
//			
//			@Override
//			public void onDateSet(DatePicker view, int year, int month,
//					int day) {
//				// TODO Auto-generated method stub
//				
//				DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd,yyyy");
//				DateTime dt2 = new DateTime(year, month, day, 0, 0, 0, 0);
//				String datetime = dt2.toString(fmt);
//				
//				EditText startDateText = (EditText) findViewById(R.id.startDateText);
//				startDateText.setText(datetime);
//				
//			}
//		}, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
//		dp.show();
	}
	
	
	
	@Override
	public void onClick(View v) {
		DatePickerDialog dp;
		TimePickerDialog tp;
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.startDateButton:
			Log.d("info", "testing startDateButton");
			
			dp = new DatePickerDialog(this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int month,
						int day) {
					// TODO Auto-generated method stub
					
					DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd,yyyy");
					DateTime dt2 = new DateTime(year, month, day, 0, 0, 0, 0);
					String datetime = dt2.toString(fmt);
					
					EditText startDateText = (EditText) findViewById(R.id.startDateText);
					startDateText.setText(datetime);
					
				}
			}, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
			dp.show();
			
			break;
		case R.id.startTimeButton:
			Log.d("info", "testing startTimeButton");
			
			tp = new TimePickerDialog(this, new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					EditText startTimeText = (EditText) findViewById(R.id.startTimeText);
					startTimeText.setText(hourOfDay+":"+minute);
				}
			}, dt.getHourOfDay(), dt.getMinuteOfHour(), true);
			tp.show();
			
			break;
		case R.id.endDateButton:
			Log.d("info", "testing endDateButton");
			
			dp = new DatePickerDialog(this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int month,
						int day) {
					// TODO Auto-generated method stub
					
					DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd,yyyy");
					DateTime dt2 = new DateTime(year, month, day, 0, 0, 0, 0);
					String datetime = dt2.toString(fmt);
					
					EditText endDateText = (EditText) findViewById(R.id.endDateText);
					endDateText.setText(datetime);
					
				}
			}, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
			dp.show();
			
			break;
		case R.id.endTimeButton:
			tp = new TimePickerDialog(this, new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					EditText endTimeText = (EditText) findViewById(R.id.endTimeText);
					endTimeText.setText(hourOfDay+":"+minute);
				}
			}, dt.getHourOfDay(), dt.getMinuteOfHour(), true);
			tp.show();
			
			
			break;
//		case R.id.bMapPicker:
//			Intent mapIntent = new Intent(this, MapLocationActivity.class); 
//			mapIntent.putExtra(PARAMS_KEY, paramid);
//			startActivityForResult(mapIntent, paramid);
//
//			break; 
//		case R.id.btn_submit:
//			a =new ActivityEvent();
//			DBAdapter db = new DBAdapter(this);
//			
//			
//			if(action > 0){
//				TextView t = (TextView)findViewById(R.id.hidden_value);
//				a.setId(Integer.parseInt(t.getText().toString()));
//				setActivityValue(a);
//				db.updateRecord(a);
//				
//				Intent i = new Intent(this, DetailActivity.class);
//		    	i.putExtra(PARAMS_KEY, a.getId());
//				startActivity(i); 
//				
//			} else {
//				setActivityValue(a);
//				db.insertRecord(a);
//				finish();
//			}
//			
//			break;
//		case R.id.icon_activity:
//			String[] labels = idIcon.getLabels();
//			final int[] icons = idIcon.getIcons();
//			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//			dialog.setTitle("Choose your Activity icon");
//			dialog.setAdapter(getDialogAdapter(labels, icons), new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					ImageView img =(ImageView)findViewById(R.id.icon_activity);
//            		Drawable drawable = getResources().getDrawable(icons[which]);
//            		img.setImageDrawable(drawable);
//            		
//            		//set label icon name
//            		TextView eIcon = (TextView)findViewById(R.id.icon_label);
//            		eIcon.setText(idIcon.getLabels()[which]);
//					
//				}});
//			dialog.show();
//		}
//		
//		if (v == homeButton) {
//			Intent i = new Intent();
//			i.setClass(this, HomeActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(i);
		}
	}

}
