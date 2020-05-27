package com.example.ecoleenligne.model;

import java.util.ArrayList;
import java.util.List;

public class User {

	public int id;
	public String firstname;
	public String lastname;
	public String email;
	public String facebook;
	public String twitter;
	public Compte compte;
	public List<Course> courses = new ArrayList<>();
	
	public List<Recommendation> recommendations;

	public User() { }

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public User(String firstname, String lastname, String email, String facebook, String twitter) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.facebook = facebook;
		this.twitter = twitter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	
	
	
	
}
