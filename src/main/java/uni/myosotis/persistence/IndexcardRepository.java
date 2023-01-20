package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Index;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Keyword;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IndexcardRepository {

    private final PersistenceManager pm = new PersistenceManager();

    /**
     * @author Len Thiemann
     *
     * This method is used to save an object of type "Indexcard" to the persistent
     * persistence storage.
     *
     * @param indexcard     The index card that should be saved to the persistence.
     * @return              Status, -1 means an error has been occurred on save.
     */
    public int saveIndexcard(final Indexcard indexcard) {

        /*Keyword keyword = new Keyword("test");
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(keyword);
        indexcard = new Indexcard("Test Karte", "Frage", "Antwort", keywords);*/



        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(indexcard);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("KARTEIKARTE FEHLER!");
            return -1;
        }
        System.out.println("KARTEIKARTE ERSTELLT!");
        return 0;
    }

    /**
     * @author Johannes Neugebauer
     *
     * This method is used to update an object of type "Indexcard" to the persistent
     * persistence storage. If the Card does not exist at this point it will be created
     * and added to the database. Otherwise, the content of the given card will be updated
     * instead.
     *
     * @param indexcard     The index card that should be updated.
     * @return              Status, -1 means an error has been occurred on update.
     */
    public int updateIndexcard(final Indexcard indexcard) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(indexcard);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to get all objects of type "Indexcard" in the persistent
     * persistence storage.
     *
     * @return List of all objects of type "Indexcard", could be empty.
     */
    public List<Indexcard> findAllIndexcards(){
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT i FROM Indexcard i").getResultList();
        }
    }

    /**
     * This method is used to find an object of type "Indexcard" in the persistent
     * persistence storage.
     *
     * @param name      The unique id of the index card.
     * @return          The object of type "Indexcard" or null if it does not exist.
     */
    public Optional<Indexcard> findIndexcardByName(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            final List<Indexcard> indexcards = em.createQuery("SELECT i FROM Indexcard i WHERE i.name = :name").setParameter("name", name).getResultList();
            if (indexcards.size() == 1) {
                return Optional.of(indexcards.get(0));
            }
            else {
                return Optional.empty();
            }
        }
    }

    /**
     * This method is used to get all objects of type "Indexcard" in the persistence storage.
     * Which have the given keyword in their keyword list.
     *
     * @return List of all objects of type "Indexcard", could be empty.
     */
    public List<Indexcard> findAllIndexcardsByKeyword(String keyword) {

        /*try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT i FROM Indexcard WHERE word = :keyword")
                    .setParameter("keyword", keyword)
                    .getResultList();
        }*/
        return null;
    }

    /**
     * This method is used to delete an object of type "Indexcard" in the persistent
     * persistence storage.
     *
     * @param id      The unique id of the index card.
     * @return          Status, -1 means an error has been occurred on delete.
     */
    public int deleteIndexcard(final Long id) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(Indexcard.class, id));
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    public Long findIndexcardIdByName(String indexcard) {
        try (final EntityManager em = pm.getEntityManager()) {
            return (Long) em.createNativeQuery("SELECT id FROM Indexcard WHERE name = :name").setParameter("name",indexcard).getSingleResult();
        }
    }

    public Optional<Indexcard> getIndexcardById(Long id) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.ofNullable(em.find(Indexcard.class, id));
        }
    }

    /**
     * This method is used to get all objects of type "Category" in the persistent
     * persistence storage.
     *
     * @return List of all objects of type "Category", could be empty.
     */
    public List<Indexcard> getAllIndexcardByCategories(String categorieName){
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT i FROM Indexcard i JOIN Category c WHERE name = :name", Indexcard.class).setParameter("name", categorieName).getResultList();
        }
    }


}
