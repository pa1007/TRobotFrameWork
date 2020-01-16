package fr.pa1007.trobotframework.event;

import fr.pa1007.trobotframework.utils.Module;

public class ApplicationSelectedEvent extends Event<Module> {

    /**
     * This constructor can create an event with all the parameters given
     *
     * @param name                the name of the event
     * @param descriptionOptional the description
     * @param source              the source, of type given in the <code>Event<T></code>
     */
    public ApplicationSelectedEvent(String name, String descriptionOptional, Module source) {
        super(name, descriptionOptional, source);
    }

}
