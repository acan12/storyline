package app.xzone.storyline.component;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import app.xzone.storyline.R;

public class PanelButtons {
	public static boolean showPanel(Activity a, boolean isShow){
		View function = (View) a.findViewById(R.id.footer_function);
		Animation fadeout = AnimationUtils.loadAnimation(a, R.anim.fadeout);
		function.setAnimation(fadeout);

		// buttons panel
		Animation bottomUp = AnimationUtils.loadAnimation(a, R.anim.bottom_up);
		function.startAnimation(bottomUp);
		
		if(!isShow){
			function.setVisibility(View.VISIBLE);
			return true;
		}else{
			function.setVisibility(View.GONE);
			return false;
		}
	}
}
