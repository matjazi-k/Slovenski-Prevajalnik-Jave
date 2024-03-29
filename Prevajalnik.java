import java.io.*;
import java.util.*;

public class Prevajalnik {
	static String[] words = new String[]{"\tče ", "\tdokler ", "\tza ", "\tpočni "};
	static String[] translations = new String[]{"\tif ", "\twhile ", "\tfor ", "\tdo "};
	/*static String[] possiblePrefixes = new String[]{" ", ")", "}","\t","\n"};
	static String[] possibleSufixes = new String[]{" ", "(", "{"};
	static HashMap<String,String> prevodi = new HashMap<String,String>();*/
	
	public static void main(String[] args) {
		String imeDat = args[0];
		String prevedeno = "";
		
		/*for (int i = 0; i < words.length; i++) {
			for (int prefi = 0; prefi < possiblePrefixes.length; prefi++) {
				for (int sufi = 0; sufi < possibleSufixes.length; sufi++) {
					prevodi.put(possiblePrefixes[prefi]+words[i]+possibleSufixes[sufi], possiblePrefixes[prefi]+translations[i]+possibleSufixes[sufi]);
				}
			}
		}*/
		
		
		try {
			BufferedReader bralec = new BufferedReader(new FileReader(imeDat + ".slojava"));
		
			prevedeno = prevediDat(bralec);
			System.out.println(prevedeno);
			
		} catch (FileNotFoundException e) {
			System.out.println("Datoteke " + imeDat + ".slojava ni bilo mogoče najti.\nPreverite, če ste pravilno vnesli ime datoteke.");
		}
		
		try {
			BufferedWriter pisatelj = new BufferedWriter(new FileWriter(imeDat + ".java")); 
			
		} catch (IOException e) {
			System.out.println("Datoteke " + imeDat + ".slojava ni bilo mogoče shraniti.");
		}
	}
	
	public static String prevediDat(BufferedReader reader) {
		StringBuilder content = new StringBuilder();
		String line;
		String translated = "";
		try {
			while ((line = reader.readLine()) != null) {
				/*for (String word: prevodi.keySet()) {
					line.replaceAll(word, prevodi.get(word));
				}*/
				content.append(line);
				content.append(System.lineSeparator());
			}
			
			translated = content.toString();
			
			for (int i = 0; i < words.length; i++) {
				translated = translated.replaceAll(words[i], translations[i]);
			}
			
		} catch (Exception e) {
			System.out.println("Napaka pri branju datoteke.");
			System.out.println(e);
		}
		
		return translated;
	}
}