package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LearnSystem;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class is used to access the persistence storage for the object type "LearnSystem".
 */
public class LearnsystemRepository {

    private final Logger logger = Logger.getLogger(LearnsystemRepository.class.getName());
    private final PersistenceManager pm = new PersistenceManager();

    /**
     * This method is used to save a new LearnSystem for an IndexcardBox.
     *
     * @param learnSystem The LearnSystem.
     */
    public <T extends LearnSystem> void saveLearnSystem(T learnSystem) {
        final EntityManager em = pm.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(learnSystem);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            // handle the exception
        } finally {
            em.close();
        }
    }

    /**
     * This method is used to update a LearnSystem in the
     * persistence storage. If the LearnSystem does not exist, it will be created
     * and added to the database. Otherwise, the content of the given LearnSystem will be updated.
     *
     * @param learnsystem The LearnSystem that should be updated.
     */
    public void updateLearnystem(final LearnSystem learnsystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(learnsystem);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error updating LearnSystem: {0}", learnsystem.getId());
        }

    }

    /**
     * This method is used to delete a LearnSystem from the persistence storage.
     *
     * @param learnsystem The LearnSystem
     */
    public void deleteLearnsystem(LearnSystem learnsystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(learnsystem);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error deleting LearnSystem: {0}", learnsystem.getId());
        }
    }

    /**
     * This method is used to find an existing LearnSystem by id.
     *
     * @param id The id of the LearnSystem.
     * @return The LearnSystem, if it exists.
     */
    public Optional<LearnSystem> getLearnsystemById(long id) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.of(em.find(LearnSystem.class, id));
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error getting LearnSystem: {0}", id);
            return Optional.empty();
        }
    }

    /**
     * This method is used to find an existing LearnSystem by a IndexcardBox.
     *
     * @param indexcardBox The Indexcardbox connected to the LearnSystem.
     * @return The LearnSystem, if it exists.
     */
    public <T extends LearnSystem> Optional<T> getLearnsystemByIndexcardBox(IndexcardBox indexcardBox) {
        try (final EntityManager em = pm.getEntityManager()) {
            final List<T> learnsystemList = em.createQuery("SELECT l FROM LearnSystem l WHERE l.indexcardBox = :indexcardBox").setParameter("indexcardBox", indexcardBox).getResultList();
            if (learnsystemList.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(learnsystemList.get(0));
            }
        }
    }
}
