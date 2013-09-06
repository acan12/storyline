package app.xzone.storyline.helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import app.xzone.storyline.R;
import app.xzone.storyline.model.Event;

public class EventHelper {

	public static Event buildEvent(Activity a, Event event){
		EditText t01 = (EditText) a.findViewById(R.id.titleFieldEvent);
		EditText t02 = (EditText) a.findViewById(R.id.messageFieldEvent);
		Spinner sp01 = (Spinner) a.findViewById(R.id.categorySpinnerEvent);
		Spinner sp02 = (Spinner) a.findViewById(R.id.transportSpinnerEvent);
		
		TextView location = (TextView) a.findViewById(R.id.locationEvent);
		
		event.setName(t01.getText().toString());
		event.setMessage(t02.getText().toString());
		event.setCategory(sp01.getSelectedItem().toString());
		event.setTransportation(sp02.getSelectedItem().toString());
		event.setLocname(location.getText().toString());
		
//		event.setLat(lat)
//		event.setLng(lng)
		return event;
	}
}
