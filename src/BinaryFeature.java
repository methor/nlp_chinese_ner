/**
 * Created by Mio on 2015/12/9.
 */
public abstract class BinaryFeature <E> {

    protected String word;
    protected E object;
    protected State wordState;

    public BinaryFeature(String word, E object, State wordState) {
        this.word = word;
        this.object = object;
        this.wordState = wordState;
    }

    public State getWordState() {
        return wordState;
    }

    public abstract boolean isSatisfied();
}
