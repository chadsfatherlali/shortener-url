package com.api.shortenerUrl.utils;

public abstract class Id {
	static String chars[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_", "-" };
	
	public static String generate() {
		StringBuilder id = new StringBuilder();
		
		for(int i = 0; i < 10; i++) {
			int position = (int) (Math.random() * (chars.length - 0) + 0);
			id.append(chars[position]);
		}
		
		return id.toString();
	}
}
