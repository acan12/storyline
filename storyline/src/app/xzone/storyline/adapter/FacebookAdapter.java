package app.xzone.storyline.adapter;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import app.xzone.storyline.HomeActivity;
import app.xzone.storyline.R;
import app.xzone.storyline.worker.Api;
import app.xzone.storyline.worker.AuthenticationWorker;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookAdapter {
	
	public static void SigninWithFacebook(final Activity a,
			final ProgressDialog progress) {

		Session.openActiveSession(a, true, new Session.StatusCallback() {

			@Override
			public void call(final Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {

					// make request to the /me API
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									if (user != null) {
										TextView welcome = (TextView) a
												.findViewById(R.id.welcome);
										welcome.setText("Hi "
												+ user.getName()
												+ "! \n ");
										
										Log.i("AccessToken: ", session.getAccessToken());
									}
									
									
									
									
									
									
									// send register API with facebook token to server backend.
									String response2 = (String) AuthenticationWorker.getInstance(Api.KEY_CALL_REGISTER_API, session.getAccessToken()).callApi();
									
									progress.dismiss();
									Intent intent = new Intent(a,
											HomeActivity.class);
									intent.setAction(Intent.ACTION_VIEW);
									a.startActivity(intent);
								}
							});
				}

			}
		});

		// SharedPreferences prefs = a.getSharedPreferences( fbkey,
		// Context.MODE_PRIVATE );
		// String token = prefs.getString(fbkey, null);
		// if(token == null){
		// System.out.println(" token nothing ,ready to fetch new one!!");
		// Intent intent = new Intent(a, WebViewActivity.class);
		// a.startActivity(intent);
		//
		// prefs.edit().putString(fbkey, fbvalue).commit();
		// System.out.println(" token saved!! token value["+fbvalue+"]");
		// }else{
		// System.out.println(" token already have!! value["+token+"]");
		//
		// }

	}

}
