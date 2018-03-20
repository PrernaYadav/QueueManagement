package infosolution.dev.com.queuemanagement.model;

/**
 * Created by Shreyansh Srivastava on 3/20/2018.
 */

public class ServiceListModel {
    String Image;
    String Id;

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    String Price;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    String Name;
}
