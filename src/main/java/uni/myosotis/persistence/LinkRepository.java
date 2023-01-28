package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Link;

import java.util.List;

/**
 * The repository for the links.
 */
public class LinkRepository {

    /**
     * The PersistenceManager.
     */
    private final PersistenceManager pm = new PersistenceManager();

    /**
     * Saves a Link to the persistence storage.
     *
     * @param link The Link that should be saved.
     */
    public void save(final Link link) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(link);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to save the link to the persistence storage: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing Link from the persistence storage.
     *
     * @param link The Link that should be deleted.
     */
    public void delete(final Link link) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.find(Link.class, link.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("Failed deleting the Link from the persistence storage: " + e.getMessage());
        }
    }

    /**
     * Returns all Links saved in the persistence storage.
     *
     * @return A list of all Links saved in the persistence storage, could be empty.
     */
    public List<Link> getAllLinks() {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT link FROM Link link").getResultList();
        } catch (Exception e) {
            throw new IllegalStateException("Failed getting all Links saved in the persistence storage: " + e.getMessage());
        }
    }

    /**
     * Returns a list of all Links saved in the persistence storage that are linked to a specific term.
     *
     * @param term The specific term.
     * @return A list of all Links saved in the persistence storage that are linked to a specific term,
     *         could be empty.
     */
    public List<Link> getLinksByTerm(final String term) {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT link FROM Link link WHERE link.term = :term").setParameter("term", term).getResultList();
        } catch (Exception e) {
            throw new IllegalStateException("Failed getting all saved in the persistence storage that are linked to a specific term: " + e.getMessage());
        }
    }

    /**
     * Returns a list of all Links saved in the persistence storage that are linked with a specific Indexcard.
     *
     * @param indexcard The specific Indexcard.
     * @return A list of all Links saved in the persistence storage that are linked with a specific Indexcard,
     *         could be empty.
     */
    public List<Link> getLinksByIndexcard(final Indexcard indexcard) {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT link FROM Link link WHERE link.indexcard = :indexcard").setParameter("indexcard", indexcard).getResultList();
        } catch (Exception e) {
            throw new IllegalStateException("Failed getting all saved in the persistence storage that are linked with a specific Indexcard: " + e.getMessage());
        }
    }
}
