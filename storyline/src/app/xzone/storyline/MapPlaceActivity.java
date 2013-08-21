package app.xzone.storyline;

import android.os.Bundle;
import android.text.Layout;

import com.google.android.maps.MapActivity;

public class MapPlaceActivity extends MapActivity{
	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
