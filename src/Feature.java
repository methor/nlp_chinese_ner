/**
 * Created by Mio on 2015/12/9.
 */
public class Feature {

    private BinaryFeature binaryFeature;
    private State state;

    public Feature(BinaryFeature binaryFeature, State state) {
        this.binaryFeature = binaryFeature;
        this.state = state;
    }

    public int isSatisfied() {
        if (binaryFeature.isSatisfied() && state == binaryFeature.getWordState())
            return 1;
        else
            return 0;
    }

}
