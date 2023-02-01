package uni.myosotis.logic;

import uni.myosotis.objects.LeitnerLearnSystem;
import uni.myosotis.persistence.LeitnerLearnSystemRepository;

import java.util.List;


public class LeitnerLearnSystemLogic {

    /**
     * The repository for the Learnsystems.
     */
    final LeitnerLearnSystemRepository leitnerLearnSystemRepository;

    public LeitnerLearnSystemLogic() {
        this.leitnerLearnSystemRepository = new LeitnerLearnSystemRepository();
    }




    /**
     * Delegates the exercise to update a learnsystem to the learnsystemRepository.
     *
     * @param learnsystem The learnsystem that should be updated.
     */
    public void updateLearnsystem(LeitnerLearnSystem learnsystem) {
        leitnerLearnSystemRepository.updateLearnSystem(learnsystem);
    }


    public LeitnerLearnSystem learnLeitnerSystem(List<String> indexcardList) {
        LeitnerLearnSystem learnSystem = new LeitnerLearnSystem(indexcardList);
        return  learnSystem;
    }
}
