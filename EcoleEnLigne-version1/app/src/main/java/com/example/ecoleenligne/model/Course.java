package com.example.ecoleenligne.model;

import java.util.List;

public class Course {

	public int id;
	public String libelle;
	public String progress;
	private List<User> users;
	public List<CourseContent> contents;
	public List<Exercice> exercices;
}
