import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.stanford.nlp.util.StringUtils;

public class ReadFile {

	public static ArrayList<String> readTxt(String dir) throws FileNotFoundException{
		FileReader directory = new FileReader(dir);
		ArrayList<String> lyrics = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(directory)){
			String lyric = br.readLine();
			while(lyric != null) {
				lyric = lyric.replaceAll("\\[.*?\\]", "").trim();
				
				lyrics.add(lyric);
				lyric = br.readLine();
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return lyrics;
	}
	
	public static ArrayList<String> removeStop(ArrayList<String> string, String path) {
		ArrayList<String> temp = new ArrayList<String>();
		Map<String, Integer> stopmap = ReadFile.createStopList(path);
		for(int i = 0; i < string.size();i++) {
			if (!stopmap.containsKey(string.get(i))) {
				temp.add(string.get(i));
			}
		}
		return temp;
	}
	
	public static Map<String, Integer> createStopList(String path) {
		File stopwords = new File(path);
		String content = null;
		try {
			content = new Scanner(stopwords).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] words = content.split("\\s+");
		//Creating a Map
		ArrayList<String> stop_words = new ArrayList<String>();
		Collections.addAll(stop_words, words);
		Map<String, Integer> stopwords_map = createMap(stop_words);
		return stopwords_map;
	}
	
	public static Map<String, Integer> createMap(ArrayList<String> strings){
		Map<String,Integer> map = new HashMap<String, Integer> ();
		for (String s: strings) {
			if(!map.containsKey(s)) {
				map.put(s, 1);
			}
			else {
				int count = map.get(s);
				map.put(s, count + 1);
			}
		}
		return map;
	}
	
	public static ArrayList<String> stripPunc(ArrayList<String> string) {
		String temp = StringUtils.join(string, " ");
		temp = temp.replaceAll("\\p{Punct}", "");
		temp = temp.replaceAll("[\\n\\t]", "");
		temp = temp.toLowerCase();
		temp = temp.replaceAll("\\d", "");
		String[] list = temp.split("[\\p{Punct}\\s]+");
		ArrayList<String> punc_list = new ArrayList<String>();
		Collections.addAll(punc_list, list);
		return punc_list;
	}
	
	public static ArrayList<String> nGram(int n, ArrayList<String> child) {
		ArrayList<String> ngram_words = new ArrayList<String>();
		for(int i = 0; i < child.size() - n; i++) {
			String ngram = child.get(i);
			for(int j = i+1; j<=i+n;j++) {
				ngram = ngram + "_" + child.get(j);
			}
			ngram_words.add(ngram);
		}
		return ngram_words;
	}
}
