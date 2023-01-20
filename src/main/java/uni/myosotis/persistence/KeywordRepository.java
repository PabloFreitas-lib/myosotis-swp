package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;

import java.util.List;
import java.util.Optional;


public class KeywordRepository {

    private final PersistenceManager pm = new PersistenceManager();

    /**
     * This method is used to save an object of type "Keyword" to the
     * persistence storage.
     *
     * @param keyword     The keyword that should be saved to the persistence.
     * @return            Status, -1 means an error has been occurred on save.
     */

    public int saveKeyword(final Keyword keyword) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(keyword);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to delete an object of type "Keyword" in the
     * persistence storage.
     *
     * @param name      The name of the keywords.
     * @return          Status, -1 means an error has been occurred on delete.
     */
    public int deleteKeyword(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(Keyword.class, name));
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to find an object of type "Keyword" in the
     * persistence storage.
     *
     * @param word      The word of the keyword.
     * @return          The object of type "Keyword" or null if it does not exist.
     */
    public Optional<Keyword> getKeywordByName(final String word) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.ofNullable(em.find(Keyword.class, word));
        }
    }

    /**
     * This method is used to get all objects of type "Keyword" in the
     * persistence storage.
     *
     * @return List of all objects of type "Keyword", could be empty.
     */
    public List<Keyword> getAllKeywords(){
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT k FROM Keyword k", Keyword.class).getResultList();
        }
    }

    // END OF CLASS













































    /**
     * This method is used to update an object of type "Keyword" to the persistent
     * persistence storage. If the keyword does not exist at this point it will be created
     * and added to the database. Otherwise, the content of the given keyword will be updated
     * instead.
     *
     * @param oldKeyword  The keyword that should be updated in the persistence.
     * @param word        The new name that should be saved to the persistence.
     * @param indexcards  The new indexcards that should be saved to the persistence.
     */
    /*
    public int updateKeyword(final Keyword oldKeyword, final String word, final List<Indexcard> indexcards) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            oldKeyword.setWord(word);
            oldKeyword.setIndexcards(indexcards);
            em.merge(oldKeyword);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }*/

    /*
    public List<Indexcard> getIndexcardsFromKeyword(Keyword getkeyword) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            Keyword keyword = em.find(Keyword.class, getkeyword.getName());
            List<Indexcard> indexcards = keyword.getIndexcards();
            em.getTransaction().commit();
            return indexcards;
        }
    }*/
}
