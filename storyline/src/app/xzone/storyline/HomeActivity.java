package app.xzone.storyline;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import app.xzone.storyline.libs.CustomHorizontalScrollView;
import app.xzone.storyline.libs.CustomHorizontalScrollView.SizeCallback;
import app.xzone.storyline.libs.ViewUtils;

public class HomeActivity extends Activity implements OnClickListener {
	private ImageButton _addButton;
	private Intent intent;

	private CustomHorizontalScrollView scrollView;
	private View menu;
	private View app;
	private ImageView btnSlide;
	boolean menuOut = false;
	Handler handler = new Handler();
	int btnWidth;
	private ImageButton _listButton;

	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.main);
		
		 Log.d("info", "testing entrance form activity");
		
		 _addButton = (ImageButton) findViewById(R.id.imgAddButton);
		 _listButton = (ImageButton) findViewById(R.id.menuListButton);
		
		 _addButton.setOnClickListener(this);
		 _listButton.setOnClickListener(this);

		// implement sliding menu

		// LayoutInflater inflater = LayoutInflater.from(this);
		// scrollView = (CustomHorizontalScrollView)
		// inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
		// setContentView(scrollView);
		//
		// menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		// app = inflater.inflate(R.layout.horz_scroll_app, null);
		// ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar);
		//
		// ListView listView = (ListView) app.findViewById(R.id.list);
		// ViewUtils.initListView(this, listView, "Item ", 30,
		// android.R.layout.simple_list_item_1);
		//
		// listView = (ListView) menu.findViewById(R.id.list);
		// ViewUtils.initListView(this, listView, "Menu", 30,
		// android.R.layout.simple_list_item_1);
		//
		// btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
		// btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView,
		// menu));
		//
		// final View[] children = new View[] { menu, app };
		//
		// // Scroll to app (view[1]) when layout finished.
		// int scrollToViewIdx = 1;
		// scrollView.initViews(children, scrollToViewIdx,
		// new SizeCallbackForMenu(btnSlide));


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == _addButton) {
			intent = new Intent(this, FormActivity.class);
		}else if(v == _listButton){
			intent = new Intent(this, HorzScrollWithListMenu.class);
		}

		startActivity(intent);
	}

	// core
	/**
	 * Helper for examples with a HSV that should be scrolled by a menu View's
	 * width.
	 */
	static class ClickListenerForScrolling implements OnClickListener {
		HorizontalScrollView scrollView;
		View menu;
		/**
		 * Menu must NOT be out/shown to start with.
		 */
		boolean menuOut = false;

		public ClickListenerForScrolling(HorizontalScrollView scrollView,
				View menu) {
			super();
			this.scrollView = scrollView;
			this.menu = menu;
		}

		@Override
		public void onClick(View v) {
			Context context = menu.getContext();
			String msg = "Slide " + new Date();
			Toast.makeText(context, msg, 1000).show();
			System.out.println(msg);

			int menuWidth = menu.getMeasuredWidth();

			// Ensure menu is visible
			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {
				// Scroll to 0 to reveal menu
				int left = 0;
				scrollView.smoothScrollTo(left, 0);
			} else {
				// Scroll to menuWidth so menu isn't on screen.
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
			}
			menuOut = !menuOut;
		}
	}

	/**
	 * Helper that remembers the width of the 'slide' button, so that the
	 * 'slide' button remains in view, even when the menu is showing.
	 */
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
			System.out.println("btnWidth=" + btnWidth);
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}
}