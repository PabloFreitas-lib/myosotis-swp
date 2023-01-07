package uni.myosotis.database;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.Indexcard;

import java.util.Optional;

public class IndexcardRepository {

    private final PersistenceManager pm = new PersistenceManager();

    /**
     * This method is used to save an object of type "Indexcard" to the persistent
     * database storage.
     *
     * @param indexcard     The index card that should be saved to the database.
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
     * database storage.
     *
     * @param indexcard     The index card that should be updated.
     * @return              Status, -1 means an error has been occurred on save.
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
     * database storage.
     *
     * @param id        The unique id of the index card.
     * @return          The object of type "Indexcard" or null if it does not exist.
     */
    public Optional<Indexcard> findIndexcard(final double id) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.ofNullable(em.find(Indexcard.class, id));
        }
    }

}
