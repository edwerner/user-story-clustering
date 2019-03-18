package user.story.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Main {

	public static void main(String[] args) throws IOException {

		List<String> wordList = new ArrayList<String>();
		List<String> stopwordList = new ArrayList<String>();
		List<List<String>> allwordsList = new ArrayList<List<String>>();
	
		// read in stopwords from file
		BufferedReader reader = new BufferedReader(new FileReader("default-english-stopwords-list.txt"));
		String line = reader.readLine();
		while (line != null) {
			line = reader.readLine();
			stopwordList.add(line);
		}
		reader.close();
		
		// tag each word
		MaxentTagger maxentTagger = new MaxentTagger("english-left3words-distsim.tagger");

		@SuppressWarnings({ "rawtypes", "unchecked" })
		PTBTokenizer ptbt = new PTBTokenizer(new FileReader("smarthome-userstories-3k.csv"),
				new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext();) {

			// Tokenize each word
			label = (CoreLabel) ptbt.next();
			String word = label.word().toLowerCase();

			if (!stopwordList.contains(word)) {
				// add non-stopwords to word list
				wordList.add(word);
				allwordsList.add(wordList);
				String lemma = label.lemma();
				
				// regex parts of speech tagging
				String tag = maxentTagger.tagString(word);

				// regex parts of speech tagging for
				// nouns, verbs, adjectives, and adverbs
				boolean pos = tag.matches(".*(_NN).*|.*(_NNS).*|.*(_VB).*|.*(_JJ).*|.*(_RB).*");
				
				// add tag to word list
				if (pos == true) {
					wordList.add(tag);
					System.out.println("TAG ADDED: " + tag);
				}
			}
			
			if (ptbt.hasNext() == false) {
				for (String w: wordList) {
					TFIDFCalculator calculator = new TFIDFCalculator();
//			        double tfidf = calculator.tfIdf(wordList, w);
//			        System.out.println("TF-IDF: " + tfidf);
				}
			}
		}
	}
}