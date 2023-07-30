/**Group 3 Members: Ramil Fatkiev, Ekaterina Nikitina, Oleksii Ariasov, Ekaterina Akhmetshina*/
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create a CorpusBuilder which generates POS tags and Lemmas for text.
 *
 */
public class CorpusBuilder {
    private String text;
    private String[] sentences;
    private List<List<String>> tokens;
    private List<List<String>> posTags;
    private List<List<String>> lemmas;



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
        try (InputStream modelIn = Files.newInputStream(Paths.get("en-sent.bin"))) {
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
        try (InputStream modelIn = Files.newInputStream(Paths.get("en-token.bin"))) {
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
        try (InputStream modelIn = Files.newInputStream(Paths.get("en-pos-maxent.bin"))) {
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
     */
    public List<List<String>> getLemmas() {

        try (InputStream modelIn = Files.newInputStream(Paths.get("en-lemmatizer.bin")))
        {
            LemmatizerModel model = new LemmatizerModel(modelIn);
            LemmatizerME lemmatizer = new LemmatizerME(model);
            lemmas = new ArrayList<>();

            for (int i = 0; i<tokens.size(); i++ ) {
                List<String> sentence = tokens.get(i);
                List<String> tmpPos = posTags.get(i);
                String[] tmpLemmas = lemmatizer.lemmatize(sentence.toArray(new String[0]), tmpPos.toArray((new String[0])));
                lemmas.add(Arrays.asList(tmpLemmas));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lemmas;
    }


    /**
     * Create a new ArrayList that contains Lists of [word, POS, lemma] for every word in every sentence in the text.
     *
     * @return A List of lists of [word, POS, lemma] for every word in every sentence in the text.
     */
    public List<List<List<String>>> getWordPosLemma() {
        List<List<List<String>>> tokPosLemList = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            List<String> sentence = tokens.get(i);
            List<String> tmpPosTags = posTags.get(i);
            List<String> tmpLemmas = lemmas.get(i);

            List<List<String>> tokPosLemSent = new ArrayList<>();

            for (int j = 0; j < sentence.size(); j++) {
                String token = sentence.get(j);
                String pos = tmpPosTags.get(j);
                String lemma = tmpLemmas.get(j);

                List<String> tokPosLem = new ArrayList<>();
                tokPosLem.add(token);
                tokPosLem.add(pos);
                tokPosLem.add(lemma);

                tokPosLemSent.add(tokPosLem);
            }

            tokPosLemList.add(tokPosLemSent);
        }

        return tokPosLemList;
    }
}
