package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;
import uni.myosotis.persistence.KeywordRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class KeywordLogic {
    /**
     * The repository for the Keywords.
     */
    final KeywordRepository KeywordRepository;

    /**
     * Creates a new KeywordsLogic.
     */
    public KeywordLogic () {
        this.KeywordRepository = new KeywordRepository();
    }


    /**
     * Creates a new Keyword and saves it in the database.
     *
     * @param word The word of the Keyword.
     */
    public void createKeywordWithNoIndexcard(String word) {
        Keyword keyword = new Keyword(word);
        KeywordRepository.saveKeyword(keyword);
    }

    /**
     * Creates a new Keyword and saves it in the database.
     * If already a Keyword with the same word exists, it will throw a IllegalStateException.
     *
     * @param word The word of the Keyword.
     * @param indexcard The name of the Indexcard.
     */
    public void createKeyword(String word, Indexcard indexcard) {
        if (KeywordIsPresent(word)) {
            addIndexcardToKeyword(word, indexcard);
        } else {
            List<Indexcard> indexcards = List.of(indexcard);
            Keyword keyword = new Keyword(word, indexcards);
            KeywordRepository.saveKeyword(keyword);
        }
    }
    /**
     * Adds an Indexcard to a Keyword.
     * @param word The word of the Keyword.
     * @param indexcard The Indexcard which should be added.
     */
    public void addIndexcardToKeyword(String word, Indexcard indexcard) {
        Optional<Keyword> keyword = KeywordRepository.getKeywordByName(word);
        if (keyword.isPresent()) {
            Keyword keyword1 = keyword.get();
            keyword1.addIndexcard(indexcard);
            KeywordRepository.saveKeyword(keyword1);
        }
    }
    /**
     * Returns if the Keyword already exists.
     * @param word The word of the Keyword.
     */
    public Boolean KeywordIsPresent(String word) {
        return KeywordRepository.getKeywordByName(word).isPresent();
    }

    /**
     * edits the word of a Keyword.
     * @param word
     * @param newWord
     */
    public void editKeywordWord(String word, String newWord) {
        Optional<Keyword> keyword = getKeyword(word);
        if (keyword.isPresent()) {
            keyword.get().setWord(newWord);
            KeywordRepository.updateKeyword(keyword.get(), newWord, keyword.get().getIndexcards());
        } else {
            throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
        }
    }
    /**
     * edits the Indexcards of a Keyword.
     * @param word
     * @param indexcards
     */
    public void editKeywordIndexcards(String word, List<Indexcard> indexcards) {
        Optional<Keyword> keyword = getKeyword(word);
        if (keyword.isPresent()) {
            keyword.get().setIndexcards(indexcards);
            KeywordRepository.updateKeyword(keyword.get(), keyword.get().getKeywordWord(), indexcards);
        } else {
            throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
        }
    }

    /**
     * Deletes an existing Keyword and saves it in the database.
     * If there is no Keyword with the given word, it will throw a IllegalStateException.
     *
     * @param word The word of the Keyword.
     */
    public void deleteKeyword(String word) {
        if (KeywordIsPresent(word)) {
            int checkvalue = KeywordRepository.deleteKeyword(word);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
            }
        }
    }

    /**
     * Returns all Indexcards.
     *
     * @return All Indexcards.
     */
    public Optional<List<Keyword>> getAllKeywords() {
        return KeywordRepository.getAllKeywords();
    }

    /**
     * Return the Keyword with the given word.
     *
     * @param keyword The word of the Keyword.
     * @return The Keyword if it exists.
     */
    public Optional<Keyword> getKeyword (String keyword) {
        return KeywordRepository.getKeywordByName(keyword);
    }

    /**
     * Returns KeywordRepository.
     *
     * @return KeywordRepository
     */
    public KeywordRepository getKeywordRepository() {
        return KeywordRepository;
    }

    /**
     * Deletes a specific Indexcard from a Keyword.
     * @param keyword
     * @param indexcards
     */
    public void deleteIndexcardFromKeyword(Keyword keyword, List<Indexcard> indexcards) {
        KeywordRepository.updateKeyword(keyword, keyword.getKeywordWord(), indexcards);
    }

    public void updateKeywordName(Keyword keyword, String name) {
        KeywordRepository.updateKeyword(keyword, name, keyword.getIndexcards());
    }

    public Keyword getKeywordByWord(String keywordName) {
        if(KeywordRepository.getKeywordByName(keywordName).isPresent()) {
            return KeywordRepository.getKeywordByName(keywordName).get();
        } else {
            throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
        }
    }
}
