package fr.univtours.polytech.locationapp.business;

import java.util.List;

import javax.ejb.Local;

import fr.univtours.polytech.locationapp.model.address.Feature;

@Local
public interface AddressBusinessLocal {
	public List<Feature> searchAdresses(String search);
}
