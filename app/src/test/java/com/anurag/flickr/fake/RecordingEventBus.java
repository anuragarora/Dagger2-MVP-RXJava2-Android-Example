package com.anurag.flickr.fake;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Fake of EventBus to facilitate argument capture & testing
 */
public class RecordingEventBus extends EventBus {
    private List<Object> mEvents = new ArrayList<>();
    private Object mLastRegisteredObject;

    @Override
    public void post(Object event) {
        mEvents.add(event);
    }

    public Object getLastPostedEvent() {
        return mEvents.get(mEvents.size() - 1);
    }

    public List<Object> getAllPostedEvents() {
        return mEvents;
    }

    @Override
    public void register(Object subscriber) {
        mLastRegisteredObject = subscriber;
    }

    @Override
    public void register(Object subscriber, int priority) {
        mLastRegisteredObject = subscriber;
    }

    public Object getLastRegisteredObject() {
        return mLastRegisteredObject;
    }

    public void clear() {
        mEvents.clear();
        mLastRegisteredObject = null;
    }
}
