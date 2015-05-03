package au.com.lotj.project.command.events;

import au.com.lotj.common.Event;
import au.com.lotj.project.command.domain.Project;

import java.util.Map;

/**
 * Created by Graeme Cooper on 20/04/2015.
 */
public class ProjectWasStarted extends ProjectEvent {

    public ProjectWasStarted(Project project, Map<String, Object> properties) {super(project, properties); }
}
