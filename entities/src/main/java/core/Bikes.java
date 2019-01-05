package core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity(name = "Bikes")
@NamedQueries({
        @NamedQuery(name = "Bikes.getAll", query = "SELECT b from Bikes b"),
        //@NamedQuery(name = "Bikes.getByRegion", query = "SELECT b FROM Bikes b INNER JOIN Users u ON u.user_id=b.user_id WHERE u.region = :region"),
        @NamedQuery(name = "Bikes.getByUserId", query = "SELECT b FROM Bikes b WHERE b.user_id = :userId")
})
public class Bikes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bike_id;
    private Integer user_id;
    private String type;
    private String size;
    private String description;
    private double price;
    private boolean isAvailable;

    public Integer getBike_id() {
        return bike_id;
    }

    public void setBike_id(Integer bike_id) {
        this.bike_id = bike_id;
    }

    @ManyToOne
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


}
