package au.com.lotj.project.command.events;

import au.com.lotj.project.command.domain.Project;

import java.util.Map;

/**
 * Created by Graeme on 3/05/2015.
 */
public class ProjectTaskWasDeleted extends ProjectEvent {
    public ProjectTaskWasDeleted(Project source, Map<String, Object> properties) {
        super(source, properties);
    }
}
