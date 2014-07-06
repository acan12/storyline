package app.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static String dateFormat(Date date){
		DateFormat df = new SimpleDateFormat("EEE MMM d, yyyy k:mm a");
		return df.format(date);
	}
	
	public static String dateFormat(Date date, String format){
		if(date == null) return "";
		
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static String timeArea(int hourOfDay){
		if(hourOfDay >= 12){
			return " PM";
		}else if(hourOfDay < 12 && hourOfDay >= 0){
			return " AM";
		}
		
		return null;
	}
	
	public static Long toEpochFormat(String mdate, int hour, int minute) throws ParseException{
		Date date = new Date(mdate);
		Time time = new Time(hour+7	, minute, 0);
		
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, (int) time.getTime());
		return calendar.getTime().getTime();
	}
	
	public static Date fromEpochFormat(long epoch){
		if(epoch == 0) return null;
		return new Date(epoch);
	}
}
