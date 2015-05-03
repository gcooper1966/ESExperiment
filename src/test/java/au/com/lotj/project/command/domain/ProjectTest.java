package au.com.lotj.project.command.domain;

import au.com.lotj.common.Event;
import au.com.lotj.project.command.events.ProjectWasChanged;
import au.com.lotj.project.command.events.ProjectWasStarted;
import au.com.lotj.project.command.events.ProjectWasCompleted;
import au.com.lotj.project.utils.ProjectProperties;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class ProjectTest {

    private ProjectValidator validator = new ProjectValidator();
    private int ownerId = 1;
    private int decisionTreeId = 1;


    @org.junit.Test
    public void testValidStartOfProject() throws InvalidProjectCommandException{

        Project project = new Project(ownerId, decisionTreeId, validator);
        assertThat(project.getState(), is(ProjectState.NEW));

        CommenceProjectCommand cmd = new CommenceProjectCommand(ProjectProperties.buildProjectProperties());
        List<Event> events = project.changeProject(cmd);

        assertThat(project.getState(), is(ProjectState.STARTED));
        assertThat(events, hasSize(1));
        assertThat(events.get(0), is(instanceOf(ProjectWasStarted.class)));
    }

    @Test
    public void testValidUpdateOfProject() throws Exception {
        Project project = new Project(ownerId, decisionTreeId, validator);
        assertThat(project.getState(), is(ProjectState.NEW));
        project.changeProject(new CommenceProjectCommand(ProjectProperties.buildProjectProperties()));
        assertThat(project.getState(), is(ProjectState.STARTED));

        UpdateProjectCommand cmd = new UpdateProjectCommand(ProjectProperties.buildProjectProperties());
        List<Event> events = project.changeProject(cmd);

        assertThat(project.getState(), is(ProjectState.STARTED));
        assertThat(events, hasSize(1));
        assertThat(events.get(0), is(instanceOf(ProjectWasChanged.class)));

    }

    @Test
    public void testValidCompletionOfProject() throws Exception {
        Project project = new Project(ownerId, decisionTreeId, validator);
        assertThat(project.getState(), is(ProjectState.NEW));
        project.changeProject(new CommenceProjectCommand(ProjectProperties.buildProjectProperties()));
        assertThat(project.getState(), is(ProjectState.STARTED));
        project.changeProject(new UpdateProjectCommand(ProjectProperties.buildProjectProperties()));
        assertThat(project.getState(), is(ProjectState.STARTED));

        CompleteProjectCommand cmd = new CompleteProjectCommand(ProjectProperties.buildProjectProperties());
        List<Event> events = project.startProject(cmd);

        assertThat(project.getState(), is(ProjectState.COMPLETED));
        assertThat(events, hasSize(1));
        assertThat(events.get(0), is(instanceOf(ProjectWasCompleted.class)));
    }

    @Test
    public void testValidAbandonmentOfProject() throws Exception {
        Project project = new Project(ownerId, decisionTreeId, validator);
        assertThat(project.getState(), is(ProjectState.NEW));
        project.changeProject(new CommenceProjectCommand(ProjectProperties.buildProjectProperties()));
        assertThat(project.getState(), is(ProjectState.STARTED));

        AbandonProjectCommand cmd = new AbandonProjectCommand(ProjectProperties.buildProjectProperties());
        List<Event> events = project.startProject(cmd);

        assertThat(project.getState(), is(ProjectState.ABANDONED));
        assertThat(events, hasSize(1));
        assertThat(events.get(0), is(instanceOf(ProjectWasCompleted.class)));
    }

    @Test
    public void testCanApplyEvents(){
        Project project = new Project(ownerId, decisionTreeId, validator);
        ProjectWasStarted evt = new ProjectWasStarted(null, null);
        List<Event> events = new ArrayList<>();
        events.add(evt);

        project.applyEvents(events);
    }

}