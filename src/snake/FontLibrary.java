package snake;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontLibrary {
	private static Map<String, Font> fontMap;
		
		static{
			fontMap = new HashMap<String,Font>();
		}
		
		private FontLibrary(){} //disable constructor
		
		//Gets the font if available already, otherwise the font is loaded and returned
		public static Font getFont(String directory, int style, int size) {
			Font font = fontMap.get(directory);
			if(font == null) {
				try {
					font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(directory));
				}
				catch (IOException | FontFormatException e) { System.out.println("Error reading font: " + e); return null; }
				fontMap.put(directory, font);
			}
			return font.deriveFont(style, size);
		}
		
		//Clears out all the fonts from the library
		public static void clearFonts() {
			fontMap.clear();
		}
}
