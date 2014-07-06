package app.core.component;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class CrosslineOverlay extends Overlay{
	
	private ArrayList geoArrayist;

	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when){
		Projection projection = mapView.getProjection();
		Point center = projection.toPixels(mapView.getMapCenter(), null);
		
		// Customize appearance, should be a fields
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(0xFF000000);
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(2.0F);
		int innerRadius = 10;
		int outerRadius = 10;
		
		canvas.drawLine(center.x, center.y-10, center.x, center.y+10, p);
		canvas.drawLine(center.x-10, center.y, center.x+10, center.y, p);
		
		return true;
	}

}
