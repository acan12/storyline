package app.xzone.storyline.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
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
		return new Date(epoch);
	}
}
