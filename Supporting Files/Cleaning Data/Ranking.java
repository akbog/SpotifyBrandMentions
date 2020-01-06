import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Ranking {
	
	//Spotify provides all entities found in a list
	//Which we can modify to get only 1 entity per list

	public static TreeMap<Integer, ArrayList<String>> countOccur(Map<String,Integer> map){
		TreeMap<Integer,ArrayList<String>> num_map = new TreeMap<Integer,ArrayList<String>>();
		for(String key : map.keySet()) {
			Integer i = map.get(key);
			if(!num_map.containsKey(i)) {
				num_map.put(i, new ArrayList<String>());
				num_map.get(i).add(key);
			} else {
				num_map.get(i).add(key);
			}
		}
		return num_map;
	}
	
	public static Map<String, Integer> freqMap(ArrayList<String> entities){
		Map<String, Integer> entity_freq = new HashMap<String, Integer>();
		for(String entity: entities) {
			if(entity_freq.containsKey(entity)) {
				int current_count = entity_freq.get(entity);
				entity_freq.put(entity, current_count+=1);
			} else {
				entity_freq.put(entity, 1);
			}
		}
		return entity_freq;
	}
	
	
	public static void printTop(int n,TreeMap<Integer,ArrayList<String>> map) {
		int count = 1;
		for(int key : map.descendingKeySet()) {
			for(int i = 0; i < map.get(key).size();i++) {
				if(count<=n) {
					System.out.println(count + "." + map.get(key).get(i));
					count++;
				} else {
					break;
				}
			}
		}
	}
	
}
