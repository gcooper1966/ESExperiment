package au.com.lotj.project.command.events;

import au.com.lotj.common.Event;
import au.com.lotj.project.command.domain.Project;

import java.util.Map;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class ProjectWasCompleted extends ProjectEvent {
    public ProjectWasCompleted(Project project, Map<String, Object> properties) { super(project, properties); }
}
