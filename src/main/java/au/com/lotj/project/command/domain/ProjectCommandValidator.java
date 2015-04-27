package au.com.lotj.project.command.domain;

import au.com.lotj.common.Command;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class ProjectCommandValidator {

    public void validate(Project project, CommenceProjectCommand cmd) throws InvalidProjectCommandException {
        if(project.getId().isPresent() || project.getState() == ProjectState.STARTED){
            throw new InvalidProjectCommandException("Cannot restart an already started project");
        }

    }

    public void validate(Project project, UpdateProjectCommand cmd) throws InvalidProjectCommandException {
        if(!project.getStartDate().isPresent() || project.getState() != ProjectState.STARTED){
            throw new InvalidProjectCommandException("Cannot update a project that has not yet started. Commence the project first");
        }
        if(project.getClosedDate().isPresent() || project.getState() == ProjectState.COMPLETED || project.getState() == ProjectState.ABANDONED){
            throw new InvalidProjectCommandException("Cannot update a project that has been closed.");
        }
    }

    public void validate(Project project, CompleteProjectCommand cmd) throws InvalidProjectCommandException {
        if(!(project.getId().isPresent() || project.getStartDate().isPresent())){
            throw new InvalidProjectCommandException("Cannot complete a project that has never started. Commence the project first");
        }

    }

    public void validate(Project project, AbandonProjectCommand cmd) throws InvalidProjectCommandException {
        if(project.getState() == ProjectState.COMPLETED || project.getClosedDate().isPresent()){
            throw new InvalidProjectCommandException("Cannot abandon a project that has already been completed.");
        }
    }
}
