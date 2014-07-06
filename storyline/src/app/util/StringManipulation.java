package app.util;

public class StringManipulation {
	public static String ellipsis(final String text, int length){
		if(text.length() < length) return text;
		return text.substring(0, length - 3) + "...";
	}
}
