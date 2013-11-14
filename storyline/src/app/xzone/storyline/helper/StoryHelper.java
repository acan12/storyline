package app.xzone.storyline.helper;

import android.app.Activity;
import android.view.View;
import app.xzone.storyline.R;

public class StoryHelper {
	public static Object getEventFromTag(Activity a) {
		View v = a.findViewById(R.id.titleStory);

		return v.getTag();
	}

	public static void setImageStoryType(View thumbnail, String cat) {

		if (cat.equals("Sport")) {
			thumbnail.setBackgroundResource(R.drawable.calendar_green);
		} else if (cat.equals("Food")) {
			thumbnail.setBackgroundResource(R.drawable.calendar_purple);
		} else if (cat.equals("Travel")) {
			thumbnail.setBackgroundResource(R.drawable.calendar_orange);
		} else if (cat.equals("Habit")) {
			thumbnail.setBackgroundResource(R.drawable.calendar_red);
		} 

	}
}
