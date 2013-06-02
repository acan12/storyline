package app.xzone.storyline.model;

import java.io.Serializable;

public class Schedule extends BaseModel implements Serializable {
	private int id;
	private String name;
	private String icon;
	private String description;
	
//	Date Time to start & end
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	
//	Location coord to start & end 
	private double startOfLat;
	private double startOfLng;
	private double endOfLat;
	private double endOfLng;
	private int status;
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the startOfLat
	 */
	public double getStartOfLat() {
		return startOfLat;
	}
	/**
	 * @param startOfLat the startOfLat to set
	 */
	public void setStartOfLat(double startOfLat) {
		this.startOfLat = startOfLat;
	}
	/**
	 * @return the startOfLng
	 */
	public double getStartOfLng() {
		return startOfLng;
	}
	/**
	 * @param startOfLng the startOfLng to set
	 */
	public void setStartOfLng(double startOfLng) {
		this.startOfLng = startOfLng;
	}
	/**
	 * @return the endOfLat
	 */
	public double getEndOfLat() {
		return endOfLat;
	}
	/**
	 * @param endOfLat the endOfLat to set
	 */
	public void setEndOfLat(double endOfLat) {
		this.endOfLat = endOfLat;
	}
	/**
	 * @return the endOfLng
	 */
	public double getEndOfLng() {
		return endOfLng;
	}
	/**
	 * @param endOfLng the endOfLng to set
	 */
	public void setEndOfLng(double endOfLng) {
		this.endOfLng = endOfLng;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
