package fr.pa1007.trobotframework.utils.json;

import java.util.HashMap;
import java.util.Map;

public class Page {

    public  String              type;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}