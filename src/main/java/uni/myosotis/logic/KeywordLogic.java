package uni.myosotis.logic;

import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;
import uni.myosotis.persistence.KeywordRepository;

import java.util.List;
import java.util.Optional;


public class KeywordLogic {

    final KeywordRepository KeywordRepository;

    /**
     * Creates a new KeywordLogic.
     */
    public KeywordLogic() {
        this.KeywordRepository = new KeywordRepository();
    }

    /**
     * Creates a new Keyword and saves it in the database.
     * If the keyword could not be saved to the database, a
     * IllegalStateException will the thrown.
     *
     * @param name The name of the Keyword.
     */
    public Keyword createKeyword(String name) {
        Keyword keyword = new Keyword(name);
        if (KeywordRepository.saveKeyword(keyword) < 0) {
            throw new IllegalStateException("Keyword could not be saved to the database!");
        }
        return keyword;
    }

    /**
     * Returns if the Keyword already exists.
     * @param word The word of the Keyword.
     */
    public boolean KeywordIsPresent(String word) {
        return KeywordRepository.getKeywordByName(word).isPresent();
    }

    /**
     * Delegates the exercise to get all Keywords to the KeywordRepository and returns them.
     *
     * @return          All Keywords, could be empty.
     */
    public List<Keyword> getAllKeywords() {
        return KeywordRepository.getAllKeywords();
    }

    /**
     * Return the Keyword with the given word.
     *
     * @param           name The name of the Keyword.
     * @return               The Keyword or null if it does not exist
     */
    public Optional<Keyword> getKeyword(String name) {
        return KeywordRepository.getKeywordByName(name);
    }

    /**
     * Deletes an existing Keyword from the database.
     * If the Keyword could not be deleted,
     * a IllegalStateException will be thrown.
     *
     * @param name          Name of the keyword that should be deleted.
     */
    public void deleteKeyword(String name) {
        if (KeywordIsPresent(name)) {
            if (KeywordRepository.deleteKeyword(name) < 0) {
                throw new IllegalStateException("Das Keyword konnte nicht gelöscht werden.");
            }
        }
    }

    // END OF CLASS







    /**
     * Returns KeywordRepository.
     *
     * @return KeywordRepository
     */
    /*public KeywordRepository getKeywordRepository() {
        return KeywordRepository;
    }*/


    /**
     * Deletes a specific Indexcard from a Keyword.
     * @param keyword
     * @param indexcards
     */
    /*public void deleteIndexcardFromKeyword(Keyword keyword, List<Indexcard> indexcards) {
        KeywordRepository.updateKeyword(keyword, keyword.getName(), indexcards);
    }*/





    /*public void updateKeywordName(Keyword keyword, String name) {
        KeywordRepository.updateKeyword(keyword, name, keyword.getIndexcards());
    }*/




    /**
     * Creates a new Keyword and saves it in the database.
     * If already a Keyword with the same word exists, it will throw a IllegalStateException.
     *
     * @param name The word of the Keyword.
     * @param indexcard The name of the Indexcard.
     */
    /*
    public void createKeyword(String name, Indexcard indexcard) {
        if (KeywordIsPresent(name)) {
            addIndexcardToKeyword(name, indexcard);
        } else {
            List<Indexcard> indexcards = List.of(indexcard);
            Keyword keyword = new Keyword(name, indexcards);
            KeywordRepository.saveKeyword(keyword);
        }
    }*/




    /**
     * Adds an Indexcard to a Keyword.
     * @param word The word of the Keyword.
     * @param indexcard The Indexcard which should be added.
     */
    /*public void addIndexcardToKeyword(String word, Indexcard indexcard) {
        Optional<Keyword> keyword = KeywordRepository.getKeywordByName(word);
        if (keyword.isPresent()) {
            Keyword keyword1 = keyword.get();
            keyword1.addIndexcard(indexcard);
            KeywordRepository.saveKeyword(keyword1);
        }
    }*/


    /**
     * This method is used to find an object of type "Keyword" in the
     * persistence storage.
     *
     * @param name      The word of the keyword.
     * @return          The object of type "Keyword" or null if it does not exist.
     */

    /*
    public Optional<Keyword> getKeywordByName(String name) {
        if(KeywordRepository.getKeywordByName(name).isPresent()) {
            return Optional.ofNullable(KeywordRepository.getKeywordByName(name).get());
        } else {
            throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
        }
    }*/




    /**
     * edits the Indexcards of a Keyword.
     * @param word
     * @param indexcards
     */
    /*public void editKeywordIndexcards(String word, List<Indexcard> indexcards) {
        Optional<Keyword> keyword = getKeyword(word);
        if (keyword.isPresent()) {
            keyword.get().setIndexcards(indexcards);
            KeywordRepository.updateKeyword(keyword.get(), keyword.get().getName(), indexcards);
        } else {
            throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
        }
    }*/




    /**
     * Edits the name of a Keyword.
     * @param name
     * @param newWord
     */
    /*public void editKeywordWord(String name, String newWord) {
        Optional<Keyword> keyword = getKeyword(name);
        if (keyword.isPresent()) {
            keyword.get().setWord(newWord);
            KeywordRepository.updateKeyword(keyword.get(), newWord, keyword.get().getIndexcards());
        } else {
            throw new IllegalStateException("Es existiert keine Karteikarte mit diesem Namen.");
        }
    }*/
}
