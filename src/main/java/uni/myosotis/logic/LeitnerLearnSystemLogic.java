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
     * Delegates the exercise to update a Learnsystem to the LearnsystemRepository.
     *
     * @param learnsystem The Learnsystem that should be updated.
     */
    public void updateLearnsystem(LeitnerLearnSystem learnsystem) {
        leitnerLearnSystemRepository.updateLearnSystem(learnsystem);
    }


    public LeitnerLearnSystem learnLeitnerSystem(final String name , List<String> indexcardList, int numberOfBoxes) {
        LeitnerLearnSystem learnSystem = leitnerLearnSystemRepository.getLeitnerLearnSystemByName(name);
        if (learnSystem!=null) {
            return learnSystem;
        } else {
            saveLeitnerLearnSystem(name, indexcardList, numberOfBoxes);
            if (leitnerLearnSystemRepository.getLeitnerLearnSystemByName(name) == null)
                throw new RuntimeException("LeitnerLearnSystem could not be saved.)");
            return leitnerLearnSystemRepository.getLeitnerLearnSystemByName(name);
        }
    }

    public void saveLeitnerLearnSystem(String name, List<String> indexcardList, int numberOfBoxes) {
        leitnerLearnSystemRepository.saveLeitnerLearnSystem(name, indexcardList, numberOfBoxes);
    }
}
