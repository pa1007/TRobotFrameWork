package fr.pa1007.trobotframework.utils;

import fr.pa1007.trobotframework.event.ModuleLoadedEvent;

public class ExempleModule extends Module {

    @Override
    public Class<ModuleLoadedEvent> getEventClass() {
        return ModuleLoadedEvent.class;
    }

    /**
     * The listener of the event, add the T event to this
     *
     * @param event the event set in the generics type
     */
    @Override
    public void listener(ModuleLoadedEvent event) {

    }
}
