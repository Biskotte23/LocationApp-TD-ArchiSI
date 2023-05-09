package fr.univtours.polytech.locationapp.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import fr.univtours.polytech.locationapp.dao.AddressDao;
import fr.univtours.polytech.locationapp.model.address.Feature;

@Stateless
public class AddressBusinessImpl implements AddressBusinessLocal {
	@Inject
	private AddressDao addressDao;

	@Override
	public List<Feature> searchAdresses(String search) {
		List<Feature> features = addressDao.getAddresses(search);
		
		return features;
	}

}
