package user.story.clustering;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.WordLemmaTag;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
//import edu.stanford.nlp.simple.Document;
//import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.simple.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
		props.setProperty("pos.model", "english-left3words-distsim.tagger");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		PTBTokenizer ptbt = new PTBTokenizer(new FileReader("smarthome-userstories-3k.csv"),
				new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext();) {
			// Tokenize each word
			label = (CoreLabel) ptbt.next();
			String word = label.word();

			// create an empty Annotation just with the given text
			Annotation document = new Annotation(word.toLowerCase());

			// run all Annotators on this text
			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			// Document document = new Document(word);
			// for (Sentence sent : document.sentences()) { // Will iterate over two
			// sentences
			// // We're only asking for words -- no need to load any models yet
			// System.out.println("The first word of the sentence '" + sent + "' is " +
			// sent.word(0));
			// // When we ask for the lemma, it will load and run the part of speech tagger
			// System.out.println("The first lemma of the sentence '" + sent + "' is " +
			// sent.lemma(0));
			// // When we ask for the parse, it will load and run the parser
			// System.out.println("The parse of the sentence '" + sent + "' is " +
			// sent.parse());
			// // ...
			// }

			// System.out.println(sentences);

			for (CoreMap sentence : sentences) {
				// traversing the words in the current sentence
				// a CoreLabel is a CoreMap with additional token-specific methods
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					// this is the text of the token
					String w = token.get(TextAnnotation.class);
					// this is the POS tag of the token
					String pos = token.get(PartOfSpeechAnnotation.class);
					// this is the NER label of the token
					String ne = token.get(NamedEntityTagAnnotation.class);
					String lemma = token.get(LemmaAnnotation.class);
					System.out.println("LEMMA: " + lemma);
				}
			}

			// // Convert all words to lowercase
			// String lowerCaseWord = word.toLowerCase();
			//
			// // tag each word
			// MaxentTagger maxentTagger = new
			// MaxentTagger("english-left3words-distsim.tagger");
			// String tag = maxentTagger.tagString(lowerCaseWord);
			// System.out.println(tag);
		}
	}
}