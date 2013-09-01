package app.xzone.storyline.worker;


/**
 * @description parent worker class for call api thread
 * @author arys
 *
 */
public class BaseWorker extends Thread {
	protected Object[] resultWorker = null;

	protected void stopWorker() {
		this.stop();
	}

	public void run() {
		this.setResultWorker(runWorker());
	}

	protected Object[] runWorker() {
		return null;
	}

	/**
	 * @return the object
	 */
	protected Object[] getResultWorker() {
		return resultWorker;
	}

	/**
	 * @param object
	 * the object to set
	 */
	public void setResultWorker(Object[] data) {
		this.resultWorker = data;
	}
}
