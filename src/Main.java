import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Mio on 2015/12/9.
 */
public class Main {

    public static void main(String[] args) {


        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("BosonNLP_NER_6C.txt")));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class TenFold {
    public List<String>[] trainData;
    public List<String>[] testData;

    public TenFold(List<String> data) {
        trainData = Util.createArray(ArrayList.class, 10, new Object[0]);
        testData = Util.createArray(ArrayList.class, 10, new Object[0]);

        int ceil = (int) Math.ceil(data.size() / 10.0);
        Random random = new Random();

        for (String string : data) {
            while (true) {
                int foldIndex = random.nextInt(10);
                if (testData[foldIndex].size() < ceil) {
                    testData[foldIndex].add(string);
                    break;
                }
            }
        }

        for (int i = 0; i < testData.length; i++) {
            trainData[i].addAll(Util.collectionMinus(data, testData[i]));
        }
    }
}


class PrevWordFeature extends BinaryFeature {


    public PrevWordFeature(String word, String prevWord, State state) {
        super(word, prevWord, state);
    }


    @Override
    public boolean isSatisfied() {
        return word.equals(object);
    }
}


class CurrentWordFeature extends BinaryFeature {


    public CurrentWordFeature(String word, String currentWord, State state) {
        super(word, currentWord, state);
    }


    @Override
    public boolean isSatisfied() {
        return word.equals(object);
    }
}

class Util<E> {
    public static int boolToInt(boolean b) {
        return b == true ? 1 : 0;
    }

    public static boolean intToBool(int i) {
        return i == 0 ? false : true;
    }

    public static int compare(Number n1, Number n2, boolean type) {

        switch (Util.boolToInt(type)) {
            case 1:
                return Integer.compare(n1.intValue(), n2.intValue());
            case 0:
                return Double.compare(n1.doubleValue(), n2.doubleValue());
            default:
                throw new RuntimeException();
        }

    }


    public static <T extends Comparable> int indexWithMax(T[] array) {
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(array[index]) > 0)
                index = i;

        }

        return index;
    }


    public static <T extends Object> T[] createArray(Class<T> type, int len, Object... args) {

        T[] result = (T[]) Array.newInstance(type, len);

        Class<?>[] classes = new Class[args.length];
        for (int i = 0; args != null && i < args.length; i++)
            classes[i] = args[i].getClass();
        try {
            for (int i = 0; i < result.length; i++) {
                result[i] = type.getConstructor(classes).newInstance(args);
            }
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return result;

    }

    public static <E> List<E> collectionMinus(List<E> from, List<E> target) {

        List<E> result = new ArrayList<>();
        for (E e : from) {
            if (!target.contains(e))
                result.add(e);
        }

        return result;
    }
}






