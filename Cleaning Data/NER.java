import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NER {

protected StanfordCoreNLP pipeline;
	
	public NER() {
		Properties props;
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
		props.setProperty("ner.fine.regexner.ignorecase", "true");
		this.pipeline = new StanfordCoreNLP(props);
	}
	
	public static ArrayList<String> keepNER(NER nerpipe, String string){
		Map<String, Integer> ner_map = new HashMap<String, Integer>();
		
		CoreDocument sentences = new CoreDocument(string);
		nerpipe.pipeline.annotate(sentences);
		
		for(CoreEntityMention em: sentences.entityMentions()) {
			if(!ner_map.containsKey(em.text())) {
				ner_map.put(em.text(), 1);
			}
		}
		ArrayList<String> ner_list = new ArrayList<String>(ner_map.keySet());
		return ner_list;
	}
	
	public static ArrayList<String> nerList(ArrayList<String> text){
		NER nerpipe = new NER();
		ArrayList<String> dep = new ArrayList<String>();
		for(int i = 0; i < text.size(); i++) {
			ArrayList<String> ner_sent = keepNER(nerpipe,text.get(i));
			dep.addAll(ner_sent);
		}
		return dep;
	}
	
	public static ArrayList<String> nounList(ArrayList<String> text){
		ArrayList<String> nouns = new ArrayList<String>();
		for(int i = 0; i < text.size(); i++) {
			ArrayList<String> noun_sent = keepNouns(text.get(i));
			nouns.addAll(noun_sent);
		}
		return nouns;
	}
	
	public static ArrayList<String> keepNouns(String string){
		NER nnoun = new NER();
		Map<String, Integer> noun_map = new HashMap<String, Integer>();
		Annotation document = new Annotation(string);
		nnoun.pipeline.annotate(document);
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		
		for(CoreMap sentence: sentences) {
			for(CoreLabel token : sentence.get(TokensAnnotation.class)) {
				if((token.get(PartOfSpeechAnnotation.class)).contains("NN")){
					if(!noun_map.containsKey(token.getString(TextAnnotation.class))) {
						noun_map.put(token.getString(TextAnnotation.class), 1);
					}
				}
			}
		}
		ArrayList<String> noun_list = new ArrayList<String>(noun_map.keySet());
		return noun_list;
	}
}
