package com.example.ecoleenligne.model;

import java.util.Date;



public class LogSession {

	public int id;
	public Date dateConnexion;
	public Date dateDeconnexion;
	public User user;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateConnexion() {
		return dateConnexion;
	}
	public void setDateConnexion(Date dateConnexion) {
		this.dateConnexion = dateConnexion;
	}
	public Date getDateDeconnexion() {
		return dateDeconnexion;
	}
	public void setDateDeconnexion(Date dateDeconnexion) {
		this.dateDeconnexion = dateDeconnexion;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
