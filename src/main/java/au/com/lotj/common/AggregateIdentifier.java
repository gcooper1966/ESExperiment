package au.com.lotj.common;

/**
 * Created by Graeme Cooper on 20/04/2015.
 */
public class AggregateIdentifier {

    private int id;

    private String aggregateName;

    public AggregateIdentifier(int id, String aggregateName) {
        this.id = id;
        this.aggregateName = aggregateName;
    }

    public int getId() {
        return id;
    }

    public String getAggregateName() {
        return aggregateName;
    }
}
