package au.com.lotj.project.command.events;

import au.com.lotj.common.Event;
import au.com.lotj.project.command.domain.Project;

import java.util.Map;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class ProjectEvent implements Event {
    public Map<String, Object> getProperties() {
        return properties;
    }

    private Map<String, Object> properties;
    private Project project;

    public ProjectEvent(Project source, Map<String, Object> properties) {
        this.project = source;
        this.properties = properties;
    }
}
