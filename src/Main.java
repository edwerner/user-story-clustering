
import java.io.FileNotFoundException;
import java.io.FileReader;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		PTBTokenizer ptbt = new PTBTokenizer(new FileReader("smarthome-userstories-3k.csv"), new CoreLabelTokenFactory(), "");
		for (CoreLabel label; ptbt.hasNext();) {
			label = (CoreLabel) ptbt.next();
			System.out.println(label);
		}
	}
}
