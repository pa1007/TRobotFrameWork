package fr.pa1007.trobotframework.listener;

import fr.pa1007.trobotframework.event.Event;

public interface Listener<T extends Event> {

    /**
     * The listener of the event, add the T event to this
     *
     * @param event the event set in the generics type
     */
    void listener(T event);
}
