package fr.pa1007.trobotframework.manager;

import fr.pa1007.trobotframework.event.Event;
import fr.pa1007.trobotframework.listener.Listener;
import fr.pa1007.trobotframework.utils.Utils;
import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EventManager {

    private static final Map<Class<? extends Event<?>>, List<Listener>> listEvent =
            Collections.synchronizedMap(new HashMap<>());

    private EventManager() {

    }

    public static <E extends Event<?>> void trigger(E event) {
        if (listEvent.containsKey(event.getClass())) {
            listEvent.get(event.getClass()).forEach((e) -> new Thread(() -> e.listener(event)).start());

        }
    }

    public static <E extends Event<?>, L extends Listener<E>> void addListener(Class<E> event, L listener) {
        if (listEvent.containsKey(event)) {
            listEvent.get(event).add(listener);
        }
        else {
            List<Listener> list = Collections.synchronizedList(new ArrayList<>());
            list.add(listener);
            listEvent.put(event, list);
        }
    }

    public static <E extends Event<?>, L extends Listener<E>> void removeListener(E event, L listener) {
        if (listEvent.containsKey(event.getClass())) {
            listEvent.get(event.getClass()).remove(listener);
        }
    }

    public static void removeListener(Listener<?> listener) {
        listEvent.values().removeIf(value -> value.contains(listener));
    }

    public static <E extends Event, L extends Listener> void forceTrigger(E event, L listener) {
        new Thread(() -> {
            try {
                listener.listener(event);
            }
            catch (ClassCastException ex) {
                Utils.getLogger().error("Error while forcing an event the event to the listener "
                                        + listener.getClass().getName()
                                        + "; \t Trying to send \""
                                        + event.getClass().getName()
                                        + "\" To a \""
                                        + listener.getClass().getInterfaces()[0].getName()
                                        + "\" Listener");
            }
        }).start();
    }
}
