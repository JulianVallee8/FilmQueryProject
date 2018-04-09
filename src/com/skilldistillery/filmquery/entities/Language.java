package com.skilldistillery.filmquery.entities;

public class Language {
	private String name;

	public Language(String langName) {
		this.name = langName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
