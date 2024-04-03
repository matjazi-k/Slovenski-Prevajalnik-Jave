import java.io.*;
import java.util.*;

public class Prevajalnik {
	static String WORDS_FILE = "translate.csv";
	static String[] words = null;
	static String[] translations = null;
	static String[] possiblePrefixes = new String[]{" ", "\\)", "\\}","\t","\n"};
	static String[] possibleSufixes = new String[]{" ", "\\(", "\\{"};
	
	public static void main(String[] args) {
		try {
			String imeDat = args[0];
			String prevedeno = "";
		
			genTranslationCombinations();
			
			try {
				BufferedReader bralec = new BufferedReader(new FileReader(imeDat + ".slojava"));
			
				prevedeno = prevediDat(bralec);
				System.out.println(prevedeno);
				bralec.close();
			} catch (FileNotFoundException e) {
				System.out.println("Datoteke " + imeDat + ".slojava ni bilo mogoče najti.\nPreverite, če ste pravilno vnesli ime datoteke.");
			} catch (IOException e) {
				System.out.println("Datoteke ni mogoče zapreti za branje.");
			}
			
			try {
				BufferedWriter pisatelj = new BufferedWriter(new FileWriter(imeDat + ".java"));
				pisatelj.write(prevedeno, 0, prevedeno.length());
				pisatelj.close();
				System.out.println("Done!");
			} catch (IOException e) {
				System.out.println("Datoteke " + imeDat + ".slojava ni bilo mogoče shraniti.");
			}
		} catch (Exception e) {
			System.out.println("Niste podali imena datoteke!");
		}
	}
	
	public static void genTranslationCombinations() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE));
			String line;
			
			int c = 0;
			while ((line = reader.readLine()) != null) {
				String[] translation = line.split(";");
				for (int i = 0; i < possiblePrefixes.length; i++) {
					for (int j = 0; j < possibleSufixes.length; j++) {
						c++;
					}
				}
			}
			
			reader.close();
			
			BufferedReader reader2 = new BufferedReader(new FileReader(WORDS_FILE));
			String[] wordsTemp = new String[c];
			String[] translationsTemp = new String[c];
			
			while ((line = reader2.readLine()) != null) {
				String[] translation = line.split(";");
				for (int i = 0; i < possiblePrefixes.length; i++) {
					for (int j = 0; j < possibleSufixes.length; j++) {
						c--;
						wordsTemp[c] = (possiblePrefixes[i]+translation[0]+possibleSufixes[j]);
						translationsTemp[c] = (possiblePrefixes[i]+translation[1]+possibleSufixes[j]);
					}
				}
			}
			
			words = wordsTemp;
			translations = translationsTemp;
			
			reader2.close();
			
		} catch (Exception e) {
			System.out.println("Napaka pri branju datoteke translate.csv");
			System.out.println(e);
		}
	}
	
	public static String prevediDat(BufferedReader reader) {
		StringBuilder content = new StringBuilder();
		String line;
		String translated = "";
		try {
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < words.length; i++) {
					if (line.contains(words[i])) {
						//System.out.println(words[i] + " : " + translations[i]);
						line = line.replaceAll(words[i], translations[i]);
					}
				}
				content.append(line);
				content.append(System.lineSeparator());
			}
			
			translated = content.toString();
			
		} catch (Exception e) {
			System.out.println("Napaka pri branju datoteke.");
			System.out.println(e);
		}
		
		return translated;
	}
}