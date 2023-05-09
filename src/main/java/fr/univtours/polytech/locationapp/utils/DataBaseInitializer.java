package fr.univtours.polytech.locationapp.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fr.univtours.polytech.locationapp.business.LocationBusinessLocal;
import fr.univtours.polytech.locationapp.model.LocationBean;

@Singleton
@Startup
public class DataBaseInitializer {

	@Inject
	private LocationBusinessLocal locationBusiness;

	@PostConstruct
	public void init() {
		LocationBean bean1 = insertBeanInDB("12 chemin de la Vallée", "Les Arcs", "73700", 150D, "1.jpg");
		locationBusiness.addLocation(bean1);

		LocationBean bean2 = insertBeanInDB("15 route de la montagne", "Les Arcs", "73700", 253D, "2.jpg");
		locationBusiness.addLocation(bean2);

		LocationBean bean3 = insertBeanInDB("25 route de la montagne", "La Plagne", "73210", 75D, "3.jpg");
		locationBusiness.addLocation(bean3);

		LocationBean bean4 = insertBeanInDB("35 route des monts", "La Clusaz", "74220", 58.5D, "4.jpg");
		locationBusiness.addLocation(bean4);
	}

	/**
	 * Permet d'insérer un enregistrement en BDD.
	 * 
	 * @param address
	 * @param city
	 * @param zipCode
	 * @param nightPrice
	 * @param picture
	 */
	private LocationBean insertBeanInDB(String address, String city, String zipCode, Double nightPrice,
			String picture) {
		LocationBean bean = new LocationBean();

		bean.setAddress(address);
		bean.setCity(city);
		bean.setZipCode(zipCode);
		bean.setNightPrice(nightPrice);

		InputStream is = getClass().getResourceAsStream(picture);
		try {
			bean.setPicture(IOUtils.InputStreamToByteArray(is));
		} catch (IOException e) {
			System.err.println(picture + " n'a pas pu être insérée en base.");
			e.printStackTrace();
			bean.setPicture(null);
		}

		return bean;
	}
}
