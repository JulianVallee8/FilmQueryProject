package com.skilldistillery.filmquery.database;

import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Language;

public interface DatabaseAccessor {
  public Film getFilmById(int filmId);
  public Actor getActorById(int actorId);
  public List<Actor> getActorsByFilmId(int filmId);
  public List<Film> getFilmByTitle(String filmTitle);
  public Language getLanguageWithID(int langID);
  
  
  
  
  
  
  
}
