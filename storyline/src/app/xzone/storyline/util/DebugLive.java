package app.xzone.storyline.util;

import android.app.Activity;

import com.bugsense.trace.BugSenseHandler;

public class DebugLive {
	private static String API_KEY_BUGSENSE = "b290e2a9";

	public static void init(Activity a) {
		BugSenseHandler.initAndStartSession(a.getApplicationContext(),
				DebugLive.API_KEY_BUGSENSE);
		
		BugSenseHandler.startSession(a);
	}
	
	public static void liveLog(String message){
		BugSenseHandler.setLogging(message);
	}
	
	public static void close(Activity a){
		BugSenseHandler.closeSession(a);
	}
}
