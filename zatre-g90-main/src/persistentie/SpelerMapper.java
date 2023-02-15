package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

/**
 * SpelerMapper voert wijzigingen van spelers door naar de database.
 */
public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO ID372258_SDP1G90.Speler (Gebruikersnaam, Geboortejaar, aantalSpellen)"
			+ "VALUES (?, ?, ?)";
	private static final String UPDATE_SPELER = "UPDATE ID372258_SDP1G90.Speler SET aantalSpellen = ? WHERE Gebruikersnaam = ?";

	/**
	 * Speler registreren in de database.
	 * @param 	speler
	 * 			Speler die geregistreerd moet worden.
	 */
	public void voegSpelerToe(Speler speler) {

		try (Connection connect = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = connect.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getJaar());
			query.setInt(3, speler.getAantalSpellen());
			query.executeUpdate();

		} catch (SQLException exc) {
			throw new RuntimeException(exc);
		}

	}

	/**
	 * Geeft een overzicht van alle spelers in de database.
	 * @return	Lijst van spelerobjecten.
	 */
	public List<Speler> geefSpelers() {
		List<Speler> spelers = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement("SELECT * FROM ID372258_SDP1G90.Speler");
				ResultSet rs = query.executeQuery()) {

			while (rs.next()) {
				String gebruikersnaam = rs.getString("Gebruikersnaam");
				int geboortejaar = rs.getInt("Geboortejaar");
				int aantalSpellen = rs.getInt("aantalSpellen");

				spelers.add(new Speler(gebruikersnaam, geboortejaar, aantalSpellen));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return spelers;
	}

	/**
	 * Een specifieke speler uit de database halen.
	 * @param 	gebruikersnaam
	 * 			Gebruikersnaam van de specifieke speler.
	 * @param 	geboortejaar
	 * 			Geboortejaar van de specifieke speler.
	 * @return	Spelerobject van de specifieke speler.
	 */
	public Speler geefSpeler(String gebruikersnaam, int geboortejaar) {
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(
						"SELECT * FROM ID372258_SDP1G90.Speler WHERE Gebruikersnaam = ? AND Geboortejaar = ?")) {
			query.setString(1, gebruikersnaam);
			query.setInt(2, geboortejaar);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					int geboorteJaar = rs.getInt("Geboortejaar");
					int aantalKansen = rs.getInt("aantalSpellen");
					speler = new Speler(gebruikersnaam, geboorteJaar, aantalKansen);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return speler;

	}

	/**
	 * Opslaan/aanpassen van het aantal spellen dat een speler kan spelen.
	 * @param 	speler
	 * 			De specifieke speler waarvan het aantal spelletjes gewijzigd moet worden.
	 */
	public void slaAantalSpellenOp(Speler speler) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(UPDATE_SPELER)) {
			query.setInt(1, speler.getAantalSpellen());
			query.setString(2, speler.getGebruikersnaam());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
