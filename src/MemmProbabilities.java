import java.util.*;

/**
 * Created by Mio on 2015/12/9.
 */
public class MemmProbabilities {

    class TriTuple {
        State prev;
        State current;
        WordInfo observation;

        public TriTuple(State current, State prev, WordInfo observation) {
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


    public double getProbability(TriTuple triTuple) {
        return probabilities.get(triTuple);
    }


    public MemmProbabilities(List<WordInfo> wordInfoList, List<Feature> featureList) {
        List<Double> coefficientList = Arrays.asList(Util.createArray(Double.class, featureList.size(), "1"));
        setMemmProbabilities(wordInfoList, featureList, coefficientList);

    }

    private void setMemmProbabilities(List<WordInfo> wordInfoList, List<Feature> featureList,
                                      List<Double> coefficientList) {

        for (WordInfo wordInfo : wordInfoList) {
            for (State var2 : State.values()) {
                for (State var1 : State.values()) {
                    probabilities.put(new TriTuple(var1, var2, wordInfo), Function.function1(
                            var1, var2, wordInfo, featureList, coefficientList));
                }
            }
        }
    }


    public void train(List<WordInfo> wordInfoList, List<Feature> featureList) {
        List<Double> coefficientList = Arrays.asList(Util.createArray(Double.class, featureList.size(), "1"));

        List<Double> function2List = new ArrayList<>();
        for (int i = 0; i < featureList.size(); i++) {

            function2List.add(Function.function2(featureList.get(i), wordInfoList));
        }

        Function.function2List = function2List;

        List<Double> function3List = new ArrayList<>();
        for (int i = 0; i < featureList.size(); i++) {

            function3List.add(Function.function3(featureList.get(i), wordInfoList, featureList,
                    coefficientList));
        }
        Function.function3List = function3List;

        List<Double> function4List = new ArrayList<>();
        for (int i = 0; i < featureList.size(); i++) {

            function4List.add(Function.function4(coefficientList, i, wordInfoList, featureList));
        }

        List<Double> function4NewList = new ArrayList<>();
        while (true) {

            function3List.clear();
            for (int i = 0; i < featureList.size(); i++) {
                function3List.add(Function.function3(featureList.get(i), wordInfoList, featureList,
                        function4List));

            }
            Function.function3List = function3List;


            for (int i = 0; i < featureList.size(); i++) {

                function4NewList.add(Function.function4(function4List, i, wordInfoList, featureList));
            }

            for (int i = 0; i < function4NewList.size(); i++) {
                if (Math.abs(function4NewList.get(i) - function4List.get(i)) > function4List.get(i) * 0.2) {
                    Util.breakFromLoop();
                    break;
                }
            }
            if (Util.breakHappened())
                break;

            function4List = function4NewList;
        }

        setMemmProbabilities(wordInfoList, featureList, function4NewList);


    }


    public Map<String, State> test(String line) {

        Map<String, State> map = new HashMap<>();
        List<WordInfo> wordInfoList;
        State var2 = State.NONE;
        int var2Start = -1;
        int var2End = -1;

        for (WordInfo wordInfo : wordInfoList) {
            List<Double> list1 = new ArrayList<>();
            for (State var1 : State.values()) {
                list1.add(getProbability(new TriTuple(var1, var2, wordInfo)));
            }

            int index = Util.indexWithMax(list1.toArray(new Double[0]));
            State var1 = State.values()[index];
            if (var2 == State.NONE && var1 == var2) {
                var2Start = wordInfo.start;
                var2End = wordInfo.end;
                var2 = var1;
                if (wordInfoList.indexOf(wordInfo) == wordInfoList.size() - 1)
                    map.put(line.substring(var2Start, var2End), var2);
            }
            else if (var2 == State.NONE && var1 == var2) {
                var2Start = -1;
                var2End = -1;
                continue;
            }
            else if (var2 != State.NONE && var1 != var2) {
                map.put(line.substring(var2Start, var2End), var2);
                var2 = var1;
                var2Start = wordInfo.start;
                var2End = wordInfo.end;
            }
            else {
                var2End = wordInfo.end;
                if (wordInfoList.indexOf(wordInfo) == wordInfoList.size() - 1)
                    map.put(line.substring(var2Start, var2End), var2);
            }
        }


    }


}
