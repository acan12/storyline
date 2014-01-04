package app.xzone.storyline.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import app.xzone.storyline.model.Story;


public interface IApi {
	
	
	public Story[] getMyStories() throws ClientProtocolException,
	IOException;
	
	public String registerApi(Object  params) throws ClientProtocolException,
	IOException;
}
