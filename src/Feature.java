import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mio on 2015/12/9.
 */
public class Feature <E> {

    protected State requiredForwardWordState;
    protected State requiredCurrentState;

    public Feature(State requiredForwardState, State forwardState) {
        this.requiredForwardWordState = requiredForwardState;
        this.requiredCurrentState = forwardState;
    }

    public int isSatisfied(State currentState, State forwardState) {
        return requiredForwardWordState == forwardState && requiredCurrentState == currentState ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        Feature o = (Feature)obj;
        return requiredForwardWordState == o.requiredForwardWordState &&
                requiredCurrentState == o.requiredCurrentState;
    }

    public static <E extends Feature> List<E> createFeatures() {
        State[] states = State.values();
        List<E> featureList = new ArrayList<>();

        for (State var1 : states) {
            for (State var2 : states) {
                featureList.add((E)new Feature(var1, var2));
            }
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