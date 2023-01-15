package uni.myosotis.objects;

/**
 * Linking between the Indexcard and term (Begriff).
 *
 */
public class Link {

    private Indexcard indexcard;
    private String term;

    /**
     * FIXME
     *
     */
    void Link(Indexcard indexcard, String term){
        this.indexcard = indexcard;
        this.term = getTerm();
    }
    /**
     * FIXME
     *
     */
    String getTerm(){
        return this.indexcard.getName();
    }

}
