package app.xzone.storyline;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import app.xzone.storyline.component.CrosslineOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LocationPickerActivity extends MapActivity {
	int latt = 28426365;
	int lngg = 77320393;

	private MapView mapView;
	private MapController controller;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_picker);

		GeoPoint p = new GeoPoint(latt, lngg);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		mapView.getController().setCenter(p);
		mapView.getController().setZoom(13);

		CrosslineOverlay chOverlay = new CrosslineOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(chOverlay);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private String getAddressString(double lat, double lng) {
		String addressText = "";

		Geocoder coder = new Geocoder(getApplicationContext());
		List<Address> addresses = null;

		try {
			addresses = coder.getFromLocation(lat, lng, 1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (addresses != null && addresses.size() > 0) {
			Address address = addresses.get(0);

			addressText = String.format("%s, %s, %s", address
					.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0)
					: "", address.getLocality(), address.getCountryName());
			Toast.makeText(getBaseContext(), "address: " + addressText,
					Toast.LENGTH_SHORT).show();

		}

		return addressText;
	}

	public void searchPick(View v) {
		double lat = 0;
		double lon = 0;
		String locname = "";

		Geocoder coder = new Geocoder(getApplicationContext());
		EditText searchPicker = (EditText) findViewById(R.id.searchPicker);

		try {
			List<Address> geocodeResults = coder.getFromLocationName(
					searchPicker.getText().toString(), 3);
			Iterator<Address> locations = geocodeResults.iterator();

			int i = 0;
			while (locations.hasNext()) {

				Address loc = locations.next();
				locname += String.format("Location: %f, %f \n",
						loc.getLatitude(), loc.getLongitude());
				lat = loc.getLatitude();
				lon = loc.getLongitude();
				i++;
			}

			GeoPoint newPoint = new GeoPoint((int) (lat * 1E6),
					(int) (lon * 1E6));
			mapView.getController().animateTo(newPoint);

		} catch (IOException e) {
			Log.e("Mapping", "Failed to get location info", e);
		}
	}

	public void donePick(View v) throws IOException {

		GeoPoint mapCenter = mapView.getProjection().fromPixels(
				mapView.getWidth() / 2, mapView.getHeight() / 2);

		double lat = mapCenter.getLatitudeE6() / 1E6;
		double lng = mapCenter.getLongitudeE6() / 1E6;

		String loc = getAddressString(lat, lng);

		// Prepare data intent
		Intent data = new Intent();
		data.putExtra("location", loc);
		data.putExtra("lat", lat);
		data.putExtra("lng", lng);
		setResult(RESULT_OK, data);

		finish();
	}

	public void cancelPick(View v) {
		finish();
	}

}
