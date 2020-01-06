import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Spotify {

	public static void main(String[] args) throws IOException {
		String dir = System.getProperty("user.dir") + "/lyrics.txt";
		String StopWordsPath = System.getProperty("user.dir") + "/stopwords.txt";
		ArrayList<String> lyrics = ReadFile.readTxt(dir);
		//Lemmatization and Removing Stop Words
		Lemmatize obj = new Lemmatize();
		ArrayList<ArrayList<String>> clean_lyrics = new ArrayList<ArrayList<String>>();
		//for even more efficiency
		//Create 2-grams to append
		FileWriter writer = new FileWriter("PPLyrics.txt");
		for(String lyric : lyrics) {
			//Lemmatizing and removing stopwords in one go
			ArrayList<String> proc = ReadFile.stripPunc(ReadFile.removeStop(obj.lemmatization(lyric), StopWordsPath));
			//Create 2-gram and add
			//proc.addAll(ReadFile.nGram(1, proc));
			StringBuilder sb = new StringBuilder();
			for(String s : proc) {
				sb.append(s);
				sb.append(" ");
			}
			sb.append("\n");
			writer.write(sb.toString());
		}
		System.out.println("Done");
		writer.close();
		//Named Entity Recognition Section
		ArrayList<String> named_entities = NER.nerList(lyrics);
		Map<String, Integer> entity_count = Ranking.freqMap(named_entities);
		TreeMap<Integer, ArrayList<String>> ranking = Ranking.countOccur(entity_count);
		FileWriter ner_writer = new FileWriter("NamedEntityRanking.txt");
		for(int key: ranking.descendingKeySet()) {
			String output = Integer.toString(key) + "Occurences: " + ranking.get(key).toString().replaceAll("\\[.*?\\]", "").trim() + "\n";
			ner_writer.write(output);
		}
		ner_writer.close();
		ArrayList<String> nouns = NER.nounList(lyrics);
		Map<String, Integer> nouns_count = Ranking.freqMap(nouns);
		TreeMap<Integer, ArrayList<String>> noun_occur = Ranking.countOccur(nouns_count);
		FileWriter noun_writer = new FileWriter("NounRanking.txt");
		for(int key: noun_occur.descendingKeySet()) {
			String output = Integer.toString(key) + "Occurences: " + noun_occur.get(key).toString().replaceAll("\\[.*?\\]", "").trim() + "\n";
			noun_writer.write(output);
		}
		noun_writer.close();
	}
	
}

