import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mio on 2015/12/9.
 */
public class FeatureOnWord {

    List<WordInfo> wordInfoList;

    public FeatureOnWord(List<WordInfo> wordInfoList) {

        this.wordInfoList = wordInfoList;

    }


    public double featureFunction(Feature feature) {

        int sum = 0;
        for (WordInfo wordInfo : wordInfoList) {
            for (State state : State.values())
                sum += featureOnWord(feature, wordInfo, state);
        }


        return (double) sum / wordInfoList.size();

    }

    public static int featureOnWord(Feature feature, WordInfo wordInfo, State var1) {
        State var2;
        if (feature instanceof PrevFeature)
            var2 = wordInfo.mapper.get(wordInfo.prev);
        else
            var2 = wordInfo.mapper.get(wordInfo.next);

        return feature.isSatisfied(var1, var2);
    }


}
