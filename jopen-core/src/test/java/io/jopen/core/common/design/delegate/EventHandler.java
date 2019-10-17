package io.jopen.core.common.design.delegate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuefeng
 */
public class EventHandler {

    private List<Event> events = new ArrayList<>();

    public void addEvent(Event e) {
        this.events.add(e);
    }

    public void notifyOther() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Event e : events) {
            e.invoke();
        }
    }
}
