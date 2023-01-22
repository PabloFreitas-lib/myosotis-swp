package uni.myosotis.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryGraph {
    private Map<Category, List<Category>> graph;
    public CategoryGraph() {
        graph = new HashMap<>();
    }
    // methods for adding, removing and getting edges
    void addEdge(Category parent, Category child) {
        graph.get(parent).add(child);
    }

    void removeEdge(Category parent, Category child) {
        graph.get(parent).remove(child);
    }

    public List<Category> getChildren(Category parent) {
        return graph.get(parent);
    }
    /**
     * The getRoots() method in a CategoryGraph class is a custom method that you
     * would need to implement to return the root nodes of the graph.
     * The root nodes are the nodes that have no incoming edges and are
     * not children of any other nodes in the graph.
     * */
    public List<Category> getRoots() {
        List<Category> roots = new ArrayList<>();
        for (Category node : graph.keySet()) {
            boolean isRoot = true;
            for (List<Category> children : graph.values()) {
                if (children.contains(node)) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                roots.add(node);
            }
        }
        return roots;
    }



}

