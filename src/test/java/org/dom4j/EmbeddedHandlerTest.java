

package org.dom4j;

import org.dom4j.io.SAXReader;
import org.testng.Assert;

import java.io.File;

/**
 * TestEmbeddedHandler
 *
 * @author <a href="mailto:franz.beil@temis-group.com">FB </a>
 * @author Filip Jirsák
 */
public class EmbeddedHandlerTest extends AbstractTestCase {
	protected String[] testDocuments = {"xml/test/FranzBeilMain.xml"};

	public String testMainReader() throws Exception {
		return readDocuments(ReaderType.MAIN_READER);
	}

	public String testOnEndReader() throws Exception {
		return readDocuments(ReaderType.ON_END_READER);
	}

	public void testBothReaders() throws Exception {
		String main = testMainReader();
		String onEnd = testOnEndReader();

		Assert.assertEquals(main, onEnd, "Results of tests should be equal.");
	}

	private String readDocuments(ReaderType readerType) throws Exception {
		StringBuilder result = new StringBuilder();
		for (String testDocument : testDocuments) {
			File testDoc = getFile(testDocument);
			String mainDir = testDoc.getParent();
			SAXReader reader = new SAXReader();
			MainHandler mainHandler = new MainHandler(readerType, mainDir);
			reader.addHandler("/main/import", mainHandler);
			getDocument(testDocument, reader);
			result.append(mainHandler.getResult());
		}
		return result.toString();
	}

	class MainHandler implements ElementHandler {
		private final SAXReader mainReader;
		private final String mainDir;
		private final StringBuilder result;
		private final ReaderType readerType;

		public MainHandler(ReaderType readerType, String dir) {
			this.readerType = readerType;
			mainReader = new SAXReader();
			mainDir = dir;
			result = new StringBuilder();
			mainReader.addHandler("/import/stuff", new EmbeddedHandler(result));
		}

		public void onStart(ElementPath path) {
		}

		public void onEnd(ElementPath path) {
			String href = path.getCurrent().attribute("href").getValue();
			Element importRef = path.getCurrent();
			Element parentElement = importRef.getParent();
			SAXReader onEndReader = new SAXReader();
			onEndReader.addHandler("/import/stuff", new EmbeddedHandler(result));

			File file = new File(mainDir + File.separator + href);
			Element importElement = null;

			try {
				switch (readerType) {
					case MAIN_READER:
						importElement = mainReader.read(file).getRootElement();
						break;
					case ON_END_READER:
						importElement = onEndReader.read(file).getRootElement();
						break;
					default:
						throw new IllegalArgumentException(String.format("Illegal type of reader: %s", readerType));
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

			// prune and replace
			importRef.detach();
			parentElement.add(importElement);
		}

		public StringBuilder getResult() {
			return result;
		}
	}

	class EmbeddedHandler implements ElementHandler {
		private final StringBuilder result;

		EmbeddedHandler(StringBuilder result) {
			this.result = result;
		}

		public void onStart(ElementPath path) {
			result.append(path.getCurrent().attribute("name").getValue());
			result.append('\n');
		}

		public void onEnd(ElementPath path) {
		}
	}

	private enum ReaderType {
		MAIN_READER,
		ON_END_READER,;
	}
}

