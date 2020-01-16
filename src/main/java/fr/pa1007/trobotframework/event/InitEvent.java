package fr.pa1007.trobotframework.event;

import fr.pa1007.trobotframework.info.ModuleInfo;

public class InitEvent extends Event<ModuleInfo> {

    /**
     * This constructor can create an event with all the parameters given
     *
     * @param name   the name of the event
     * @param source the source, of type given in the <code>Event<T></code>
     */
    public InitEvent(String name, ModuleInfo source) {
        super(name, "A new module loading", source);
    }


}
