package in.nitjsr.cognitio.Modals;

public class Modal {
    private String eventName;
    private String image;
    private String eventPrize;
    private String description;
    private String timeStamp;
    private int drawableImage;

    public Modal(String image, String description, String eventPrize, String eventName) {
        this.eventPrize = eventPrize;
        this.eventName = eventName;
        this.image = image;
        this.description = description;
    }

    public Modal(String image, String eventName) {
        this.eventName = eventName;
        this.image = image;
    }

    public String getEventPrize() {
        return eventPrize;
    }

    public String getEventName() {
        return eventName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public String getDescription() {
        return description;
    }
}