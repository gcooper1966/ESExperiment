package au.com.lotj.project.command.domain;

import au.com.lotj.common.Command;

import java.util.Map;

/**
 * Created by Graeme Cooper on 21/04/2015.
 *
 * Base class for Commands that can be processed by the <code>Project</code> aggregate root class.
 * @see Project
 */
public class ProjectCommand implements Command {

    public Map<String, Object> getProperties() {
        return properties;
    }

    private Map<String, Object> properties;

    public ProjectCommand(Map<String,Object> values){
        properties = values;
    }
}
