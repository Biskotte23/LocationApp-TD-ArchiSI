package fr.univtours.polytech.locationapp.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import fr.univtours.polytech.locationapp.model.LocationBean;
import fr.univtours.polytech.locationapp.model.weather.WsWeatherResult;

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
		List<LocationBean> locations = request.getResultList();
		
		for (LocationBean location : locations) {
			// TODO: Get la température.
			// location.setTemperature(0);
		}
		
		return locations;
	}

	@Override
	public LocationBean getLocation(Integer id) {
		LocationBean location = em.find(LocationBean.class, id);
		
		// TODO: Get la température.
		// location.setTemperature(0);
		
		return location;
	}

	@Override
	public void updateLocation(LocationBean locationBean) {
		em.merge(locationBean);
	}

	@Override
	public void deleteLocation(LocationBean locationBean) {
		em.remove(locationBean);
	}

	private static String URL = "https://api.openweathermap.org/data/2.5";

	@Override
	public WsWeatherResult getWeather(double lat, double lon, String key) {
		Client client = ClientBuilder.newClient();

		// On indique l'URL du Web Service.
		WebTarget target = client.target(URL);

		// On indique le "end point" (on aurait aussi pu directement le mettre dans
		// l'URL).
		// C'est également avec cette méthode qu'on pourrait ajouter des "path
		// parameters" si besoin.
		target = target.path("weather");

		// On précise (lorsqu'il y en a) les "query parameters".
		target = target.queryParam("lat", lat);
		target = target.queryParam("lon", lon);
		target = target.queryParam("appid", key);

		// On appelle le WS en précisant le type de l'objet renvoyé, ici un
		// WsAdressResult.
		WsWeatherResult wsResult = target.request(MediaType.APPLICATION_JSON).get(WsWeatherResult.class);
		return wsResult;
	}

}
