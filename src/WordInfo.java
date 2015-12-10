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
    int start;
    int end;

    Map<String, State> mapper;

    static String punctuations = ",.:;!?";

    public WordInfo(String word, String prev, String next, Map<String, State> mapper,
                    int start, int end) {
        this.word = word;
        this.prev = prev;
        this.next = next;
        this.mapper = mapper;
        this.start = start;
        this.end = end;
    }


    public static List<WordInfo> createFromFold(Fold fold) {
        List<String> trainData = fold.trainData;
        List<WordInfo> wordInfoList = new ArrayList<>();

        for (String s : trainData) {
            IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(s), true);
            Lexeme lexeme;

            String var2 = null;
            String var3 = null;
            int var2Start = -1;
            int var2End = -1;
            boolean punctuation = false;
            try {
                while ((lexeme = ikSegmenter.next()) != null) {
                    String var1 = lexeme.getLexemeText();
                    int var1Start = lexeme.getBeginPosition();

                    if (var1Start == 0) {
                        var2 = var1;
                        var2Start = 0;
                        var2End = lexeme.getEndPosition();
                        continue;
                    }


                    if (punctuations.indexOf(s.charAt(var1Start - 1)) != -1)
                        punctuation = true;
                    else
                        punctuation = false;


                    if (punctuation == false) {
                        wordInfoList.add(new WordInfo(var2, var3, var1, fold.trainNers, var2Start, var2End));
                        var3 = var2;
                        var2 = var1;
                    }
                    else {
                        wordInfoList.add(new WordInfo(var2, var3, null, fold.trainNers, var2Start, var2End));
                        var3 = null;
                        var2 = var1;
                    }

                    var2Start = var1Start;
                    var2End = lexeme.getEndPosition();




                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return wordInfoList;
    }
}
