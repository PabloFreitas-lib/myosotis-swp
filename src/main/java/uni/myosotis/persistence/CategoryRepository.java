package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import uni.myosotis.objects.CategoryGraph;
import uni.myosotis.objects.Indexcard;
import uni.myosotis.objects.Category;

import java.util.List;
import java.util.Optional;


public class CategoryRepository {

    private final PersistenceManager pm = new PersistenceManager();


    /**
     * This method is used to save an object of type "category" to the persistent
     * persistence storage.
     *
     * @param category     The category that should be saved to the persistence.
     * @return            Status, -1 means an error has been occurred on save.
     */

    public int saveCategory(final Category category) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }


    /**
     * This method is used to update an object of type "Category" to the persistent
     * persistence storage. If the Category does not exist at this point it will be created
     * and added to the database. Otherwise, the content of the given Category will be updated
     * instead.
     *
     * @param oldCategory  The Category that should be updated in the persistence.
     * @param word        The new name that should be saved to the persistence.
     * @param indexcards  The new indexcards that should be saved to the persistence.
     * @param parent The parent from the Category.
     */
    public int updateCategory(final Category oldCategory, final String word, final List<Indexcard> indexcards, Category parent) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            oldCategory.setName(word);
            oldCategory.setIndexcardList(indexcards);
            oldCategory.setParent(parent);
            em.merge(oldCategory);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to update an object of type "Category" to the persistent
     * persistence storage. If the Category does not exist at this point it will be created
     * and added to the database. Otherwise, the content of the given Category will be updated
     * instead.
     *
     * @param oldCategory  The Category that should be updated in the persistence.
     * @param word        The new name that should be saved to the persistence.
     * @param indexcards  The new indexcards that should be saved to the persistence.
     */
    public int updateCategory(final Category oldCategory, final String word, final List<Indexcard> indexcards) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            oldCategory.setName(word);
            oldCategory.setIndexcardList(indexcards);
            em.merge(oldCategory);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * This method is used to find an object of type "Category" in the persistent
     * persistence storage.
     *
     * @param name      The name of the Category.
     * @return          The object of type "Category" or null if it does not exist.
     */
    public Optional<Category> getCategoryByName(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.ofNullable(em.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class).setParameter("name", name).getSingleResult());
        }
         catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Category> getCategoriesByCategoryNameList(final List<String> categoryNames) {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT c FROM Category c WHERE c.name IN :categoryNames", Category.class).setParameter("categoryNames", categoryNames).getResultList();
        }
    }

    /**
     * This method is used to get all objects of type "Category" in the persistent
     * persistence storage.
     *
     * @return List of all objects of type "Category", could be empty.
     */
    public List<Category> getAllCategories(){
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT k FROM Category k", Category.class).getResultList();
        }
    }


    /**
     * This method is used to delete an object of type "Category" in the persistent
     * persistence storage.
     *
     * @param name      The name of the Categorys.
     * @return          Status, -1 means an error has been occurred on delete.
     */
    public int deleteCategory(final String name) {
        final EntityManager em = pm.getEntityManager();
        try {
            em.getTransaction().begin();
            Category category = getCategoryByName(name).get();
            category = em.find(Category.class, category.getId());
            em.remove(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        return 0;
    }

    public List<Indexcard> getIndexcardsFromCategory(Category getCategory) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            Category Category = em.find(Category.class, getCategory.getCategoryName());
            List<Indexcard> indexcards = Category.getIndexcardList();
            em.getTransaction().commit();
            return indexcards;
        }
    }

}
