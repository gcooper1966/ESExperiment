package au.com.lotj.common;

import java.util.List;
import java.util.Optional;

/**
 * Created by Graeme Cooper on 20/04/2015.
 */
public interface AggregateWithId {
    Optional<AggregateIdentifier> getId();
    AggregateWithId applyEvents(List<Event> events);
}
