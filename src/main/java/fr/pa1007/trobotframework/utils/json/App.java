package fr.pa1007.trobotframework.utils.json;


import java.util.HashMap;
import java.util.Map;

public class App {

    private String              name;
    private Page                page;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public String getName() {
        return name;
    }

    public Page getPage() {
        return page;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


