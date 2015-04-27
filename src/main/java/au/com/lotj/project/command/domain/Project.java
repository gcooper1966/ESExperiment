package au.com.lotj.project.command.domain;

import au.com.lotj.common.AggregateIdentifier;
import au.com.lotj.common.AggregateWithId;
import au.com.lotj.common.Event;
import au.com.lotj.common.EventApplicator;
import au.com.lotj.project.command.events.ProjectChangedEvent;
import au.com.lotj.project.command.events.ProjectCommencedEvent;
import au.com.lotj.project.command.events.ProjectCompletedEvent;

import java.util.*;

/**
 * Created by Graeme Cooper on 20/04/2015.
 * The <code>Project</code> represents an aggregate root container that
 * encapsulates the important relationships between a body of work being
 * undertaken and the behavioural changes that occur as the body of work
 * progresses to completion.
 */
public class Project implements AggregateWithId {

    private Date createdDate;
    private Optional<Date> closedDate = Optional.empty();
    private int ownerId;
    private int decisionTreeId;
    private AggregateIdentifier id;
    private ProjectCommandValidator validator;
    private ProjectState state;
    private Map<String, Object> properties;
    private EventApplicator applicator;

    public Project(int ownerId, int decisionTreeId, ProjectCommandValidator validator) {
        this.ownerId = ownerId;
        this.decisionTreeId = decisionTreeId;
        this.validator = validator;
        this.state = ProjectState.NEW;
        this.properties = new HashMap<>();
    }

    /**
     * Commences a new <code>Project</code> by processing the <code>CommenceProjectCommand</code>
     * which moves the project into a started state.
     * @param cmd   the command to process
     * @return      the list of events to be processed by the caller
     * @throws InvalidProjectCommandException
     */
    public List<Event> process(CommenceProjectCommand cmd) throws InvalidProjectCommandException {
        UpdateProperties(cmd.getProperties());
        validator.validate(this, cmd);
        state = ProjectState.STARTED;
        createdDate = new Date();
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(new ProjectCommencedEvent(this, cmd.getProperties()));
        return events;
    }

    /**
     * Updates an existing <code>Project</code> by processing the <code>UpdateProjectCommand</code>
     * which adds or updates properties on the project without effecting the state of the project.
     * @param cmd   the command to process
     * @return      the list of events to be processed by the caller
     * @throws InvalidProjectCommandException
     */
    public List<Event> process(UpdateProjectCommand cmd) throws InvalidProjectCommandException{
        validator.validate(this, cmd);
        UpdateProperties(cmd.getProperties());
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(new ProjectChangedEvent(this, cmd.getProperties()));
        return events;
    }

    /**
     * Completes an existing <code>Project</code> by processing the <code>CommenceProjectCommand</code>
     * which adds or updates properties on the project and moves the project into a closed state which
     * cannot be reopened.
     * @param cmd   the command to be processed
     * @return      the list of events to be processed by the caller
     * @throws InvalidProjectCommandException
     */
    public List<Event> process(CompleteProjectCommand cmd) throws InvalidProjectCommandException{
        validator.validate(this, cmd);
        this.closedDate = Optional.of(new Date());
        state = ProjectState.COMPLETED;
        UpdateProperties(cmd.getProperties());
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(new ProjectCompletedEvent(this, cmd.getProperties()));
        return events;
    }

    /**
     * Completes an existing <code>Project</code> by processing  the <code></code>
     * @param cmd
     * @return
     * @throws InvalidProjectCommandException
     */
    public List<Event> process(AbandonProjectCommand cmd)throws InvalidProjectCommandException{
        validator.validate(this, cmd);
        this.closedDate = Optional.of(new Date());
        state = ProjectState.ABANDONED;
        UpdateProperties(cmd.getProperties());
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(new ProjectCompletedEvent(this, cmd.getProperties()));
        return events;
    }

    public Project applyEvents(List<Event> eventHistory){
        eventHistory.forEach(this::apply);
        return this;
    }

    protected void apply(Event evt){
        String eventClassName = evt.getClass().getName();
        switch (eventClassName){
            case "au.com.lotj.project.command.events.ProjectCommencedEvent":
                this.apply((ProjectCommencedEvent) evt);
                break;
            case "au.com.lotj.project.command.events.ProjectChangedEvent":
                this.apply((ProjectChangedEvent) evt);
                break;
            case "au.com.lotj.project.command.events.ProjectCompletedEvent":
                this.apply((ProjectCompletedEvent) evt);
        }
    }

    protected void apply(ProjectCommencedEvent e){
        System.out.println("Inside ProjectCommencedEvent");
    }

    protected void apply(ProjectChangedEvent e){
        System.out.println("Inside ProjectChangedEvent");
    }

    protected void apply(ProjectCompletedEvent e){
        System.out.println("Inside ProjectCompletedEvent");
    }

    /**
     * Gets the unique identifier for this instance of a project.
     * @return
     */
    public Optional<AggregateIdentifier> getId() {
        return Optional.ofNullable(id);
    }


    /**
     * Gets the date the project moved to the <code>STARTED</code> state.
     * @return
     */
    public Optional<Date> getStartDate() {
        return Optional.ofNullable(createdDate);
    }

    /**
     *  Gets a property for the Project based on the property's name.
     * @param propertyName
     * @return
     */
    public Object getProperty(String propertyName){
        return properties.get(propertyName);
    }

    /**
     * Gets the date the project moved to either COMPLETED or ABANDONED.
     * @return
     */
    public Optional<Date> getClosedDate() {
        return closedDate;
    }

    /**
     * Gets the current state of the project.
     * @return
     */
    public ProjectState getState() {
        return state;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getDecisionTreeId() {
        return decisionTreeId;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Loops through the keys of the props map and updates the properties of this <code>Project</code>
     * if the key already exists or adds it and its value if it doesn't.
     * @param props the properties to set on this instance
     */
    protected void UpdateProperties(final Map<String, Object> props){
        //loop through the properties keys, check to see if it already exists,
        //if it does, update it, if it doesn't add it.
        props.keySet().forEach((k) -> {
                    if(this.properties.get(k) == null){
                        this.properties.put(k, props.get(k));
                    }else{
                        this.properties.replace(k, props.get(k));
                    }
                }
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (ownerId != project.ownerId) return false;
        if (decisionTreeId != project.decisionTreeId) return false;
        if (!createdDate.equals(project.createdDate)) return false;
        if (closedDate != null ? !closedDate.equals(project.closedDate) : project.closedDate != null) return false;
        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (state != project.state) return false;
        return !(properties != null ? !properties.equals(project.properties) : project.properties != null);

    }

    @Override
    public int hashCode() {
        int result = createdDate.hashCode();
        result = 31 * result + ownerId;
        return result;
    }
}
