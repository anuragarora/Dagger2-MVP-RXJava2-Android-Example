package com.anurag.flickr.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Library to facilitate communication between application components.
 */

@Module
public class EventBusModule {
    private EventBus sEventBus = EventBus.builder().throwSubscriberException(true).build();

    @Provides
    @Singleton
    public EventBus eventBus() {
        return sEventBus;
    }
}
