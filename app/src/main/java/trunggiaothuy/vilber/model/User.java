package trunggiaothuy.vilber.model;

/**
 * Created by HUNGVIET on 04/04/2017.
 */

public class User {
    private String id;
    private String name;
    private int image;
    private String time;
    private String message;

    public User(String name, int image, String time, String message) {
        this.name = name;
        this.image = image;
        this.time = time;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
