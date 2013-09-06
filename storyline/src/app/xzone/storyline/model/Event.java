package app.xzone.storyline.model;

import java.io.Serializable;

public class Event extends BaseModel implements Serializable {
	private int id;
	private String name;
	private String message;
	private String category;

	// provide vehicle car, bike, flight, train
	private String transportation;

	// provide flag information if event shared or not (true/false)
	private int status;

	// provide information if story shared or not (true/false)
	private int shared;

	// Location coordinates when event occur + location name
	private String locname;
	private double lat;
	private double lng;

	// Date Time to start & end
	private String startDate;
	
	private Story story;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the transportation
	 */
	public String getTransportation() {
		return transportation;
	}

	/**
	 * @param transportation
	 *            the transportation to set
	 */
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the locname
	 */
	public String getLocname() {
		return locname;
	}

	/**
	 * @param locname the locname to set
	 */
	public void setLocname(String locname) {
		this.locname = locname;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getShared() {
		return shared;
	}

	public void setShared(int shared) {
		this.shared = shared;
	}

	/**
	 * @return the story
	 */
	public Story getStory() {
		return story;
	}

	/**
	 * @param story the story to set
	 */
	public void setStory(Story story) {
		this.story = story;
	}

}
