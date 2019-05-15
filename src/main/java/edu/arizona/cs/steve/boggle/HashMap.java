package edu.arizona.cs.steve.boggle;

public class HashMap {

	private int size;
	private String[] array;
	
	public HashMap(int size) {
		this.size = 2*size;
		array = new String[size*2];
	}
	
	public void put(String str) {
		int baseInsert = hashString(str);
		
		int mod = 0;
		while (array[(baseInsert + mod*mod)%size] != null) {
			mod++;		
		}	
//		System.out.println("Insert at pos: " + (baseInsert + mod*mod)%size);
//		System.out.println("  base: " + baseInsert);
//		System.out.println("  mod: " + mod);
		array[(baseInsert + mod*mod)%size] = str;
	}
	
	public String get(String str) {
		
		int baseInsert = hashString(str);
	
		if (array[(baseInsert)%size] == null)
			return null;
		int mod = 0;
		while ( array[(baseInsert + mod*mod)%size] != null 
				&& !(array[(baseInsert + mod*mod)%size].equals(str)) ) {
			mod++;
			//This should prevent an infinite loop
			if (mod > 6) {
				return null;
			}
		}	
//		System.out.println("Found " + array[(baseInsert + mod*mod)%size] + " at pos: " + (baseInsert + mod*mod)%size);
//		System.out.println("  base: " + baseInsert);
//		System.out.println("  mod: " + mod);
		return array[(baseInsert + mod*mod)%size];
		
	}
	
	private int hashString(String key) {
		int out = 0;
		int n = key.length();
		for (int j = 0; j < n; j++)
			out = 37 * out + key.charAt(j);		
		out = out % size;
		if (out < 0)
			out += size;
		return out;
	}
	
}
