package fr.pa1007.trobotframework.event;

import fr.pa1007.trobotframework.utils.Module;

public class ModuleLoadedEvent extends Event<Module> {

    /**
     * This constructor can create an event with all the parameters given
     *
     * @param source the source, of type given in the <code>Event<T></code>
     */
    public ModuleLoadedEvent(Module source) {
        super(source.getInfo().getName(), "Module loaded", source);
    }
}
