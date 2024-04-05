import java.io.*;
import java.util.*;
import java.lang.*;

public class Prevajalnik {
	static HashMap<String,String> wordsAndTranslations = new HashMap<String, String>();
	static String[] possiblePrefixes = new String[]{" ", ".", "#", ")", "}","\t","\n"};
	static String[] possibleSufixes = new String[]{" ", "(", "{"};
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Niste podali imena datoteke!");
		}
		
		String imeDat = args[0];
		String plang = args[1];
		String lang = "slo";
		String prevedeno = "";
		
		genTranslationCombinations(lang, plang);
		
		try {
			BufferedReader bralec = new BufferedReader(new FileReader(imeDat + "." + lang + plang));
			
			prevedeno = prevediDat(bralec);
			System.out.println(prevedeno);
			bralec.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datoteke " + imeDat + "." + lang + plang + " ni bilo mogoče najti.\nPreverite, če ste pravilno vnesli ime datoteke.");
		} catch (IOException e) {
			System.out.println("Datoteke ni mogoče zapreti za branje.");
		}
			
		try {
			BufferedWriter pisatelj = new BufferedWriter(new FileWriter(imeDat + "." + plang));
			pisatelj.write(prevedeno, 0, prevedeno.length());
			pisatelj.close();
			System.out.println("Done!");
		} catch (IOException e) {
			System.out.println("Datoteke " + imeDat + "." + lang + plang + " ni bilo mogoče shraniti.");
		}
		
		String command = "javac " + imeDat + "." + plang;
		ProcessBuilder pB = new ProcessBuilder();
		pB.command("cmd", "/c", command);
		try {
			Process p = pB.start();
		}
		catch (Exception e) {}
	}
	
	public static void genTranslationCombinations(String lang, String plang) {
		try {
			File file = new File("./languages/"+plang+"/"+lang+".csv");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] wandt = line.split(";");
				for (int i = 0; i < possiblePrefixes.length; i++) {
					for (int j = 0; j < possibleSufixes.length; j++) {
						String word = possiblePrefixes[i]+wandt[0]+possibleSufixes[j];
						String transaltion = possiblePrefixes[i]+wandt[1]+possibleSufixes[j];
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
				line = " "+line;
				for (String word : wordsAndTranslations.keySet()) {
					if (line.contains(word)) {
						line = line.replace(word, wordsAndTranslations.get(word));
					}
				}
				line = line.substring(1,line.length());
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