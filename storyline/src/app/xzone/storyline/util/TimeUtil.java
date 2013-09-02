package app.xzone.storyline.util;

public class TimeUtil {
	public static String timeZone(int hourOfDay){
		if(hourOfDay >= 12){
			return " PM";
		}else if(hourOfDay < 12 && hourOfDay >= 0){
			return " AM";
		}
		
		return null;
	}
}
