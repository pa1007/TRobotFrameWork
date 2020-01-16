package fr.pa1007.trobotframework.event;

import java.util.Objects;
import java.util.Optional;

public abstract class Event<T> {

    /**
     * The source if provided.
     *
     * @since 1.0
     */
    protected T source;

    /**
     * The event name.
     *
     * @since 1.0
     */
    private String name;

    /**
     * The description.
     *
     * @since 1.0
     */
    private String descriptionOptional;

    /**
     * This constructor can create an event with all the parameters given
     *
     * @param name                the name of the event
     * @param descriptionOptional the description
     * @param source              the source, of type given in the <code>Event<T></code>
     */
    public Event(String name, String descriptionOptional, T source) {
        this.name = name;
        this.descriptionOptional = descriptionOptional;
        this.source = source;
    }

    /**
     * @return The source if provided.
     * @since 1.0
     */
    public Optional<T> getSource() {
        return Optional.ofNullable(this.source);
    }

    /**
     * Sets the <code>source</code> field.
     *
     * @param source The source if provided.
     * @since 1.0
     */
    public void setSource(T source) {
        this.source = source;
    }

    /**
     * @return The desciption.
     * @since 1.0
     */
    public Optional<String> getDescriptionOptional() {
        return Optional.ofNullable(this.descriptionOptional);
    }

    /**
     * Sets the <code>descriptionOptional</code> field.
     *
     * @param descriptionOptional The desciption.
     * @since 1.0
     */
    public void setDescriptionOptional(String descriptionOptional) {
        this.descriptionOptional = descriptionOptional;
    }

    /**
     * @return The event name.
     * @since 1.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the <code>name</code> field.
     *
     * @param name The event name.
     * @since 1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event<?> event = (Event<?>) o;
        return Objects.equals(source, event.source) &&
               Objects.equals(name, event.name) &&
               Objects.equals(descriptionOptional, event.descriptionOptional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, name, descriptionOptional);
    }

    @Override
    public String toString() {
        return "Event{" +
               "source=" + source +
               ", name='" + name + '\'' +
               ", descriptionOptional='" + descriptionOptional + '\'' +
               '}';
    }
}
