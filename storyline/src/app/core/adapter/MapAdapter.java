package app.core.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import app.core.component.map.BalloonItemizedOverlay;
import app.core.component.map.MapOverlay;
import app.data.factory.IMapFactory;
import app.xzone.storyline.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapAdapter implements IMapFactory {
	private static Activity activity;
	static GeoPoint point;
	
	public MapAdapter(Activity activity){
		this.activity = activity;
	}
	
	public static MapController loadMarker(MapView map, double[] coordinate, int marker){
		point = getPoint(coordinate[0], coordinate[1]);
		
		MapController mapControl = map.getController();
		Drawable drawable = activity.getResources().getDrawable(marker);
		
		MapOverlay itemizedOverlay = new MapOverlay(drawable, map, false);
		List<Overlay> os = map.getOverlays();
		
		OverlayItem overlayItem = new OverlayItem(point, "dodol", "jawa");
		itemizedOverlay.addOverlay(overlayItem);
		itemizedOverlay.onParentTap(0);
		
		os.add(itemizedOverlay);
		map.setBuiltInZoomControls(true);
		return map.getController();
	}

	public static GeoPoint getPoint(double lat, double lng) {
		return new GeoPoint((int) (lat * 1000000.0), (int) (lng * 1000000.0));
	}

}
