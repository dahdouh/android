package com.example.ecoleenligne.model;

import java.util.List;



public class Exercice {

	public int id;
	public String libelle;
	public double note;
	public Course course;
	public List<Qcm> Qcms;
	public List<Qcm> Qrs;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public double getNote() {
		return note;
	}
	public void setNote(double note) {
		this.note = note;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public List<Qcm> getQcms() {
		return Qcms;
	}
	public void setQcms(List<Qcm> qcms) {
		Qcms = qcms;
	}
	public List<Qcm> getQrs() {
		return Qrs;
	}
	public void setQrs(List<Qcm> qrs) {
		Qrs = qrs;
	}
	
	

}
