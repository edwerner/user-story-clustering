package user.story.clustering;

import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
    // Tokenize each word
    PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new FileReader(Strings.CSV_SOURCE),
            new CoreLabelTokenFactory(), "");
    while (ptbt.hasNext()) {
      CoreLabel label = ptbt.next();
      System.out.println(label);
    }
  }
}
