package fr.univtours.polytech.locationapp.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import fr.univtours.polytech.locationapp.dao.AddressDao;
import fr.univtours.polytech.locationapp.dao.LocationDao;
import fr.univtours.polytech.locationapp.model.LocationBean;
import fr.univtours.polytech.locationapp.model.address.Feature;
import fr.univtours.polytech.locationapp.ws.WsWeatherResult;

@Stateless
public class LocationBusinessImpl implements LocationBusinessLocal, LocationBusinessRemote {

	@Inject
	private LocationDao locationDao;
	
	@Inject
	private AddressDao addressDao;

	@Override
	public void addLocation(LocationBean bean) {
		locationDao.createLocation(bean);
	}

	@Override
	public List<LocationBean> getLocations() {
		List<LocationBean> locations = locationDao.getLocations();
		
		for (LocationBean location : locations) {
			location.setTemperature(getLocationTemperature(location));
		}
		
		return locations;
	}

	@Override
	public LocationBean getLocation(Integer id) {
		LocationBean location = locationDao.getLocation(id);

		location.setTemperature(getLocationTemperature(location));
		
		return location;
				
	}

	@Override
	public void updateLocation(LocationBean locationBean) {
		locationDao.updateLocation(locationBean);
	}

	@Override
	public void deleteLocation(Integer id) {
		LocationBean locationBean = getLocation(id);
		locationDao.deleteLocation(locationBean);
	}

	@Override
	public Double getLocationTemperature(LocationBean location) {
		List<Feature> features = addressDao.getAddresses(location.getAddress() + ", " + location.getZipCode());
		Double temperature = null;
		
		System.out.println("Adresse: " + location.getAddress() + ", " + location.getZipCode() + " / " + features.get(0).toString());
		
		if (features.size() > 0) {
			List<Double> coordinates = features.get(0).getGeometry().getCoordinates();
			System.out.println("Cooronnées: " + coordinates.get(0) + " / " + coordinates.get(1));
			WsWeatherResult weatherResult = locationDao.getWeather(coordinates.get(1), coordinates.get(0));
			temperature = weatherResult.getMain().getTemp() - 273.15;
			System.out.println("Temp Kelvin: " + weatherResult.getMain().getTemp());
		} 
		
		return temperature;
	}

}
