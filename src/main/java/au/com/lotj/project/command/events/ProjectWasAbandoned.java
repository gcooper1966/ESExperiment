package au.com.lotj.project.command.events;

import au.com.lotj.project.command.domain.Project;

import java.util.Map;

/**
 * Created by Graeme on 3/05/2015.
 */
public class ProjectWasAbandoned extends ProjectEvent {
    public ProjectWasAbandoned(Project source, Map<String, Object> properties) {
        super(source, properties);
    }
}
