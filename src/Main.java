import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mio on 2015/12/9.
 */
public class Main {

    static String punctuations = ",.:;!?";

    public static void main(String[] args) {


        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("BosonNLP_NER_6C.txt")));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            TenFold tenFold = new TenFold(lines);
            tenFold.extractInfo();

            for (int i = 0; i < 10; i++) {
                Fold fold = tenFold.folds[i];


                List<PrevFeature> prevFeatureList = PrevFeature.createFeatures();
                List<ForwardFeature> forwardFeatureList = ForwardFeature.createFeatures();

                FeatureOnWord featureOnWord = new FeatureOnWord(WordInfo.createFromFold(fold));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Fold {
    public List<String> trainData;
    public List<String> testData;
    public Map<String, State> trainNers;
    public Map<String, State> testNers;

    public Fold() {
        trainData = new ArrayList<>();
        testData = new ArrayList<>();
        trainNers = new HashMap<>();
        testNers = new HashMap<>();
    }

    public State getTrainState(String s) {
        if (trainNers.containsKey(s))
            return trainNers.get(s);
        else
            return State.NONE;
    }

    public State getTestState(String s) {
        if (testNers.containsKey(s))
            return testNers.get(s);
        else
            return State.NONE;
    }
}


class TenFold {


    public Fold[] folds;

    public TenFold(List<String> data) {
        folds = Util.createArray(Fold.class, 10, new Object[0]);

        int ceil = (int) Math.ceil(data.size() / 10.0);
        Random random = new Random();

        for (String string : data) {
            while (true) {
                int foldIndex = random.nextInt(10);
                if (folds[foldIndex].testData.size() < ceil) {
                    folds[foldIndex].testData.add(string);
                    break;
                }
            }
        }

        for (int i = 0; i < folds.length; i++) {
            folds[i].trainData.addAll(Util.collectionMinus(data, folds[i].testData));
        }
    }

    public void extractInfo() {
        for (int i = 0; i < 10; i++) {
            String regex = "\\{\\{[^}]+:[^}]+\\}\\}";
            List<String> trainFold = folds[i].trainData;
            for (int j = 0; j < trainFold.size(); j++) {
                String s = trainFold.get(j);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s);
                String substitute = new String(s);
                while (matcher.find()) {
                    String subString = s.substring(matcher.start(), matcher.end());
                    String[] tokens = subString.substring(2).split("[:}]");

                    folds[i].trainNers.put(tokens[1], State.fromString(tokens[0]));
                    substitute = substitute.replaceFirst(regex, tokens[1]);
                }
                trainFold.set(j, substitute);
            }
            List<String> testFold = folds[i].testData;
            for (int j = 0; j < testFold.size(); j++) {
                String s = testFold.get(j);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s);
                String substitute = new String(s);
                while (matcher.find()) {
                    String subString = s.substring(matcher.start(), matcher.end());
                    String[] tokens = subString.substring(2).split("[:}]");

                    folds[i].testNers.put(tokens[1], State.fromString(tokens[0]));
                    substitute = substitute.replaceFirst(regex, tokens[1]);
                }
                testFold.set(j, substitute);
            }
        }
    }

}







