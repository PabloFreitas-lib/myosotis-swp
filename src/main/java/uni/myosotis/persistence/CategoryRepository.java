package uni.myosotis.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import uni.myosotis.objects.Category;
import uni.myosotis.objects.Indexcard;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * This class is used to access the persistence storage for the object type "Category".
 */
public class CategoryRepository {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CategoryRepository.class.getName());

    private final PersistenceManager pm = new PersistenceManager();


    /**
     * This method is used to save an object of type "category" to the persistent
     * persistence storage.
     *
     * @param category     The category that should be saved to the persistence.
     */

    public void saveCategory(final Category category) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error saving category: {0}", category.getName());
            logger.log(Level.SEVERE,"Error: {0}", e.getMessage());
        }
        //logger.log(Level.INFO,"Successfully saved category: {0}", category.getName());
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
    public int updateCategory(final Category oldCategory, final String word, final List<String> indexcards, Category parent) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            oldCategory.setName(word);
            oldCategory.setIndexcardList(indexcards);
            oldCategory.setParent(parent);
            em.merge(oldCategory);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating category: {0}", oldCategory.getName());
            logger.log(Level.SEVERE, "Error: {0}", e.getMessage());
            return -1;
        }
        //logger.log(Level.INFO, "Successfully updated category: {0}", oldCategory.getName());
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
    public int updateCategory(final Category oldCategory, final String word, final List<String> indexcards) {
        try (final EntityManager em = pm.getEntityManager()) {
            em.getTransaction().begin();
            oldCategory.setName(word);
            oldCategory.setIndexcardList(indexcards);
            em.merge(oldCategory);
            em.getTransaction().commit();
        }
         catch (Exception e) {
        logger.log(Level.SEVERE, "Error updating category: {0}", oldCategory.getName());
        logger.log(Level.SEVERE, "Error: {0}", e.getMessage());
        return -1;
    }
        //logger.log(Level.INFO, "Successfully updated category: {0}", oldCategory.getName());
        return 0;
    }

    /**
     * This method is used to find an object of type "Category" in the persistent
     * persistence storage.
     *
     * @param name      The name of the Category.
     * @return          The object of type "Category" or null if it does not exist.
     */
    /*public Optional<Category> getCategoryByName(final String name) {
        try (final EntityManager em = pm.getEntityManager()) {
            return Optional.ofNullable(em.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class).setParameter("name", name).getSingleResult());
        } catch (NoResultException e) {
            logger.log(Level.WARNING,"No category found with name {0}", name);
            return Optional.empty();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error occurred while searching for category by name {0}", name);
            logger.log(Level.SEVERE,"Error: {0}", e.getMessage());
            throw e;
        }
    }*/

    public Category getCategoryByName(final String name) {
        final EntityManager em = pm.getEntityManager();
        try {
            final List<Category> categories = em.createQuery("SELECT c FROM Category c WHERE c.name = :name").setParameter("name", name).getResultList();
            if (categories.size() == 1) {
                return categories.get(0);
            } else {
                return null;
            }
        } catch (NoResultException e) {
            logger.log(Level.WARNING,"No category found with name {0}", name);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error occurred while searching for category by name {0}", name);
            logger.log(Level.SEVERE,"Error: {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * This method is used to find an object of type "Category" in the persistent by Category Names.
     * @param categoryNames The names of the Categories.
     * @return The object of type "Category" or null if it does not exist.
     */

    public List<Category> getCategoriesByCategoryNameList(final List<String> categoryNames) {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT c FROM Category c WHERE c.name IN :categoryNames", Category.class).setParameter("categoryNames", categoryNames).getResultList();
        }
        catch (NoResultException e) {
            logger.log(Level.WARNING,"No category found with name {0}", categoryNames);
            return new ArrayList<>();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,"Error occurred while searching for category by name {0}", categoryNames);
            logger.log(Level.SEVERE,"Error: {0}", e.getMessage());
            throw e;
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
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error occurred while retrieving all categories", e);
            throw e;
        }
    }


    /**
     * This method is used to delete an object of type "Category" in the persistent
     * persistence storage.
     *
     * @param name      The name of the Categories.
     * @return          Status, -1 means an error has been occurred on delete.
     */
    public int deleteCategory(final String name) {
        final EntityManager em = pm.getEntityManager();
        try {
            em.getTransaction().begin();
            final Category categoryOpt = getCategoryByName(name);
            if (categoryOpt == null) {
                logger.log(Level.WARNING,"No category found with name {0}", name);
                return -1;
            }
            em.remove(em.find(Category.class, categoryOpt.getId()));
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.log(Level.SEVERE,"Error occurred while deleting category with name {0}", name);
            logger.log(Level.SEVERE,"Error: {0}", e.getMessage());
            throw e;
        }
    }


    /**
     * This method is used to search for an object of type "Category" in the persistent
     * persistence storage.
     *
     * @param text      The text that should be searched for.
     * @return          List of all objects of type "Category" that contain the text, could be empty.
     */

    public List<Category> searchCategory(String text) {
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT i FROM Category i WHERE LOWER(i.name) LIKE :text", Category.class)
                    .setParameter("text", "%" + text.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error occurred while searching category with text {0}", text);
            logger.log(Level.SEVERE,"Error: {0}", e.getMessage());
            throw e;
        }
    }

    public List<Category> getChildren(Category category){
        try (final EntityManager em = pm.getEntityManager()) {
            return em.createQuery("SELECT k FROM Category k WHERE parent = :category", Category.class)
                    .setParameter("category", category)
                    .getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Error occurred while retrieving all categories", e);
            throw e;
        }
    }

}
