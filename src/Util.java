import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mio on 2015/12/10.
 */
public class Util<E> {

    private static boolean breakFromLoop = false;

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

    public static <E> boolean isContained(E e, E[] elements) {

        for (E element : elements) {
            if (e instanceof Object) {
                if (e.equals(element))
                    return true;
            }
            else if (e == element)
                return true;
        }

        return false;
    }

    public static void breakFromLoop() {
        breakFromLoop = true;
    }

    public static boolean breakHappened() {
        boolean temp = breakFromLoop;
        breakFromLoop = false;
        return temp;
    }
}
