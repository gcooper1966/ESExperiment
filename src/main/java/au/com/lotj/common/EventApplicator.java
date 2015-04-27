package au.com.lotj.common;

/**
 * Created by Graeme Cooper on 25/04/2015.
 */
public interface EventApplicator {
    <T extends Event> void apply(T eventToApply, AggregateWithId stateContainer);
}
