import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mio on 2015/12/9.
 */
public class Feature <E> {

    protected State requiredForwardState;
    protected State requiredCurrentState;

    public Feature(State requiredForwardState, State forwardState) {
        this.requiredForwardState = requiredForwardState;
        this.requiredCurrentState = forwardState;
    }

    public int isSatisfied(State currentState, State forwardState) {
        return requiredForwardState == forwardState && requiredCurrentState == currentState ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        Feature o = (Feature)obj;
        return requiredForwardState == o.requiredForwardState &&
                requiredCurrentState == o.requiredCurrentState;
    }

    public static <E extends Feature> List<E> createFeatures() {
        State[] states = State.values();
        List<E> featureList = new ArrayList<>();

        for (int i = 0; i < states.length - 1; i++) {
            featureList.add((E)new Feature(states[i], states[i+1]));
            featureList.add((E)new Feature(states[i+1], states[i]));
        }

        return featureList;
    }
}

class ForwardFeature extends Feature {


    public ForwardFeature(String word, State requiredForwardState, State forwardState) {
        super(requiredForwardState, forwardState);
    }

}

class PrevFeature extends Feature {


    public PrevFeature(String word, State requiredPrevState, State forwardState) {
        super(requiredPrevState, forwardState);
    }

}