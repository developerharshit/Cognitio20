package in.nitjsr.cognitio.Modals;

import java.util.ArrayList;

public class EventsModals {
    String about, details;
    ArrayList<EventHeadModal> eventsHead;

    public EventsModals(String about, String details, ArrayList eventsHead) {
        this.about = about;
        this.details = details;
        this.eventsHead = eventsHead;
    }

    public String getAbout() {
        return about;
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<EventHeadModal> getEventHead() {
        return eventsHead;
    }
}
