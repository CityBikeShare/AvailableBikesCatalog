package beans;

import core.Bikes;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

    public List<Bikes> getBikesByRegion(String region) {
        TypedQuery<Bikes> query = entityManager.createNamedQuery("Bikes.getByRegion", Bikes.class);
        query.setParameter("region", region);

        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }
}
