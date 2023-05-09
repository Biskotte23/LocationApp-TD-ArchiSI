package fr.univtours.polytech.locationapp.model;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: LocationBean
 *
 */
@Entity
@XmlRootElement
public class LocationBean implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double nightPrice;
	private String address;
	private String city;
	private String zipCode;
	@Lob
	private byte[] picture;
	private static final long serialVersionUID = 1L;

	public LocationBean() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getNightPrice() {
		return this.nightPrice;
	}

	public void setNightPrice(Double nightPrice) {
		this.nightPrice = nightPrice;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	/**
	 * Transforme le byte[] contenant les donn�es de l'images en String.
	 * 
	 * @return La cha�ne de caract�re permettant l'affichage d'une image depuis une
	 *         JSP.
	 */
	public String getBase64Image() {
		if (null != this.picture && !"".equals(this.picture)) {
			return Base64.getEncoder().encodeToString(this.picture);
		} else {
			return "";
		}
	}
}
