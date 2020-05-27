package com.example.ecoleenligne.model;


public class Course {

	public int id;
 	public String libelle;
 	public String progress;
	public String level;
	public String description;
	public String image;


	public Course(){

	}
	public Course(int id, String libelle, String description, String image){
		this.id = id;
		this.libelle = libelle;
		this.description = description;
		this.image = image;
	}

	public Course(String libelle, String description, String image, String level, String progress){
		this.libelle = libelle;
		this.description = description;
		this.image = image;
		this.level = level;
		this.progress = progress;

	}

	public Course(int id, String libelle, String description, String image, String level, String progress){
		this.id = id;
		this.libelle = libelle;
		this.description = description;
		this.image = image;
		this.level = level;
		this.progress = progress;

	}

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
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
