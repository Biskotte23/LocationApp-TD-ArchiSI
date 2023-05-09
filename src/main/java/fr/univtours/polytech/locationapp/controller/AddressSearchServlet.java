package fr.univtours.polytech.locationapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univtours.polytech.locationapp.business.AddressBusinessLocal;
import fr.univtours.polytech.locationapp.model.address.Feature;

@WebServlet("/searchAddress")
public class AddressSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private AddressBusinessLocal addressBusiness;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // On place une liste vide dans la requête.
	    request.setAttribute("ADDRESSES", new ArrayList<Feature>());
	    // On redirige vers la JSP.
	    request.getRequestDispatcher("addresses.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // On récupère ce que l'utilisateur à saisi.
	    String search = request.getParameter("search");
	    // On exécute la requête correspondante.
	    List<Feature> addresses = this.addressBusiness.searchAdresses(search);
	    // On place le résultat de la recherche dans la requête.
	    request.setAttribute("ADDRESSES", addresses);
	    // On redirige vers la JSP.
	    request.getRequestDispatcher("addresses.jsp").forward(request, response);
	}

}
