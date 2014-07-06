package app.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewUtil {
	
	public static String getValueOfEditText(View root, int resId) {
		EditText edit = (EditText) root.findViewById(resId);
		if (edit == null) {
			throw new RuntimeException("View not found id=" + resId);
		}
		return edit.getText().toString();
	}
	
	/**
     * show toast
     * @param context
     * @param resId is R.string.xxxx to show as toast
     */
    public static void showToast(Context context, int resId) {
    
        showToast(context, context.getString(resId));
    }
    
    /**
     * show toast
     * @param context
     * @param message 
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
