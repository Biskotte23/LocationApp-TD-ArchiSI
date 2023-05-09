package fr.univtours.polytech.locationapp.business;

import java.util.List;

import javax.ejb.Remote;

import fr.univtours.polytech.locationapp.model.LocationBean;

@Remote
public interface LocationBusinessRemote {

	public void addLocation(LocationBean bean);

	public List<LocationBean> getLocations();

	public LocationBean getLocation(Integer id);
}
