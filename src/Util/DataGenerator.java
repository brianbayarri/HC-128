package Util;

import java.util.Random;

public class DataGenerator {
	
	private static int DATA_LENGTH = 16;
	
	public static String generateData()
	{
		char c;
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray(); 
		StringBuilder sb = new StringBuilder(DATA_LENGTH); 
		Random random = new Random(); 
		
		for (int i = 0; i < DATA_LENGTH; i++) { 
		    c = chars[random.nextInt(chars.length)]; 
		    sb.append(c); 
		} 
		
		return sb.toString();
	}

}
