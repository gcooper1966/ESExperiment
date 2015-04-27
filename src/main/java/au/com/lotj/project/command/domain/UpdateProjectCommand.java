package au.com.lotj.project.command.domain;

import au.com.lotj.common.Command;

import java.util.Map;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class UpdateProjectCommand extends ProjectCommand{

    public UpdateProjectCommand(Map<String,Object> updatedValues){
        super(updatedValues);
    }
}
