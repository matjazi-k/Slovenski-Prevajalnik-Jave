import java.io.*;
import java.util.*;

public class Prevajalnik {
	static String words_file = "translate.csv";
	static HashMap<String,String> wordsAndTranslations = new HashMap<String, String>();
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
			BufferedReader reader = new BufferedReader(new FileReader(words_file));
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] wandt = line.split(";");
				for (int i = 0; i < possiblePrefixes.length; i++) {
					for (int j = 0; j < possibleSufixes.length; j++) {
						String word = possiblePrefixes[i]+wandt[1]+possibleSufixes[j];
						String transaltion = possiblePrefixes[i]+wandt[0]+possibleSufixes[j];
						wordsAndTranslations.put(word, transaltion);
					}
				}
			}
			
			reader.close();
			
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
				for (String word : wordsAndTranslations.keySet()) {
					if (line.contains(word)) {
						line = line.replaceAll(word, wordsAndTranslations.get(word));
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