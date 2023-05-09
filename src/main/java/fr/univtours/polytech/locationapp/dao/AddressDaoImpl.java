package fr.univtours.polytech.locationapp.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import fr.univtours.polytech.locationapp.model.address.Feature;
import fr.univtours.polytech.locationapp.ws.WsAddressResult;

@Stateless
public class AddressDaoImpl implements AddressDao {

	private static String URL = "https://api-adresse.data.gouv.fr";

	@Override
	public List<Feature> getAddresses(String search) {
	    Client client = ClientBuilder.newClient();

	    // On indique l'URL du Web Service.
	    WebTarget target = client.target(URL);

	    // On indique le "end point" (on aurait aussi pu directement le mettre dans l'URL).
	    // C'est également avec cette méthode qu'on pourrait ajouter des "path parameters" si besoin.
	    target = target.path("search");
	    
	    // On précise (lorsqu'il y en a) les "query parameters".
	    target = target.queryParam("q", search);
	    target = target.queryParam("limit", 15);

	    // On appelle le WS en précisant le type de l'objet renvoyé, ici un WsAdressResult.
	    WsAddressResult wsResult = target.request(MediaType.APPLICATION_JSON).get(WsAddressResult.class);
	    return wsResult.getFeatures();
	}

}
