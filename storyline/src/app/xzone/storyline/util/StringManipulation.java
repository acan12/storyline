package app.xzone.storyline.util;

public class StringManipulation {
	public static String ellipsis(final String text, int length){
		
		return text.substring(0, length - 3) + "...";
	}
}
