package beans.core;

import core.Bikes;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BikesBean {

    @PersistenceContext(unitName = "cityBikeShare-jpa")
    private EntityManager entityManager;

    public List<Bikes> getBikes() {
        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getAll", Bikes.class);
        return query.getResultList();
    }

    public Bikes getBikeById(int bikeId) {
        return entityManager.find(Bikes.class, bikeId);
    }

//    public List<Bikes> getBikesByRegion(String region) {
//        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getByRegion", Bikes.class);
//        query.setParameter("region", region);
//
//        try {
//            return query.getResultList();
//        } catch (NoResultException | NonUniqueResultException e) {
//            return null;
//        }
//    }

    public List<Bikes> getBikesByUserId(int userId) {
        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getByUserId", Bikes.class);
        query.setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    @Transactional
    public Bikes insertNewBike(Bikes bike) {
        entityManager.persist(bike);
        entityManager.flush();
        return bike;
    }

    @Transactional
    public Bikes updateBike(int bikeId, Bikes bike){
        try {
            Bikes tempBike = entityManager.find(Bikes.class, bikeId);
            bike.setBike_id(tempBike.getBike_id());
            bike = entityManager.merge(bike);
        } catch(Exception e) {
            return null;
        }
        return bike;
    }

    @Transactional
    public boolean deleteBike(int bikeId) {
        try {
            Bikes bike = entityManager.find(Bikes.class, bikeId);
            entityManager.remove(bike);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
