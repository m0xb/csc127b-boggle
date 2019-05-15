package edu.arizona.cs.steve.boggle;

import static org.junit.Assert.*;
import org.junit.Test;

public class DiceTrayTest {

	private DiceTray tray;
	private HashMap myHash;

	@Test
	public void testStringFound() {
		char[][] tray_char = {  
								{ 'A', 'B', 'C', 'D' }, 
								{ 'E', 'F', 'G', 'H' },
								{ 'I', 'J', 'K', 'L' }, 
								{ 'M', 'N', 'O', 'P' } };

		tray = new DiceTray(tray_char);

		assertTrue(tray.stringFound("ABC"));
		assertTrue(tray.stringFound("AEI"));
		assertTrue(tray.stringFound("AFK"));
		assertTrue(tray.stringFound("DHL"));
		assertTrue(tray.stringFound("AFKP"));

		assertTrue(tray.stringFound("MINO"));
		assertTrue(tray.stringFound("MINOPLHD"));

		assertFalse(tray.stringFound("ABD"));
		assertFalse(tray.stringFound("AFP"));
		assertFalse(tray.stringFound("AEK"));
		
		assertFalse(tray.stringFound("CFOP"));
		assertFalse(tray.stringFound("DGHO"));
		assertFalse(tray.stringFound("KLAB"));
		assertFalse(tray.stringFound("NOPM"));
		assertFalse(tray.stringFound("ABCE"));

		assertFalse(tray.stringFound("KLL"));
		assertFalse(tray.stringFound("DHLPA"));
		assertFalse(tray.stringFound("JKLPONMIA"));
		assertFalse(tray.stringFound("MO"));
		
		assertTrue(tray.stringFound(""));
		assertTrue(tray.stringFound("A"));
		assertTrue(tray.stringFound("B"));
		assertTrue(tray.stringFound("C"));
		assertTrue(tray.stringFound("D"));
		assertTrue(tray.stringFound("E"));
		assertTrue(tray.stringFound("F"));
		assertTrue(tray.stringFound("G"));
		assertTrue(tray.stringFound("H"));
		
		assertTrue(tray.stringFound("IJ"));
		assertTrue(tray.stringFound("KL"));
		assertTrue(tray.stringFound("MN"));
		assertTrue(tray.stringFound("OP"));

	}
	
	@Test
	public void testRickWebCat1() {
		char[][] tray_char = {  
								{ 'A', 'B', 'S', 'E' }, 
								{ 'I', 'M', 'T', 'N' },
								{ 'N', 'D', 'E', 'D' }, 
								{ 'S', 'S', 'E', 'N' } };
		tray = new DiceTray(tray_char);

		assertTrue(tray.stringFound("ABSENTMINDE"));
		assertTrue(tray.stringFound("ABSENTMINDED"));
		assertTrue(tray.stringFound("ABSENTMINDEDE"));
		assertTrue(tray.stringFound("ABSENTMINDEDNESS"));
		
		assertFalse(tray.stringFound("ABSENTMINDEDED"));
		
		assertTrue(tray.stringFound("DEDS"));
		assertTrue(tray.stringFound("DEDSS"));
		assertTrue(tray.stringFound("DEDSS"));
		assertTrue(tray.stringFound("SMIDEE"));
		assertTrue(tray.stringFound("TENS"));
		assertTrue(tray.stringFound("DEET"));
		
	}
	
	@Test
	public void testRickWebCat2() {
		char[][] tray_char = {  
								{ 'M', 'O', 'S', 'E' }, 
								{ 'D', 'A', 'L', 'N' },
								{ 'T', 'O', 'P', 'D' }, 
								{ 'S', 'S', 'E', 'N' } };
		tray = new DiceTray(tray_char);

		assertTrue(tray.stringFound("DOE"));
		
		assertTrue(tray.stringFound("POTS"));
		assertTrue(tray.stringFound("POTSS"));
		
		assertFalse(tray.stringFound("POTSSS"));
		assertFalse(tray.stringFound("MOSENDNESSTDM"));
		
		assertTrue(tray.stringFound("NEOPAL"));
		assertTrue(tray.stringFound("MOSENDNESSTD"));
		assertTrue(tray.stringFound("POE"));
		assertTrue(tray.stringFound("TOES"));
		
	}
	
	@Test
	public void testStringFoundDoubleBack() {
		char[][] tray_char = {  
								{ 'A', 'B', 'C', 'D' }, 
								{ 'E', 'F', 'G', 'H' },
								{ 'I', 'J', 'K', 'L' }, 
								{ 'M', 'N', 'O', 'P' } };

		tray = new DiceTray(tray_char);

//		System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
		assertFalse(tray.stringFound("ABCB"));

		assertFalse(tray.stringFound("ABA"));
		assertFalse(tray.stringFound("AFKF"));
		assertFalse(tray.stringFound("AFKPP"));
		assertFalse(tray.stringFound("JKLGK"));
		assertTrue(tray.stringFound("ABCDHLPONMIE"));
		assertFalse(tray.stringFound("ABCDHLPONMIEA"));

	}
	@Test
	public void testRandomDiceRoll(){
		tray = new DiceTray();
		
	}
	
	@Test
	public void testQReplacement() {
		char[][] tray_char = {  
								{ 'Q', 'E', 'C', 'D' }, 
								{ 'E', 'N', 'G', 'H' },
								{ 'I', 'J', 'K', 'L' }, 
								{ 'M', 'N', 'O', 'P' } };

		tray = new DiceTray(tray_char);

		assertTrue(tray.stringFound("Queen"));
		assertTrue(tray.stringFound("Qeen"));
		assertTrue(tray.stringFound("Jenque"));

	}
	
	@Test
	public void testSpecialCase() {
		char[][] tray_char = {  
				{ 'S', 'Y', 'N', 'R' }, 
				{ 'R', 'A', 'R', 'A' },
				{ 'K', 'N', 'B', 'D' }, 
				{ 'U', 'I', 'U', 'G' } };

		tray = new DiceTray(tray_char);

		assertTrue(tray.stringFound("say"));
		assertTrue(tray.stringFound("array"));
		assertTrue(tray.stringFound("arrays"));
		assertTrue(tray.stringFound("barnyard"));
		assertTrue(tray.stringFound("drank"));
		assertTrue(tray.stringFound("nark"));
		assertTrue(tray.stringFound("saran"));
		assertTrue(tray.stringFound("unbars"));
		assertTrue(tray.stringFound("yarn"));
		
		assertFalse(tray.stringFound("bards"));
		assertFalse(tray.stringFound("guns"));
		assertFalse(tray.stringFound("rapid"));
		assertFalse(tray.stringFound("velocity"));
	}
	
	///*
	@Test
	public void testHashClass() {
		myHash = new HashMap(10);
		
		myHash.put("agf");
		myHash.put("hello");
		myHash.put("world");
		myHash.put("again");

		myHash.get("agf");
		assertNotNull(myHash.get("agf"));
		myHash.get("hello");
		assertNotNull(myHash.get("hello"));
		myHash.get("world");
		assertNotNull(myHash.get("world"));
		myHash.get("again");
		assertNotNull(myHash.get("again"));
		
		assertNull(myHash.get("fish"));
		assertNull(myHash.get("HELLO"));
		assertNull(myHash.get("hello."));
		assertNull(myHash.get(""));
		
		myHash.put("HELLO");
		assertNotNull(myHash.get("HELLO"));
		
	}
	/**/

}
