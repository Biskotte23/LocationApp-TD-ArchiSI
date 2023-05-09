package fr.univtours.polytech.locationapp.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.univtours.polytech.locationapp.model.LocationBean;

@Stateless
public class LocationDaoImpl implements LocationDao {

	@PersistenceContext(unitName = "LocationApp")
	private EntityManager em;

	@Override
	public void createLocation(LocationBean bean) {
		em.persist(bean);
	}

	@Override
	public List<LocationBean> getLocations() {
		Query request = em.createQuery("select l from LocationBean l");
		return request.getResultList();
	}

	@Override
	public LocationBean getLocation(Integer id) {
		return em.find(LocationBean.class, id);
	}

	@Override
	public void updateLocation(LocationBean locationBean) {
		em.merge(locationBean);
	}

	@Override
	public void deleteLocation(LocationBean locationBean) {
		em.remove(locationBean);
	}

}
