package au.com.lotj.project.command.domain;

import au.com.lotj.common.Command;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class ProjectValidator {

    public void validate(Project project) throws InvalidProjectCommandException {
        if(project.getId().isPresent() || project.getState() == ProjectState.STARTED){
            throw new InvalidProjectCommandException("Cannot restart an already started project");
        }

    }
}
