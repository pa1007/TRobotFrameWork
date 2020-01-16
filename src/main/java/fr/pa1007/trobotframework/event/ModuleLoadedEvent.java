package fr.pa1007.trobotframework.event;

public class ModuleLoadedEvent extends Event<Module> {

    /**
     * This constructor can create an event with all the parameters given
     *
     * @param source the source, of type given in the <code>Event<T></code>
     */
    public ModuleLoadedEvent(Module source) {
        super(source.getName(), "Module loaded", source);
    }
}
