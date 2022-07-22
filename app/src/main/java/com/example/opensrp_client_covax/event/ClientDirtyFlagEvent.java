package com.example.opensrp_client_covax.event;

import com.vijay.jsonwizard.event.BaseEvent;

public class ClientDirtyFlagEvent extends BaseEvent {
    private String eventType;
    private String baseEntityId;

    public ClientDirtyFlagEvent(String baseEntityId, String eventType) {

        this.baseEntityId = baseEntityId;
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }
}
