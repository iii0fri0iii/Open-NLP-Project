
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a CorpusBuilder which generates POS tags and Lemmas for text.
 *
 * @param text The text which should be annotated.
 */
public class CorpusBuilder {
    private String text;
    private String[] sentences;
    private List<List<String>> tokens;
    private List<List<String>> posTags;



    public CorpusBuilder(String text) {
        this.text = text;
    }
    /**
     * Returns the text of this CorpusBuilder
     *
     * @return The text of this CorpusBuilder
     */
    public String getText() {
        return text;
    }

    /**
     * Return an array with the sentences of the CorpusBuilder
     *
     * @return An array with the sentences of the CorpusBuildr
     */
    public String[] getSentences() {
        try (InputStream modelIn = new FileInputStream("de-sent.bin")) {
            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
            this.sentences = sentenceDetector.sentDetect(this.text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sentences;
    }

    /**
     * Return a List of List with the tokens/words of the text of CorpusBuilder. The first list holds the words of the
     * first sentence, the second list holds the words of the second sentence and so on.
     *
     * @return A List of List the tokens/words of the text of the CorpusBuilder.
     */
    public List<List<String>> getTokens() {
        try (InputStream modelIn = new FileInputStream("detoken.bin")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);
            tokens=new ArrayList<>();

            for (String s : sentences) {
                String[] sTokens = tokenizer.tokenize(s);
                List<String> sentenceTokenList= new ArrayList<>();
                for (String token : sTokens) {
                    sentenceTokenList.add(token);
                }
                tokens.add(sentenceTokenList);

            }
        } catch (IOException e) {e.printStackTrace();}

        return tokens;
    }

    /**
     * Return a List of List with the POS tags of the text of CorpusBuilder. The first list holds the POS tags of the
     * first sentence, the second list holds the POS tags of the second sentence and so on.
     *
     * @return A List of List with the POS tags of the text of CorpusBuilder.
     */
    public List<List<String>> getPosTags() {
        try (InputStream modelIn = new
                FileInputStream("de-pos-maxent.bin")) {
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);
            posTags=new ArrayList<>();

            for (List<String> sentenceTokens : tokens) {
                String[] tags = tagger.tag(sentenceTokens.toArray(new String[0]));
                List<String> tagsList = new ArrayList<>();
                for (String tag : tags) {
                    tagsList.add(tag);
                }
                posTags.add(tagsList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return posTags;
    }

    /**
     * Return a List of List with the Lemmas of the text of CorpusBuilder. The first list holds the lemmas of the
     * first sentence, the second list holds the Lemmas of the second sentence and so on.
     *
     * @return A List of List with the Lemmas of the text of CorpusBuilder.
     * @return
     */
    public List<List<String>> getLemmas() {
        return lemmas;
    }
}