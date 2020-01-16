package fr.pa1007.trobotframework.info;

import java.util.Arrays;
import java.util.Objects;

/**
 * The type Module info.
 */
public class ModuleInfo {

    /**
     * The Name of the framework.
     */
    protected String name;

    /**
     * The Id of the framework.
     */
    protected String id;

    /**
     * The Description of the framework.
     */
    protected String description;

    /**
     * The Main class of the framework.
     */
    protected String mainClass;

    /**
     * The Authors of the framework.
     */
    protected String[] authors;

    /**
     * The Version of the framework.
     */
    protected String version;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets main class.
     *
     * @return the main class
     */
    public String getMainClass() {
        return mainClass;
    }

    /**
     * Get authors string [ ].
     *
     * @return the string [ ]
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Is incomplete boolean.
     *
     * @return the boolean
     */
    public boolean isIncomplete() {
        return name.isEmpty() || mainClass.isEmpty() || version.isEmpty() || id.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleInfo)) {
            return false;
        }
        ModuleInfo that = (ModuleInfo) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(id, that.id) &&
               Objects.equals(description, that.description) &&
               Objects.equals(mainClass, that.mainClass) &&
               Arrays.equals(authors, that.authors) &&
               Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, id, description, mainClass, version);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }

    @Override
    public String toString() {
        return "ModuleInfo{" +
               "name='" + name + '\'' +
               ", id='" + id + '\'' +
               ", description='" + description + '\'' +
               ", mainClass='" + mainClass + '\'' +
               ", authors=" + Arrays.toString(authors) +
               ", version='" + version + '\'' +
               '}';
    }
}
