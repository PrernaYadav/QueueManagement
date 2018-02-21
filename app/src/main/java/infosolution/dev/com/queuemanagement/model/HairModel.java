package infosolution.dev.com.queuemanagement.model;

import android.widget.TextView;

/**
 * Created by amit on 2/21/2018.
 */

public class HairModel {
    int Image;
    String Name,Waiting;

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getWaiting() {
        return Waiting;
    }

    public void setWaiting(String waiting) {
        Waiting = waiting;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    String Id;

}
