package it.polito.tdp.yelp.db;

import java.util.List;

import it.polito.tdp.yelp.model.User;

public class TestDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		YelpDao dao = new YelpDao();
//		System.out.println(String.format("Users: %d\nBusiness: %d\nReviews: %d\n", 
//				dao.getAllUsers().size(), dao.getAllBusiness().size(), dao.getAllReviews().size()));
		
		List<User> utenti= dao.getUsersWithReviews(200);
		System.out.println(utenti);
		System.out.println(utenti.size());
//		int similarita= dao.calcolaSimilarita(null, null, 0)
		
	}

}
