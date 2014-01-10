package app.xzone.storyline.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import app.xzone.storyline.R;

public class AppAdapter {
	private static AppAdapter appAdapter = null;
	private static PackageInfo packageInfo;
	private Context context;

	public AppAdapter(Context context) {
		this.context = context;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static AppAdapter getInstance(Context context) {
		if (appAdapter == null) {
			appAdapter = new AppAdapter(context);
		}
		return appAdapter;
	}

	public String getAppName() {

		return context.getResources().getString(R.string.app_name);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	public static int getAppVersion(Context context)
			throws NameNotFoundException {
		return packageInfo.versionCode;
	}
}
