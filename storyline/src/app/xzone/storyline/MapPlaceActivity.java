package app.xzone.storyline;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import app.core.adapter.MapAdapter;
import app.data.factory.IMapFactory;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MapPlaceActivity extends MapActivity implements IMapFactory, OnClickListener{
	/** Called when the activity is first created. Testing */
	ImageButton listButton;
	SlidingMenu menu;
	MapView map;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		
		//implementation sliding 
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setContent(R.layout.sliding_menu);
		menu.toggle();
		
		map = (MapView) findViewById(R.id.map_view);
		new MapAdapter(this);
		
		MapAdapter.loadMarker(map, new double[] { poi[0][0][0], poi[0][0][1] } , R.drawable.resto_restaurant_chinese);  
		MapAdapter.loadMarker(map, new double[] { poi[0][1][0], poi[0][1][1] } , R.drawable.resto_restaurant_chinese)
			.setCenter(MapAdapter.getPoint(poi[0][1][0], poi[0][1][1]));
			
		MapAdapter.loadMarker(map, new double[] { poi[0][2][0], poi[0][2][1] } , R.drawable.resto_restaurant_chinese);
		MapAdapter.loadMarker(map, new double[] { poi[0][2][0], poi[0][2][1] } , R.drawable.resto_restaurant_chinese);
		
		MapAdapter.loadMarker(map, new double[] { poi[1][0][0], poi[1][0][1] } , R.drawable.transport_fillingstation);
		MapController mc = MapAdapter.loadMarker(map, new double[] { poi[1][2][0], poi[1][2][1] } , R.drawable.transport_fillingstation);
		mc.setZoom(14);
		
		listButton = (ImageButton) findViewById(R.id.menuListButton);
		listButton.setOnClickListener(this);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.menuListButton:
			System.out.println("----- masuk map toogle !!");
			menu.toggle();
			break;
		}
		
	}
	
}
