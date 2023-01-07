package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.Indexcard;

import java.util.List;
import java.util.Optional;

public class IndexcardRepository {

    private final PersistenceManager pm = new PersistenceManager();

    /**
     * This method is used to save an object of type "Indexcard" to the persistent
     * persistence storage.
     *
     * @param indexcard     The index card that should be saved to the persistence.
     * @return              Status, -1 means an error has been occurred on save.
     */
    public int saveIndexcard(final Indexcard indexcard) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(indexcard);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
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
     * This method is used to find an object of type "Indexcard" in the persistent
     * persistence storage.
     *
     * @param name      The unique id of the index card.
     * @return          The object of type "Indexcard" or null if it does not exist.
     */
    public Optional<Indexcard> findIndexcard(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.ofNullable(em.find(Indexcard.class, name));
        }
    }

    /**
     * This method is used to get all objects of type "Indexcard" in the persistent
     * persistence storage.
     *
     * @return List of all objects of type "Indexcard", could be empty.
     */
    public List<Indexcard> findAllIndexcards() {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT i FROM Indexcard i").getResultList();
        }
    }

}
