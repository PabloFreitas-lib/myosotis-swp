package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LeitnerLearnSystem;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class is used to access the persistence storage for the object type "LeitnerLearnSystem".
 */
public class LeitnerLearnSystemRepository {

    private final Logger logger = Logger.getLogger(LeitnerLearnSystemRepository.class.getName());
    private final PersistenceManager pm = new PersistenceManager();

    /**
     * This method is used to save a new leitnerLearnSystem for an IndexcardBox.
     *
     * @param leitnerLearnSystem The leitnerLearnSystem.
     */
    public void saveLeitnerLearnSystem(LeitnerLearnSystem leitnerLearnSystem) {
        final EntityManager em = pm.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(leitnerLearnSystem);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            // handle the exception
        } finally {
            em.close();
        }
    }

    /**
     * This method is used to update a leitnerLearnSystem in the
     * persistence storage. If the leitnerLearnSystem does not exist, it will be created
     * and added to the database. Otherwise, the content of the given leitnerLearnSystem will be updated.
     *
     * @param leitnerLearnSystem The leitnerLearnSystem that should be updated.
     */
    public void updateLearnSystem(final LeitnerLearnSystem leitnerLearnSystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(leitnerLearnSystem);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error updating leitnerLearnSystem: {0}", leitnerLearnSystem.getId());
        }

    }

    /**
     * This method is used to delete a LeitnerLearnSystem from the persistence storage.
     *
     * @param LeitnerLearnSystem The LeitnerLearnSystem
     */
    public void deleteLeitnerLearnSystem(LeitnerLearnSystem LeitnerLearnSystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(LeitnerLearnSystem);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error deleting LeitnerLearnSystem: {0}", LeitnerLearnSystem.getId());
        }
    }

    /**
     * This method is used to find an existing LeitnerLearnSystem by id.
     *
     * @param id The id of the LeitnerLearnSystem.
     * @return The LeitnerLearnSystem, if it exists.
     */
    public Optional<LeitnerLearnSystem> getLeitnerLearnSystemById(long id) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.of(em.find(LeitnerLearnSystem.class, id));
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error getting LeitnerLearnSystem: {0}", id);
            return Optional.empty();
        }
    }

    /**
     * This method is used to find an existing LeitnerLearnSystem by a IndexcardBox.
     *
     * @param indexcardBox The Indexcardbox connected to the LeitnerLearnSystem.
     * @return The LeitnerLearnSystem, if it exists.
     */
    public <T extends LeitnerLearnSystem> Optional<T> getLeitnerLearnSystemByIndexcardBox(IndexcardBox indexcardBox) {
        try (final EntityManager em = pm.getEntityManager()) {
            final List<T> LeitnerLearnSystemList = em.createQuery("SELECT l FROM LeitnerLearnSystem l WHERE l.indexcardBox = :indexcardBox").setParameter("indexcardBox", indexcardBox).getResultList();
            if (LeitnerLearnSystemList.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(LeitnerLearnSystemList.get(0));
            }
        }
    }
}