package au.com.lotj.project.utils;

import au.com.lotj.common.utils.Maps;

import java.util.Map;

/**
 * Created by Graeme Cooper on 21/04/2015.
 */
public class ProjectProperties {
    public static Map<String, Object> buildProjectProperties(){
        return Maps.<String, Object>builder().
                put("Description", "Test description").
                put("Name", "Project test description").
                put("OwnerId", 1).
                unmodifiable(true).
                build();
    }
}
