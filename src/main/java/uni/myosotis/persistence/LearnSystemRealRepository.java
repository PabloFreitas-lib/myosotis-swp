package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LearnSystem;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class LearnSystemRealRepository {
    /**
     * This class is used to access the persistence storage for the object type "LearnSystem".
     */
        private final Logger logger = Logger.getLogger(uni.myosotis.persistence.LearnSystemRealRepository.class.getName());
        private final PersistenceManager pm = new PersistenceManager();

        /**
         * This method is used to save a new LearnSystem.
         *
         * @param learnSystem The LearnSystem.
         */
        public void saveLearnSystem(LearnSystemRealRepository learnSystem) {
            final EntityManager em = pm.getEntityManager();
            try{
                em.getTransaction().begin();
                em.persist(learnSystem);
                em.getTransaction().commit();
            } catch (Exception e) {
                logger.warning("Could not save LearnSystem: " + e.getMessage());
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }

        /**
         * This method is used to update a LearnSystem in the
         * persistence storage. If the LearnSystem does not exist, it will be created
         * and added to the database. Otherwise, the content of the given LearnSystem will be updated.
         *
         * @param learnSystemRealRepository The LearnSystem that should be updated.
         */
        public void updateLearnSystem(LearnSystemRealRepository learnSystemRealRepository) {
            final EntityManager em = pm.getEntityManager();
            try{
                em.getTransaction().begin();
                em.merge(learnSystemRealRepository);
                em.getTransaction().commit();
            } catch (Exception e) {
                logger.warning("Could not update LearnSystem: " + e.getMessage());
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }


        /**
         * This method is used to delete a LearnSystem from the persistence storage.
         *
         * @param learnsystem The LearnSystem
         */
        public void deleteLearnSystem(LearnSystem learnsystem) {
            final EntityManager em = pm.getEntityManager();
            try{
                em.getTransaction().begin();
                em.remove(learnsystem);
                em.getTransaction().commit();
            } catch (Exception e) {
                logger.warning("Could not delete LearnSystem: " + e.getMessage());
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }

    /**
     * Get LearnSystem By name from persistence storage.
     */
    public Optional<LearnSystem> getLearnSystemByName(String name) {
        final EntityManager em = pm.getEntityManager();
        try{
            return Optional.ofNullable(em.find(LearnSystem.class, name));
        } catch (Exception e) {
            logger.warning("Could not get LearnSystem: " + e.getMessage());
            return Optional.empty();
        } finally {
            em.close();
        }
    }

}
