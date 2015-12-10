import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mio on 2015/12/10.
 */
public class WordInfo {

    String word;
    String prev;
    String next;

    Map<String, State> mapper;

    static String punctuations = ",.:;!?";

    public WordInfo(String word, String prev, String next, Map<String, State> mapper) {
        this.word = word;
        this.prev = prev;
        this.next = next;
        this.mapper = mapper;
    }


    public static List<WordInfo> createFromFold(Fold fold) {
        List<String> trainData = fold.trainData;
        List<WordInfo> wordInfoList = new ArrayList<>();

        for (String s : trainData) {
            IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(s), true);
            Lexeme lexeme;

            String var2 = null;
            String var3 = null;
            boolean punctuation = false;
            try {
                while ((lexeme = ikSegmenter.next()) != null) {
                    String var1 = lexeme.getLexemeText();
                    int var1Pos = lexeme.getBeginPosition();

                    if (var1Pos == 0) {
                        var2 = var1;
                        continue;
                    }


                    if (punctuations.indexOf(s.charAt(var1Pos - 1)) != -1)
                        punctuation = true;
                    else
                        punctuation = false;


                    if (punctuation == false) {
                        wordInfoList.add(new WordInfo(var2, var3, var1, fold.trainNers));
                        var3 = var2;
                        var2 = var1;
                    }
                    else {
                        wordInfoList.add(new WordInfo(var2, var3, null, fold.trainNers));
                        var3 = null;
                        var2 = var1;
                    }




                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return wordInfoList;
    }
}
