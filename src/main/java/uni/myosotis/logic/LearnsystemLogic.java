package uni.myosotis.logic;

import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.LearnSystem;
import uni.myosotis.objects.LeitnerLearnSystem;
import uni.myosotis.persistence.LearnsystemRepository;


public class LearnsystemLogic {

    /**
     * The repository for the Learnsystems.
     */
    final LearnsystemRepository learnsystemRepository;

    public LearnsystemLogic() {
        this.learnsystemRepository = new LearnsystemRepository();
    }

    /**
     * This method is used to learn an IndexcardBox. If there is no LearnSystem connected to this Indexcardbox,
     * a new one will be created.
     *
     * @param indexcardBox The IndexcardBox that should be learnend.
     * @param learnsystemName The learnsystem that should be used.
     */
    public <T extends LearnSystem> T learn(IndexcardBox indexcardBox, String learnsystemName) {
        if (learnsystemName.equals("Leitner")) {
            if (learnsystemRepository.getLearnsystemByIndexcardBox(indexcardBox).isEmpty()) {
                T learnsystem = (T) new LeitnerLearnSystem(indexcardBox);
                learnsystemRepository.saveLearnSystem(learnsystem);
                return learnsystem;
            } else {
                return (T) learnsystemRepository.getLearnsystemByIndexcardBox(indexcardBox).get();
            }
        }else {
            throw new IllegalArgumentException("Invalid learnsystem name");
        }
    }


    /**
     * Delegates the exercise to update a learnsystem to the learnsystemRepository.
     *
     * @param learnsystem The learnsystem that should be updated.
     */
    public void updateLearnsystem(LearnSystem learnsystem) {
        learnsystemRepository.updateLearnystem(learnsystem);
    }
}
