package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.Learnsystem;

import java.util.List;
import java.util.Optional;

public class LearnsystemRepository {

    private final PersistenceManager pm = new PersistenceManager();

    /**
     * This method is used to save a new Learnsystem for an IndexcardBox.
     *
     * @param learnsystem The Learnsystem.
     */
    public void safeLearnSystem(Learnsystem learnsystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(learnsystem);
            em.getTransaction().commit();
        }
    }

    /**
     * This method is used to update a Learnsystem in the
     * persistence storage. If the Learnsystem does not exist, it will be created
     * and added to the database. Otherwise, the content of the given Learnsystem will be updated.
     *
     * @param learnsystem The Learnsystem that should be updated.
     */
    public void updateLearnystem(final Learnsystem learnsystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(learnsystem);
            em.getTransaction().commit();
        }
    }

    /**
     * This method is used to delete a Learnsystem from the persistence storage.
     *
     * @param learnsystem The Learnsystem
     */
    public void deleteLearnsystem(Learnsystem learnsystem) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(learnsystem);
            em.getTransaction().commit();
        }
    }

    /**
     * This method is used to find an existing Learnsystem by id.
     *
     * @param id The id of the Learnsystem.
     * @return The Learnsystem, if it exists.
     */
    public Optional<Learnsystem> getLearnsystemById(long id) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.of(em.find(Learnsystem.class, id));
        }
    }

    /**
     * This method is used to find an existing Learnsystem by a IndexcardBox.
     *
     * @param indexcardBox The Indexcardbox connected to the Learnsystem.
     * @return The Learnsystem, if it exists.
     */
    public Optional<Learnsystem> getLearnsystemByIndexcardBox(IndexcardBox indexcardBox) {
        try (final EntityManager em = pm.getEntityManager()) {
            final List<Learnsystem> learnsystemList = em.createQuery("SELECT l FROM Learnsystem l WHERE l.indexcardBox = :indexcardBox").setParameter("indexcardBox", indexcardBox).getResultList();
            if (learnsystemList.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(learnsystemList.get(0));
            }
        }
    }
}
