package app.xzone.storyline.model;

import org.apache.commons.lang.ArrayUtils;


import app.xzone.storyline.R;
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
		R.drawable.health_acupuncture, // 1 Health
		R.drawable.health_beautysalon, // 2
		R.drawable.health_dentist, // 3
		R.drawable.health_hospital, // 4
		R.drawable.health_medicine, // 5
		R.drawable.health_sauna, // 6
		R.drawable.health_spa, // 7
		R.drawable.health_therapy, // 8

		R.drawable.resto_cafetaria, // 9 Restaurant
		R.drawable.resto_fastfood, // 10
		R.drawable.resto_restaurant, // 11
		R.drawable.resto_restaurant_chinese, // 12
		R.drawable.resto_restaurant_italian, // 13
		R.drawable.resto_terrace, // 14

		R.drawable.sport_beachvolleyball, // 15 Sport
		R.drawable.sport_climbing, // 16
		R.drawable.sport_fishing, // 17
		R.drawable.sport_diving, // 18
		R.drawable.sport_fitness, // 19
		R.drawable.sport_hiking, // 20
		R.drawable.sport_jogging, // 21
		R.drawable.sport_rowboat, // 22
		R.drawable.sport_sailing, // 23
		R.drawable.sport_scubadiving, // 24
		R.drawable.sport_snorkeling, // 25
		R.drawable.sport_surfing, // 26
		R.drawable.sport_swimming, // 27
		R.drawable.sport_watercraft, // 28
		R.drawable.sport_waterskiing, // 29
		R.drawable.sport_windsurfing, // 30
		R.drawable.sport_yoga, // 31

		R.drawable.transport_bus, // 32 Transportation
		R.drawable.transport_car, // 33
		R.drawable.transport_carwash, // 34
		R.drawable.transport_cycling, // 35
		R.drawable.transport_fillingstation, // 36
		R.drawable.transport_highway, // 37
		R.drawable.transport_repair, // 38
		R.drawable.transport_taxi, // 39
		R.drawable.transport_tunnel // 40

	};

//	private static final int[] bizIcons = {
//		R.drawable.seven_eleven, //1
//		R.drawable.carefour //2
//	};

	
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

//	public static int[] getBizIcons() {
//		return bizIcons;
//	}

	
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
		int index = ArrayUtils.indexOf(getLabels(), label);
		return icons[index];
	}
	
//	public int getIconFromBizLabel(String label) {
//
//		int index = ArrayUtils.indexOf(getBizLabels(), label);
//		return bizIcons[index];
//	}

	
	/**
	 * @description get label String from icon Image
	 * @param icon
	 * @return
	 */
	public String getLabelFromIcon(int icon) {

		int index = ArrayUtils.indexOf(getIcons(), icon);
		return labels[index];
	}
	
//	public String getLabelFromBizIcon(int icon) {
//
//		int index = ArrayUtils.indexOf(getBizIcons(), icon);
//		return bizLabels[index];
//	}
}
