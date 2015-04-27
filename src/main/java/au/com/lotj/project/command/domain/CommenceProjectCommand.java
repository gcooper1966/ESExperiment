package au.com.lotj.project.command.domain;

import au.com.lotj.common.Command;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Graeme Cooper on 20/04/2015.
 */
public class CommenceProjectCommand extends ProjectCommand {


    public CommenceProjectCommand(Map<String,Object> initialValues){
        super(initialValues);
    }
}
