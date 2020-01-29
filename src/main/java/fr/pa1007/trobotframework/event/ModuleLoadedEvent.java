package fr.pa1007.trobotframework.event;

import fr.pa1007.trobotframework.info.ModuleInfo;

public class ModuleLoadedEvent extends Event<ModuleInfo> {

    /**
     * This constructor can create an event with all the parameters given
     *
     * @param source the source, of type given in the <code>Event<T></code>
     */
    public ModuleLoadedEvent(ModuleInfo source) {
        super(source.getName(), "Module loaded", source);
    }
}
