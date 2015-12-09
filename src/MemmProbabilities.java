import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mio on 2015/12/9.
 */
public class MemmProbabilities {

    class TriTuple {
        State prev;
        State current;
        String observation;

        public TriTuple(State current, State prev, String observation) {
            this.current = current;
            this.prev = prev;
            this.observation = observation;
        }

        @Override
        public boolean equals(Object obj) {
            TriTuple o = (TriTuple)obj;
            return prev == o.prev && current == o.current && observation.equals(o.observation);
        }
    }

    private Map<TriTuple, Double> probabilities = new HashMap<>();


    public void addProbability(TriTuple triTuple, double pr) {
        probabilities.put(triTuple, pr);
    }

    public double getProbability(TriTuple triTuple) {
        return probabilities.get(triTuple);
    }


}
