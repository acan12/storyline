package app.xzone.storyline.helper;

import android.app.Activity;
import android.view.View;
import app.xzone.storyline.R;

public class StoryHelper {
	public static Object getEventFromTag(Activity a){
		View v = a.findViewById(R.id.titleStory);
		
		return v.getTag();
	}
}
