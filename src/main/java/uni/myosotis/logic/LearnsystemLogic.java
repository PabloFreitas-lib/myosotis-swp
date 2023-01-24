package uni.myosotis.logic;

import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.Learnsystem;
import uni.myosotis.objects.LeitnerLearnsystem;
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
     * This method is used to learn an IndexcardBox. If there is no Learnsystem connected to this Indexcardbox,
     * a new one will be created.
     *
     * @param indexcardBox The IndexcardBox that should be learnend.
     * @param learnsystemName The learnsystem that should be used.
     */
    public <T extends Learnsystem> T learn(IndexcardBox indexcardBox, String learnsystemName) {
        if (learnsystemRepository.getLearnsystemByIndexcardBox(indexcardBox).isEmpty()) {
            if (learnsystemName.equals("Leitner")) {
                T learnsystem = (T) new LeitnerLearnsystem(indexcardBox);
                learnsystemRepository.safeLearnSystem(learnsystem);
            }
        }
        return (T) learnsystemRepository.getLearnsystemByIndexcardBox(indexcardBox).get();
    }

    /**
     * Delegates the exercise to update a learnsystem to the learnsystemRepository.
     *
     * @param learnsystem The learnsystem that should be updated.
     */
    public void updateLearnsystem(Learnsystem learnsystem) {
        learnsystemRepository.updateLearnystem(learnsystem);
    }
}
