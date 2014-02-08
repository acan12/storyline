package app.xzone.storyline.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;
import app.xzone.storyline.HomeActivity;
import app.xzone.storyline.R;
import app.xzone.storyline.worker.Api.ApiKey;
import app.xzone.storyline.worker.AuthenticationWorker;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookAdapter {

	public static Session openConnection(final Activity a) {
		return Session.openActiveSession(a, true, new Session.StatusCallback() {

			@SuppressWarnings("deprecation")
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
										String userEmail = user.asMap()
												.get("email").toString();
										TextView welcome = (TextView) a
												.findViewById(R.id.welcome);
										welcome.setText("Hello "
												+ user.getName() + " ! \n "
												+ userEmail);

										// save fb avatar link
										SharedPreferences settings = a
												.getSharedPreferences(
														"facebook",
														Activity.MODE_WORLD_READABLE);
										SharedPreferences.Editor editor = settings
												.edit();
										editor.putString("avatar",
												getAvatarLink(user.getId()));
										editor.commit();

										Toast.makeText(a, "Login Success with "
														+ userEmail, 200)
												.show();

										// call api tracore
										String response2 = (String) AuthenticationWorker.getInstance(ApiKey.REGISTER_API, session.getAccessToken()).callApi();
										
										//go to homeactivity
										Intent goTimeline = new Intent(a,
												HomeActivity.class);
										a.startActivity(goTimeline);
										a.finish();
									}
								}
							});

				}

			}

		});

	}

	private static String getAvatarLink(String id) {
		return "http://graph.facebook.com/" + id + "/picture?type=small";
	}

}
