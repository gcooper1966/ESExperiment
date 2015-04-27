package au.com.lotj.project.command.domain;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class InvalidProjectCommandException extends Exception {
    public InvalidProjectCommandException() {
        super();
    }

    public InvalidProjectCommandException(String message) {
        super(message);
    }
}
