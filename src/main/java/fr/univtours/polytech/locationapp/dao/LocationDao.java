package fr.univtours.polytech.locationapp.dao;

import java.util.List;

import fr.univtours.polytech.locationapp.model.LocationBean;

public interface LocationDao {

	public void createLocation(LocationBean bean);

	public List<LocationBean> getLocations();

	public LocationBean getLocation(Integer id);

	public void updateLocation(LocationBean locationBean);

	public void deleteLocation(LocationBean locationBean);
}
