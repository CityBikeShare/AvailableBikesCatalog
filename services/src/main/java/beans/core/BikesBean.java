package beans.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import core.Bikes;
import external.Users;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class BikesBean {

    private Logger log = Logger.getLogger(BikesBean.class.getName());

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    @Metered(name = "getBikes")     // Measures how many times this method is called
    public List<Bikes> getBikes() {
        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getAll", Bikes.class);
        return query.getResultList();
    }

    @Metered(name = "getBikeById")
    public Bikes getBikeById(int bikeId) {
        return entityManager.find(Bikes.class, bikeId);
    }

    @Metered(name = "getBikesByRegion")
    public List<Bikes> getBikesByRegion(List<Users> usersByRegion) {
        if (usersByRegion == null) {
            return new ArrayList<>();
        }

        List<Bikes> allBikes = getBikes();
        List<Bikes> bikesInRegion = new ArrayList<>();
        for (int i = 0; i < allBikes.size(); i++) {
            int userId = allBikes.get(i).getUser_id();
            for (int j = 0; j < usersByRegion.size(); j++) {
                int userIdU = usersByRegion.get(j).getUser_id();
                if (userIdU == userId) {
                    bikesInRegion.add(allBikes.get(i));
                    break;
                }
            }
        }
        return bikesInRegion;
    }

    @Metered(name = "getBikesByUserId")
    public List<Bikes> getBikesByUserId(int userId) {
        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getByUserId", Bikes.class);
        query.setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            return new ArrayList<>();
        }
    }

    @Metered(name = "insertNewBike")
    @Transactional
    public Bikes insertNewBike(Bikes bike) {
        entityManager.persist(bike);
        entityManager.flush();
        return bike;
    }

    @Metered(name = "updateBike")
    @Transactional
    public Bikes updateBike(int bikeId, Bikes bike) {
        try {
            Bikes tempBike = entityManager.find(Bikes.class, bikeId);
            bike.setBike_id(tempBike.getBike_id());
            bike = entityManager.merge(bike);
        } catch (Exception e) {
            return null;
        }
        return bike;
    }

    @Metered(name = "deleteBike")
    @Transactional
    public boolean deleteBike(int bikeId) {
        try {
            Bikes bike = entityManager.find(Bikes.class, bikeId);
            entityManager.remove(bike);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Metered(name = "convert")
    @Transactional
    public double convert(int bikeId, String currency) {
        double price = getBikeById(bikeId).getPrice();

        try {
            HttpResponse<JsonNode> response = Unirest.post("https://community-neutrino-currency-conversion.p.rapidapi.com/convert")
                    .header("X-RapidAPI-Key", "5d6690d88bmsh3a9f8d8538d0bedp1768f4jsn63a8da0306ad")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("from-type", "EUR")
                    .field("to-type", currency)
                    .field("from-value", price)
                    .asJson();

            String result = response.getBody().getObject().get("result").toString();
            if(result == null || result.isEmpty()){
                throw new UnirestException("Invalid input data.");
            }
            return Double.parseDouble(result);
        } catch (UnirestException e) {
            log.log(Level.SEVERE, "Request failed! Check if the bikeId and currency are valid.");
            return Double.MIN_VALUE;
        }
    }

}
