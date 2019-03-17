package user.story.clustering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Main {

	public static void main(String[] args) throws IOException {

		List<String> wordList = new ArrayList<String>();
		List<String> stopwordList = new ArrayList<String>();
	
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
				String lemma = label.lemma();
				String tag = maxentTagger.tagString(word);
			}
		}
		
		for (String word: wordList) {
			System.out.println(word);
		}
	}
}