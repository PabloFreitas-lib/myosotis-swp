package uni.myosotis.logic;

import uni.myosotis.objects.Keyword;
import uni.myosotis.persistence.KeywordRepository;

import java.util.List;
import java.util.Optional;


public class KeywordLogic {
    /**
     * The repository for the Indexcards.
     */
    final KeywordRepository KeywordRepository;

    /**
     * Creates a new IndexcardLogic.
     */
    public KeywordLogic () {
        this.KeywordRepository = new KeywordRepository();
    }

    /**
     * Creates a new Keyword and saves it in the database.
     * If already an Keyword with the same word exists, it will throw a IllegalStateException.
     *
     * @param word The word of the Keyword.
     */
    public void createKeyword(String word) {
        if (KeywordRepository.findKeyword(word).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            Keyword keyword = new Keyword(word);
            KeywordRepository.saveKeyword(keyword);
        }
    }

    /**
     * Creates a new Keyword and saves it in the database.
     * If already an Keyword with the same word exists, it will throw a IllegalStateException.
     *
     * @param words The word of the Keyword.
     */
    public void createMultiplesKeyword(String words) {
        // FIXME split the String in the spaces and each word run the createKeyword
        if (KeywordRepository.findKeyword(words).isPresent()) {
            throw new IllegalStateException("Es existiert bereits eine Karteikarte mit diesem Namen.");
        } else {
            Keyword keyword = new Keyword(words);
            KeywordRepository.saveKeyword(keyword);
        }
    }
    /**
     * Edits a existing Keyword and saves it in the database.
     * If there is no Keyword with the given word, it will throw a IllegalStateException.
     *
     * @param word The word of the Keyword.
     */
    public void editKeyword(String word) {
        if (KeywordRepository.findKeyword(word).isPresent()) {
            Keyword Keyword = new Keyword(word);
            int checkvalue = KeywordRepository.updateKeyword(Keyword);
            if (checkvalue != 0) {
                throw new IllegalStateException("Es existiert kein Schlagwort mit diesem Namen.");
            }
        }
    }
    /**
     * Deletes a existing Keyword and saves it in the database.
     * If there is no Keyword with the given word, it will throw a IllegalStateException.
     *
     * @param word The word of the Keyword.
     */
    public void deleteKeyword(String word) {
        if (KeywordRepository.findKeyword(word).isPresent()) {
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
    public List<Keyword> getAllKey() {
        return KeywordRepository.findAllKeywords();
    }

    /**
     * Return the Keyword with the given word.
     *
     * @param keyword The word of the Keyword.
     * @return The Keyword if it exists.
     */
    public Optional<Keyword> getKeyword (String keyword) {
        return KeywordRepository.findKeyword(keyword);
    }

    /**
     * Returns KeywordRepository.
     *
     * @return KeywordRepository
     */
    public KeywordRepository getKeywordRepository() {
        return KeywordRepository;
    }
}
