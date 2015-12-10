import java.util.*;

/**
 * Created by Mio on 2015/12/9.
 */
public class Function {


    public static int C = 1000;

    public static List<Double> function2List = null;
    public static List<Double> function3List = null;

    // F_a
    public static double function2(Feature feature, List<WordInfo> wordInfoList) {

        int sum = 0;
        for (WordInfo wordInfo : wordInfoList) {
            for (State state : State.values())
                sum += featureOnWord(feature, wordInfo, state);
        }


        return (double) sum / wordInfoList.size();

    }

    // f_a(o, s)
    public static int featureOnWord(Feature feature, WordInfo wordInfo, State var1) {
        State var2;
        if (feature instanceof PrevFeature)
            var2 = wordInfo.mapper.get(wordInfo.prev);
        else
            var2 = wordInfo.mapper.get(wordInfo.next);

        return feature.isSatisfied(var1, var2);
    }

    private static double _function1(List<Feature> featureList, List<Double> coefficientList, WordInfo wordInfo, State state) {
        if (featureList.size() != coefficientList.size())
            throw new RuntimeException();

        double sum = 0;
        for (int i = 0; i < featureList.size(); i++) {
            sum += coefficientList.get(i) * featureOnWord(featureList.get(i), wordInfo, state);
        }

        return sum;
    }

    // P_s'(s|o)
    public static double function1(State var1, State var2, WordInfo wordInfo, List<Feature> featureList,
                                   List<Double> coefficientList) {

        double sum = 0;
        for (int i = 0; i < State.values().length; i++) {
            sum += Math.pow(Math.E, _function1(featureList, coefficientList, wordInfo, State.values()[i]));
        }
        return Math.pow(Math.E, _function1(featureList, coefficientList, wordInfo, var1)) / sum;
    }


    // E_a^(j)
    public static double function3(Feature feature, List<WordInfo> wordInfoList, List<Feature> featureList,
                                   List<Double> coefficientList) {

        double sum = 0;
        for (WordInfo wordInfo : wordInfoList) {
            for (State var2 : State.values()) {
                for (State var1 : State.values()) {
                    sum += function1(var1, var2, wordInfo, featureList, coefficientList) * featureOnWord(feature, wordInfo, var1);
                }
            }
        }

        return sum / wordInfoList.size();
    }


    // lamda_a^(j+1)
    public static double function4(List<Double> coefficientList, int index, List<WordInfo> wordInfoList,
                                         List<Feature> featureList) {

        double function2Result, function3Result;
        if (function2List != null)
            function2Result = function2List.get(index);
        else
            function2Result = function2(featureList.get(index), wordInfoList);

        if (function3List != null)
            function3Result = function3List.get(index);
        else
            function3Result = function3(featureList.get(index), wordInfoList, featureList, coefficientList);

        return coefficientList.get(index) + Math.log(function2Result /
            function3Result) / C;

    }


}
