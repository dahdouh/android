package com.example.ecoleenligne.model;

import java.util.ArrayList;
import java.util.List;

public class User {

	public int id;
	public String firstname;
	public String lastname;
	public String email;
	public String tel;
	public String ville;
	public String level;
	public String sex;
	public Compte compte;
	public List<Course> courses = new ArrayList<>();
	
	public List<Recommendation> recommendations;

	public User() { }

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public User(int id, String firstname, String lastname, String level) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.level = level;
	}

	public User(String firstname, String lastname, String email, String tel, String ville) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.tel = tel;
		this.ville = ville;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
