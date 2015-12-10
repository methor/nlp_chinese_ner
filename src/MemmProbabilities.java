import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    List<PrevFeature> prevFeatureList = PrevFeature.createFeatures();
    List<ForwardFeature> forwardFeatureList = ForwardFeature.createFeatures();


    public void addProbability(TriTuple triTuple, double pr) {
        probabilities.put(triTuple, pr);
    }

    public double getProbability(TriTuple triTuple) {
        return probabilities.get(triTuple);
    }


    public MemmProbabilities(List<WordInfo> wordInfoList) {
        State[] states = State.values();
        List<Integer> sums = new ArrayList<>();
        List<Double> prs = new ArrayList<>();
        List<TriTuple> triTuples = new ArrayList<>();

        for (WordInfo wordInfo : wordInfoList) {
            for (int i = 0; i < states.length; i++) {
                State var1 = states[i];


                for (int j = 0; j < states.length; j++) {
                    if (j == i)
                        continue;

                    State var2 = states[j];
                    int sum = 0;


                    for (PrevFeature prevFeature : prevFeatureList) {
                        sum += FeatureOnWord.featureOnWord(prevFeature, wordInfo, var2);
                    }
                    for (ForwardFeature forwardFeature : forwardFeatureList) {
                        sum += FeatureOnWord.featureOnWord(forwardFeature, wordInfo, var2);
                    }

                    sums.add(sum);
                    triTuples.add(new TriTuple(var2, var1, sum));
                }

                int sumAll = 0;
                for (Integer sum : sums) {
                    sumAll += sum;
                }
                for (int k = 0; k < sums.size(); k++)
                    prs.add((double)sums.get(k) / sumAll);


            }
        }
    }


}
