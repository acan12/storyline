package app.xzone.storyline.model;

import org.apache.commons.lang.ArrayUtils;

import android.util.Log;



public class Icon {
	private static Icon icon;
	
	private static final String[] labels = {

		"Acupuncture", // 1 Health
		"Salon", // 2
		"Dentist", // 3
		"Hospital", // 4
		"Medicine", // 5
		"Sauna", // 6
		"Spa", // 7
		"Therapy", // 8

		"Cafetaria", // 9 Restaurant
		"Fastfood", // 10
		"Restaurant", // 11
		"Restaurant Chinese", // 12
		"Restaurant Italia", // 13
		"Terrace", // 14

		"Volley Beach", // 15 Sport
		"Climbing", // 16
		"Fishing", // 17
		"Diving", // 18
		"Fitness", // 19
		"Hiking", // 20
		"Jogging", // 21
		"Rowboat", // 22
		"Salling", // 23
		"Scubadiving", // 24
		"Snorkeling", // 25
		"Surfing", // 26
		"Swimming", // 27
		"Watercraft", // 28
		"Waterskiing", // 29
		"Windsurfing", // 30
		"Yoga", // 31

		"Bus", // 32 Transportation
		"Car", // 33
		"Carwash", // 34
		"Cycling", // 35
		"Filling Station", // 36
		"Highway", // 37
		"Repair", // 38
		"Taxi", // 39
		"Tunnel" // 40

	};
	
	private static final String[] bizLabels = {
		"7eleven", //1
		"Carefour" //2
	};
	
	
	
	private static final int[] icons = { 
		R.drawable.acupuncture, // 1 Health
		R.drawable.beautysalon, // 2
		R.drawable.dentist, // 3
		R.drawable.hospital_building, // 4
		R.drawable.medicine, // 5
		R.drawable.sauna, // 6
		R.drawable.spa, // 7
		R.drawable.therapy, // 8

		R.drawable.cafetaria, // 9 Restaurant
		R.drawable.fastfood, // 10
		R.drawable.restaurant, // 11
		R.drawable.restaurant_chinese, // 12
		R.drawable.restaurant_italian, // 13
		R.drawable.terrace, // 14

		R.drawable.beachvolleyball, // 15 Sport
		R.drawable.climbing, // 16
		R.drawable.fishing, // 17
		R.drawable.diving, // 18
		R.drawable.fitness, // 19
		R.drawable.hiking, // 20
		R.drawable.jogging, // 21
		R.drawable.rowboat, // 22
		R.drawable.sailing, // 23
		R.drawable.scubadiving, // 24
		R.drawable.snorkeling, // 25
		R.drawable.surfing, // 26
		R.drawable.swimming, // 27
		R.drawable.watercraft, // 28
		R.drawable.waterskiing, // 29
		R.drawable.windsurfing, // 30
		R.drawable.yoga, // 31

		R.drawable.bus, // 32 Transportation
		R.drawable.car, // 33
		R.drawable.carwash, // 34
		R.drawable.cycling, // 35
		R.drawable.fillingstation, // 36
		R.drawable.highway, // 37
		R.drawable.repair, // 38
		R.drawable.taxi, // 39
		R.drawable.tunnel // 40

	};

	private static final int[] bizIcons = {
		R.drawable.seven_eleven, //1
		R.drawable.carefour //2
	};

	
	public static Icon getInstances(){
		if(icon == null){
			icon = new Icon();
		}
		return icon;
	}
	
	public static String[] getLabels() {
		return labels;
	}

	public static int[] getIcons() {
		return icons;
	}

	
	public static String[] getBizLabels() {
		return bizLabels;
	}

	public static int[] getBizIcons() {
		return bizIcons;
	}

	
	/**
	 * @description get index icon from label String
	 * @param label
	 * @return
	 */
	public int getIndexFromLabel(String label) {
		return ArrayUtils.indexOf(getLabels(), label);
	}
	
	public int getIndexFromBizLabel(String label) {
		return ArrayUtils.indexOf(getBizLabels(), label);
	}

	/**
	 * @description get icon Image from label String
	 * @param label
	 * @return
	 */
	public int getIconFromLabel(String label) {
		Log.d("","----------label---"+label);
		int index = ArrayUtils.indexOf(getLabels(), label);
		Log.d("","----------index---"+index);
		return icons[index];
	}
	
	public int getIconFromBizLabel(String label) {

		int index = ArrayUtils.indexOf(getBizLabels(), label);
		return bizIcons[index];
	}

	
	/**
	 * @description get label String from icon Image
	 * @param icon
	 * @return
	 */
	public String getLabelFromIcon(int icon) {

		int index = ArrayUtils.indexOf(getIcons(), icon);
		return labels[index];
	}
	
	public String getLabelFromBizIcon(int icon) {

		int index = ArrayUtils.indexOf(getBizIcons(), icon);
		return bizLabels[index];
	}
}
