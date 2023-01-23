package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.IndexcardBox;

import java.util.List;
import java.util.Optional;

public class IndexcardBoxRepository {
    private final PersistenceManager pm = new PersistenceManager();

    /**
     * @author Len Thiemann
     *
     * This method is used to save an object of type "indexcardBox" to the persistent
     * persistence storage.
     *
     * @param indexcardBox     The index card that should be saved to the persistence.
     * @return              Status, -1 means an error has been occurred on save.
     */
    public int saveIndexcardBox(final IndexcardBox indexcardBox) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(indexcardBox);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * @author Johannes Neugebauer
     *
     * This method is used to update an object of type "indexcardBox" to the persistent
     * persistence storage. If the Card does not exist at this point it will be created
     * and added to the database. Otherwise, the content of the given card will be updated
     * instead.
     *
     * @param indexcardBox     The index card that should be updated.
     * @return              Status, -1 means an error has been occurred on update.
     */
    public int updateIndexcardBox(final IndexcardBox indexcardBox) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(indexcardBox);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to delete an object of type "IndexcardBox" in the persistent
     * persistence storage.
     *
     * @param id      The unique id of the index card.
     * @return          Status, -1 means an error has been occurred on delete.
     */
    public int deleteIndexcardBox(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(IndexcardBox.class, name));
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to find an object of type "IndexcardBox" in the persistent
     * persistence storage.
     *
     * @param name      The unique id of the index card.
     * @return          The object of type "IndexcardBox" or null if it does not exist.
     */
    public Optional<IndexcardBox> getIndexcardBoxByName(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            final List<IndexcardBox> indexcardBoxes = em.createQuery("SELECT i FROM IndexcardBox i WHERE i.name = :name").setParameter("name", name).getResultList();
            if (indexcardBoxes.size() == 1) {
                return Optional.of(indexcardBoxes.get(0));
            }
            else {
                return Optional.empty();
            }
        }
    }


    /**
     * This method is used to get all objects of type "Indexcard" in the persistent
     * persistence storage.
     *
     * @return List of all objects of type "Indexcard", could be empty.
     */
    public List<IndexcardBox> getAllIndexcardBoxes(){
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT i FROM IndexcardBox i").getResultList();
        }
    }
}
