package fr.univtours.polytech.locationapp.ws;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.univtours.polytech.locationapp.business.LocationBusinessLocal;
import fr.univtours.polytech.locationapp.model.LocationBean;

@Path("api")
@Stateless
public class LocationWS {
	@EJB
	private LocationBusinessLocal businessLocation;

	@GET
	@Path("locations")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<LocationBean> getLocations(@QueryParam("sort") String sort, @QueryParam("city") String city) {
		List<LocationBean> locations = this.businessLocation.getLocations();

		if (sort != null) {
			if (sort.equals("asc")) {
				Collections.sort(locations, (l1, l2) -> {
					return l1.getCity().compareTo(l2.getCity());
				});
			}

			else if (sort.equals("desc")) {
				Collections.sort(locations, (l1, l2) -> {
					return -1 * l1.getCity().compareTo(l2.getCity());
				});
			}
		}

		if (city != null) {
			locations = locations.stream().filter(l -> l.getCity().toUpperCase().equals(city.toUpperCase()))
					.collect(Collectors.toList());
		}

		return locations;
	}

	@GET
	@Path("locations/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public LocationBean getLocation(@PathParam("id") int id) {
		LocationBean location = this.businessLocation.getLocation(id);

		return location;
	}

	@POST
	@Path("locations")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createLocation(LocationBean locationBean) {
		Response response = Response.ok().build();

		try {
			this.businessLocation.addLocation(locationBean);
		} catch (Exception e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		return response;
	}

	@POST
	@Path("locations")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response createLocation(@FormParam("address") String address, @FormParam("city") String city,
			@FormParam("nightPrice") Double price, @FormParam("zipCode") String zipCode) {
		Response response = Response.ok().build();

		try {
			LocationBean location = new LocationBean();

			location.setAddress(address);
			location.setCity(city);
			location.setNightPrice(price);
			location.setZipCode(zipCode);

			this.businessLocation.addLocation(location);
		} catch (Exception e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}

		return response;
	}

	@PUT
	@Path("locations/{id}")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public LocationBean putLocation(@PathParam("id") int id, @FormParam("address") String address, @FormParam("city") String city,
			@FormParam("nightPrice") Double price, @FormParam("zipCode") String zipCode) {
		LocationBean location = new LocationBean();

		location.setId(id);
		location.setAddress(address);
		location.setCity(city);
		location.setNightPrice(price);
		location.setZipCode(zipCode);

		this.businessLocation.updateLocation(location);

		return location;
	}
	
	@PATCH
	@Path("locations/{id}")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public LocationBean patchLocation(@PathParam("id") int id, @FormParam("address") String address, @FormParam("city") String city,
			@FormParam("nightPrice") Double price, @FormParam("zipCode") String zipCode) {
		LocationBean location = this.businessLocation.getLocation(id);
		
		if (location != null) {
			if (address != null) {
				location.setAddress(address);
			}
			
			if (city != null) {
				location.setCity(city);
			}
			
			if (price != null) {
				location.setNightPrice(price);
			}
			
			if (zipCode != null) {
				location.setZipCode(zipCode);
			}
		}

		return location;
	}

	@DELETE
	@Path("locations/{id}")
	public Response deleteLocation(@PathParam("id") int id,
			@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
		Response response = Response.status(Status.NOT_FOUND).build();

		if (authorization == null) {
			authorization = "";
		}

		// If the user is logged in.
		if (authorization.matches("^Bearer [0-9]+$")) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(authorization);
			matcher.find();
			int token = Integer.valueOf(matcher.group(0));

			// If the token is correct.
			if (token == 42) {

				// If the location exists.
				if (this.businessLocation.getLocation(id) != null) {
					this.businessLocation.deleteLocation(id);
					response = Response.ok().build();
				} else {
					response = Response.status(Status.BAD_REQUEST).build();
				}
			} else {
				response = Response.status(Status.UNAUTHORIZED).build();
			}
		} else {
			response = Response.status(Status.FORBIDDEN).build();
		}

		return response;
	}
}
