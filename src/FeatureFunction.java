import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mio on 2015/12/9.
 */
public class FeatureFunction {

    private List<Feature> featureList = new ArrayList<>();

    public void addFeature(Feature feature) {
        featureList.add(feature);
    }

    public double calculate() {
        int sum = 0;
        for (Feature feature : featureList)
            sum += feature.isSatisfied();

        return (double)sum / featureList.size();
    }
}
