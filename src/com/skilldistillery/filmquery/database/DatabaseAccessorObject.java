package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid";
	private static final String user = "student";
	private static final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to load DB driver. Exiting");
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
  public Film getFilmById(int filmId) throws SQLException {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			String userWildCardTitle = "%"+ filmId + "%";
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			Film film = null;
			boolean isMovieFound = false;
			while (rs.next()) {
				int filmID = rs.getInt(1);
				String filmTitle = rs.getString(2);
				String filmDesc = rs.getString(3);
				int filmReleaseYR = rs.getInt(4);
				String filmLang = rs.getString(5);
				int filmRentalDuration = rs.getInt(6);
				int filmRentalRate = rs.getInt(7);
				int filmLength = rs.getInt(8);
				int filmReplaceCost = rs.getInt(9);
				String filmRating = rs.getString(10);
				String filmSpecFeature = rs.getString(11);
				List<Actor> actors = getActorsByFilmId(filmID);
				
				System.out.println(filmID + " " + filmTitle + " " + filmDesc + " "+ filmReleaseYR + 
						" "+ filmLang + " " +  filmRentalDuration + " " + filmRentalRate + " " + filmLength +
						" " + filmReplaceCost+ " "+ filmRating+ " " + filmSpecFeature);
				isMovieFound = true;
				
				 film = new Film(filmID, filmTitle, filmDesc, filmReleaseYR, filmLang, filmRentalDuration,
						filmRentalRate, filmLength, filmReplaceCost, filmRating, filmSpecFeature, actors);
				
			}
			if ( ! isMovieFound )
				System.out.println("Is not in List");

			rs.close();
			stmt.close();
			conn.close();
			return film;
		}


@Override
public Actor getActorById(int actorId) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		String userWildCardActor = "%"+ actorId + "%";
		stmt.setInt(1, actorId);
		ResultSet rs = stmt.executeQuery();

		Actor actor = null;
		boolean isMovieFound = false;
		while (rs.next()) {
			int actorID = rs.getInt(1);
			String actorFirstName = rs.getString(2);
			String actorLastName = rs.getString(3);
			
			
			 actor = new Actor(actorID, actorFirstName, actorLastName);
			
		}
		if ( ! isMovieFound )
			System.out.println("Is not in List");

		rs.close();
		stmt.close();
		conn.close();
		return actor;
	}



@Override
public List<Actor> getActorsByFilmId(int filmId) throws SQLException {
	
	Connection conn = DriverManager.getConnection(URL, user, pass);

	String sql = "SELECT a.id, a.first_name, a.last_name FROM actor a JOIN film_actor fa ON fa.actor_id = a.id WHERE fa.film_id = ?" ; 
	PreparedStatement stmt = conn.prepareStatement(sql);
	String userWildCardActor = "%"+ filmId + "%";
	stmt.setInt(1, filmId);
	ResultSet rs = stmt.executeQuery();

	List<Actor> actorList = null;
	boolean isMovieFound = false;
	while (rs.next()) {
		int actorId = rs.getInt(1);
		String actorFirstName = rs.getString(2);
		String actorLastName = rs.getString(3);
		
		Actor actorL = new Actor( actorId, actorFirstName, actorLastName);
		
		 actorList = new ArrayList<Actor>();
		 actorList.add(actorL);
		 
		 
		
	}
	if ( ! isMovieFound )
		System.out.println("Is not in List");

	rs.close();
	stmt.close();
	conn.close();
	return actorList;
}


}
