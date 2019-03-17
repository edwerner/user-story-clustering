package user.story.clustering;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        props.setProperty("pos.model", "english-left3words-distsim.tagger");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		PTBTokenizer ptbt = new PTBTokenizer(new FileReader("smarthome-userstories-3k.csv"), new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext();) {
			// Tokenize each word
			label = (CoreLabel) ptbt.next();
			String word = label.word();
			
			// create an empty Annotation just with the given text
	        Annotation document = new Annotation(word.toLowerCase());

	        // run all Annotators on this text
	        pipeline.annotate(document);
	        
	        System.out.println(document);
	        
	        
//			// Convert all words to lowercase
//			String lowerCaseWord = word.toLowerCase();
//			
//			// tag each word
//	        MaxentTagger maxentTagger = new MaxentTagger("english-left3words-distsim.tagger");
//	        String tag = maxentTagger.tagString(lowerCaseWord);
//	        System.out.println(tag);
		}
	}
}