import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import edu.stanford.nlp.coref.data.Document;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Lemmatize {

	protected StanfordCoreNLP pipeline;
	
	public Lemmatize() {
		Properties props;
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		this.pipeline = new StanfordCoreNLP(props);
	}
	
	public ArrayList<String> lemmatization(String string){
		ArrayList<String> lemmas = new ArrayList<String>();
		
		Annotation document = new Annotation(string);
		this.pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence: sentences) {
			for(CoreLabel token: sentence.get(TokensAnnotation.class)) {
				lemmas.add(token.getString(LemmaAnnotation.class));

			}
		}
		return lemmas;
	}
	
}
