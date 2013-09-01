package app.xzone.storyline.worker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import app.xzone.storyline.interfaces.IApi;
import app.xzone.storyline.model.Story;

public class StoryWorker extends BaseWorker implements IStoryWorker {
	private static StoryWorker storyWorker;
	private IApi api;

	protected StoryWorker() {
		this.api = new Api();
		this.start();
	}

	public static StoryWorker getInstance() {
		storyWorker = null;
		if (storyWorker == null) {
			storyWorker = new StoryWorker();
		}
		return storyWorker;
	}

	public synchronized Story[] createApiCall() {
		while (this.isAlive()) {

		}

		stopWorker();
		return getResultWorker();
	}

	protected Story[] runWorker() {

		try {
			return api.getMyStories();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Story[] getResultWorker() {
		Story[] dataBusiness = (Story[]) this.resultWorker;

		return null;
	}

	public void setResultWorker(Story[] dataBusiness) {
		this.resultWorker = dataBusiness;
	}

	/**
	 * @return the businessWorker
	 */
	public static StoryWorker getStoryWorker() {
		return storyWorker;
	}

	/**
	 * @param businessWorker
	 *            the businessWorker to set
	 */
	public static void setStoryWorker(StoryWorker storyWorker) {
		StoryWorker.storyWorker = storyWorker;
	}

}
