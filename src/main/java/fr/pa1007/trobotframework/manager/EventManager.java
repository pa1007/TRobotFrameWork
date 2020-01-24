package fr.pa1007.trobotframework.manager;

import fr.pa1007.trobotframework.event.Event;
import fr.pa1007.trobotframework.listener.Listener;

import java.util.*;

public class EventManager {

    private static final Map<Class<? extends Event<?>>, List<Listener<?>>> listEvent =
            Collections.synchronizedMap(new HashMap<>());

    private EventManager() {

    }

    public static void trigger(Event<?> event) {
        if (listEvent.containsKey(event.getClass())) {
            for (Listener l : listEvent.get(event.getClass())) {
                new Thread(() -> {
                    l.listener(event);
                }).start();
            }
        }
    }

    public  static void addListener(Class<? extends Event<?>> event, Listener<?> listener) {
        if (listEvent.containsKey(event)) {
            listEvent.get(event).add(listener);
        } else {
            List<Listener<?>> list = Collections.synchronizedList(new ArrayList<>());
            list.add(listener);
            listEvent.put(event, list);
        }
    }

    public static void removeListener(Event<?> event, Listener<?> listener) {
        if (listEvent.containsKey(event.getClass())) {
            listEvent.get(event.getClass()).remove(listener);
        }
    }

    public static void removeListener(Listener<?> listener) {
        listEvent.values().removeIf(value -> value.contains(listener));
    }

}
