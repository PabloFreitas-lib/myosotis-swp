package uni.myosotis.logic;

import uni.myosotis.objects.IndexcardBox;
import uni.myosotis.objects.Learnsystem;
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
     */
    public Learnsystem learn(IndexcardBox indexcardBox) {
        if (learnsystemRepository.getLearnsystemByIndexcardBox(indexcardBox).isEmpty()) {
            Learnsystem learnsystem = new Learnsystem(indexcardBox);
            learnsystemRepository.safeLearnSystem(learnsystem);
        }
        return learnsystemRepository.getLearnsystemByIndexcardBox(indexcardBox).get();
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
