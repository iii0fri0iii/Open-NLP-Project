/**
 * Create a CorpusBuilder which generates POS tags and Lemmas for text.
 *
 * @param text The text which should be annotated.
 */
public class CorpusBuilder(String text) {
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
        return sentences;
    }

    /**
     * Return a List of List with the tokens/words of the text of CorpusBuilder. The first list holds the words of the
     * first sentence, the second list holds the words of the second sentence and so on.
     *
     * @return A List of List the tokens/words of the text of the CorpusBuilder.
     */
    public List<List<String>> getTokens() {
        return tokens;
    }

    /**
     * Return a List of List with the POS tags of the text of CorpusBuilder. The first list holds the POS tags of the
     * first sentence, the second list holds the POS tags of the second sentence and so on.
     *
     * @return A List of List with the POS tags of the text of CorpusBuilder.
     */
    public List<List<String>> getPosTags() {
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