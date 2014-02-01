package app.xzone.storyline.adapter;

import com.easy.facebook.android.facebook.FBLoginManager;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import app.xzone.storyline.HomeActivity;
import app.xzone.storyline.MapPlaceActivity;
import app.xzone.storyline.R;
import app.xzone.storyline.component.ProgressCustomDialog;

public class FacebookAdapter {
	
	private static String permissions[] = {
		    "user_about_me",
		    "user_activities",
		    "user_birthday",
		    "user_checkins",
		    "user_education_history",
		    "user_events",
		    "user_groups",
		    "user_hometown",
		    "user_interests",
		    "user_likes",
		    "user_location",
		    "user_notes",
		    "user_online_presence",
		    "user_photo_video_tags",
		    "user_photos",
		    "user_relationships",
		    "user_relationship_details",
		    "user_religion_politics",
		    "user_status",
		    "user_videos",
		    "user_website",
		    "user_work_history",
		    "email",

		    "read_friendlists",
		    "read_insights",
		    "read_mailbox",
		    "read_requests",
		    "read_stream",
		    "xmpp_login",
		    "ads_management",
		    "create_event",
		    "manage_friendlists",
		    "manage_notifications",
		    "offline_access",
		    "publish_checkins",
		    "publish_stream",
		    "rsvp_event",
		    "sms",
		    //"publish_actions",

		    "manage_pages"

		  };
	
	
	public static FBLoginManager SigninWithFacebook(final Activity a, FBLoginManager fbManager) {
		
//		fbManager = new FBLoginManager(a, R.layout.main, a.getResources().getString(R.string.facebook_app_id), permissions);
		
		if(fbManager.existsSavedFacebook()){
			fbManager.loadFacebook();
		}else{
			fbManager.login();
		}
		
		return fbManager;
		
	}
	
	
	public static Session openConnection(final Activity a){
		return Session.openActiveSession(a, true, new Session.StatusCallback(){

			@SuppressWarnings("deprecation")
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if(session.isOpened()){
					
					// make request to the /me API
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
						
						@Override
						public void onCompleted(GraphUser user, Response response) {
							if(user != null){
								TextView welcome = (TextView) a.findViewById(R.id.welcome);
								welcome.setText("Hello "+user.getName()+ " !");
								
								// save fb avatar link 
								SharedPreferences settings = a.getSharedPreferences("facebook", Activity.MODE_WORLD_READABLE);
								SharedPreferences.Editor editor = settings.edit();
								editor.putString("avatar", getAvatarLink(user.getId()));
								editor.commit();
								
								Toast.makeText(a, "Login Success", 200).show();
								
//								// go to homeactivity
								
								Intent goTimeline = new Intent(a, HomeActivity.class);
								a.startActivity(goTimeline);
								a.finish();
							}
						}
					});
					
				}
				
			}
			
		});
		
	}
	
	private static String getAvatarLink(String id){
		return "http://graph.facebook.com/"+id+"/picture?type=small";
	}

}

