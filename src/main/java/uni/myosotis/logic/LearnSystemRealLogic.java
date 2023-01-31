package uni.myosotis.logic;

import uni.myosotis.persistence.LearnSystemRealRepository;
import uni.myosotis.persistence.LearnsystemRepository;

public class LearnSystemRealLogic {

    /**
     * The repository for the Learnsystems.
     */
    final LearnSystemRealRepository learnSystemRealRepository;

    public LearnSystemRealLogic() {
        this.learnSystemRealRepository = new LearnSystemRealRepository();
    }
}
