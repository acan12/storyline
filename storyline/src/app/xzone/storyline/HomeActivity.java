package app.xzone.storyline;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import app.xzone.storyline.component.Sliding;
import app.xzone.storyline.component.SlidingMenuLeft;
import app.xzone.storyline.libs.CustomHorizontalScrollView;
import app.xzone.storyline.libs.CustomHorizontalScrollView.SizeCallback;

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
	private ImageButton _addEventButton;
	private RelativeLayout _bubleEvent;
	int key = 0;
	int key2 = 0;
	private Sliding popup;
	private Button _submitButton;
	private SlidingMenuLeft popupLeft;

	/** Called when the activity is first created. Testing */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("info", "testing entrance form activity");

		popup = (Sliding) findViewById(R.id.sliding1);
		popup.setVisibility(View.GONE);

		// popupLeft = (SlidingMenuLeft) findViewById(R.id.slidingLeft);
		// popupLeft.setVisibility(View.GONE);

		_addButton = (ImageButton) findViewById(R.id.imgAddButton);
		_addEventButton = (ImageButton) findViewById(R.id.imgAddEvent);
		_listButton = (ImageButton) findViewById(R.id.menuListButton);

		_submitButton = (Button) findViewById(R.id.submitButton);

		_addButton.setOnClickListener(this);
		_addEventButton.setOnClickListener(this);
		_listButton.setOnClickListener(this);

		_submitButton.setOnClickListener(this);
		


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

		Log.d("debug::::", "---------- " + v.getId());
		switch (v.getId()) {
		case R.id.menuListButton:
			intent = new Intent(this, HorzScrollWithListMenu.class);

			startActivity(intent);

			// if(key2==0){
			// key2=1;
			// popupLeft.setVisibility(View.VISIBLE);
			// }
			// else if(key2==1){
			// key2=0;
			// popupLeft.setVisibility(View.GONE);
			// }

			break;

		case R.id.imgAddButton:
			intent = new Intent(this, FormActivity.class);

			startActivity(intent);
			break;

		case R.id.imgAddEvent:
			intent = new Intent(this, EventFormActivity.class);

			startActivity(intent);
			break;

		case R.id.submitButton:
			key = 0;
			popup.setVisibility(View.GONE);
			break;

		}

	}

	// Testing functionality
	public void handleBubleEvent(View v) {
		if (key == 0) {
			key = 1;
			popup.setVisibility(View.VISIBLE);
		} else if (key == 1) {
			key = 0;
			popup.setVisibility(View.GONE);
		}
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