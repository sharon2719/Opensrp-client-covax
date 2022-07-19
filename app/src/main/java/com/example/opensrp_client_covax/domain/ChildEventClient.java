package com.example.opensrp_client_covax.domain;

import org.smartregister.clientandeventmodel.Client;
import org.smartregister.clientandeventmodel.Event;

public class ChildEventClient {
    private Event event;
    private Client client;

    public ChildEventClient(Client client, Event event) {
        this.client = client;
        this.event = event;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
