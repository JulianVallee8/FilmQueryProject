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
import com.skilldistillery.filmquery.entities.Language;

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
  public Film getFilmById(int filmId) {
		
		Film film = null;
			try {
				Connection conn = DriverManager.getConnection(URL, user, pass);
				String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film WHERE id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, filmId);
				ResultSet rs = stmt.executeQuery();

				
				boolean isMovieFound = false;
				while (rs.next()) {
					int filmID = rs.getInt(1);
					String filmTitle = rs.getString(2);
					String filmDesc = rs.getString(3);
					int filmReleaseYR = rs.getInt(4);
					int filmLang = rs.getInt(5);
					int filmRentalDuration = rs.getInt(6);
					int filmRentalRate = rs.getInt(7);
					int filmLength = rs.getInt(8);
					int filmReplaceCost = rs.getInt(9);
					String filmRating = rs.getString(10);
					String filmSpecFeature = rs.getString(11);
					List<Actor> actors = getActorsByFilmId(filmID);
					
			
					isMovieFound = true;
					
					 film = new Film(filmID, filmTitle, filmDesc, filmReleaseYR, filmLang, filmRentalDuration,
							filmRentalRate, filmLength, filmReplaceCost, filmRating, filmSpecFeature, actors);
					
				}
				if ( ! isMovieFound )
					System.out.println("Is not in List");

				rs.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return film;
		}

	@Override
	  public List<Film> getFilmByTitle(String filmTitle) {
			
			List<Film> films = new ArrayList<>();
				try {
					Connection conn = DriverManager.getConnection(URL, user, pass);
					String sql = "SELECT title, id, description, release_year, language_id, rental_duration, rental_rate,"
							+ " length, replacement_cost, rating, special_features"
							+ " FROM film WHERE title LIKE  ? OR description LIKE ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					String userWildCardTitle = "%"+ filmTitle + "%";
					stmt.setString(1, userWildCardTitle);
					stmt.setString(2, userWildCardTitle);
					ResultSet rs = stmt.executeQuery();

					
					boolean isMovieFound = false;
					while (rs.next()) {
						
						String filmTitled = rs.getString(1);
						int filmID = rs.getInt(2);
						String filmDesc = rs.getString(3);
						int filmReleaseYR = rs.getInt(4);
						int filmLang = rs.getInt(5);
						int filmRentalDuration = rs.getInt(6);
						int filmRentalRate = rs.getInt(7);
						int filmLength = rs.getInt(8);
						int filmReplaceCost = rs.getInt(9);
						String filmRating = rs.getString(10);
						String filmSpecFeature = rs.getString(11);
						
						
				
						isMovieFound = true;
						
						films.add(new Film(filmTitled, filmID, filmDesc, filmReleaseYR, filmLang, filmRentalDuration,
								filmRentalRate, filmLength, filmReplaceCost, filmRating, filmSpecFeature));
					}
					if ( ! isMovieFound )
						System.out.println("Is not in List");

					rs.close();
					stmt.close();
					conn.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return films;
			}
	
	

@Override
public Actor getActorById(int actorId) {
	
	
		Connection conn;
		PreparedStatement stmt;
		ResultSet rs;
		Actor actor = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			String userWildCardActor = "%"+ actorId + "%";
			stmt.setInt(1, actorId);
			rs = stmt.executeQuery();

			actor = null;
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actor;

	}



@Override
public List<Actor> getActorsByFilmId(int filmId) {
	
	List<Actor> actorList = null;
	try {
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT a.id, a.first_name, a.last_name FROM actor a JOIN film_actor fa ON fa.actor_id = a.id WHERE fa.film_id = ?" ; 
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();

		actorList = new ArrayList<Actor>();
		boolean isMovieFound = false;
		while (rs.next()) {
			int actorId = rs.getInt(1);
			String actorFirstName = rs.getString(2);
			String actorLastName = rs.getString(3);
			
			Actor actorL = new Actor( actorId, actorFirstName, actorLastName);
			
			 
			 actorList.add(actorL);
			 
			 isMovieFound = true;
			
		}
		if ( ! isMovieFound )
			System.out.println("Is not in List");

		rs.close();
		stmt.close();
		conn.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return actorList;
}

	public Language getLanguageWithID(int langID) {
		
			Connection conn;
			PreparedStatement stmt;
			ResultSet rs;
			Language lang = null;
			try {
				conn = DriverManager.getConnection(URL, user, pass);
				String sql = "SELECT Distinct l.name FROM language l Join film f ON l.id = f.language_id WHERE l.id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, langID);
				rs = stmt.executeQuery();

				boolean isMovieFound = false;
				while (rs.next()) {
					String langName = rs.getString(1);
					
					
					lang = new Language(langName);
					
					 isMovieFound = true;
					
				}
				if ( ! isMovieFound )
					System.out.println("Is not in List");
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return lang;
		
	}


}
