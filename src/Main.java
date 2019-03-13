
import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		PTBTokenizer ptbt = new PTBTokenizer(new FileReader("smarthome-userstories-3k.csv"), new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext();) {
			// Tokenize each word
			label = (CoreLabel) ptbt.next();
			String word = label.word();
			
			// Convert all words to lowercase
			String lowerCaseWord = word.toLowerCase();
			
			// tag each word
	        MaxentTagger maxentTagger = new MaxentTagger("english-left3words-distsim.tagger");
	        String tag = maxentTagger.tagString(lowerCaseWord);
	        System.out.println(tag);
		}
	}
}