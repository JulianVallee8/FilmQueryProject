package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
  }

  private void test() {
    Film film = db.getFilmById(1);
    System.out.println(film);
  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
    
	  System.out.println("Enter 1 to enter Film ID");
	  System.out.println("Enter 2 to enter Film Keyword");
	  System.out.println("Enter 3 to Exit");
	  
	  int key = input.nextInt();
	  
	  switch(key) {
    case 1:
    		System.out.println("Enter a film id");
    		int id = input.nextInt();
    		Film film = db.getFilmById(id);
    		film.setActors(db.getActorsByFilmId(id));
    		getFilm(film);
    		break;
    case 2:
    		System.out.println("Enter a film keyword");
    		String sd = input.next();
    		List<Film> filmByTitle = db.getFilmByTitle(sd);
    		for( int i = 0; i < filmByTitle.size(); i++) {
    			filmByTitle.get(i).setActors(db.getActorsByFilmId(filmByTitle.get(i).getId()));
    		}
    		getMultiFilms(filmByTitle);
    		break;
    case 3:
    		System.out.println("Exiting app");
    		break;
    		
    }
  }
  
  private void getFilm(Film film) {
	  System.out.println("Film ID " + film.getId());
	  System.out.println("Film Title " + film.getTitle());
	  System.out.println("Film Description " + film.getDescription());
	  System.out.println("Film Release Year " + film.getRelease_year());
	  System.out.println("Film Language ID " + film.getLanguage_id());
	  System.out.println("Film Language " + db.getLanguageWithID(film.getLanguage_id()).getName());
	  System.out.println("Film Rental Duration " + film.getRental_duration());
	  System.out.println("Film Rental Rate " + film.getRental_rate());
	  System.out.println("Film Length " + film.getLength());
	  System.out.println("Film Replacement Cost " + film.getReplacement_cost());
	  System.out.println("Film Rating " + film.getRating());
	  System.out.println("Film Special Features " + film.getSpecial_features());
	  getCast(film);
	  
	  
  }
  
  private void getCast(Film film) {
	  if(film == null) {
		  System.out.println("Doesn't have a cast");
		  return;
	  }
	  if(film.getActors() == null) {
		  System.out.println("Doesn't have a cast");
		  return;
	  }
	  for(int i=0; i < film.getActors().size(); i++) {
		 String fn = film.getActors().get(i).getFirstName();
		 String ln = film.getActors().get(i).getLastName();
		 int id = film.getActors().get(i).getId();
		 
	  System.out.print("Actor " + (i+1) +  ": "  + fn + " " + ln + " " + id + " ");
	  
	  }
	  
	  
  }
  
  private void getMultiFilms(List<Film> listOfFilms) {
	  if (listOfFilms == null) {
		  System.out.println("Sorry not in list");
		  return ;
	  }
	  for( int i = 0; i < listOfFilms.size(); i++) {
		  
		  getFilm(listOfFilms.get(i));
		  System.out.println();
		  
	  }
  }

}
