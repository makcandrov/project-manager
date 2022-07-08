package databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataBase {
	protected String dbName = "database";
	protected String dbUrl;
	protected Connection connection;

	public static DataBase db = new DataBase();

	public DataBase(boolean connect) {

		String dbLocation = "db/database.db";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("db/db_location.txt"));
			dbLocation = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.dbUrl = "jdbc:sqlite:" + dbLocation;

		if (connect) {
			connect();
		}
	}

	public DataBase() {
		this(true);
	}

	public void connect() {
		try {
			connection = DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sendVoidSQLRequest(String sql, Object... params) {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ResultArray sendObjectSQLRequest(String sql, Object... params) {
		ArrayList<HashMap<String, Object>> res = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				stmt.setObject(i + 1, params[i]);
			}

			rs = stmt.executeQuery();
			int nc = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> hm = new HashMap<>();
				for (int i = 1; i <= nc; i++) {
					hm.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
				}
				res.add(hm);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return new ResultArray(res);
	}

	public void createTable(String tableName, String... attributs) {
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
		for (int i = 0; i < attributs.length; i++) {
			sql += attributs[i];
			if (i != attributs.length - 1) {
				sql += ", ";
			}
		}
		sql += ")";
		sendVoidSQLRequest(sql);
	}

	// TABLE STRUCTURE

	public int getUserID(String nom, String prenom, String qualification) {
		ResultArray res = null;
		String sql = "SELECT id FROM structure WHERE nom = ? ";
		if (prenom == null) {
			res = sendObjectSQLRequest(sql, nom);
		} else {
			sql += "AND prenom = ? ";
			if (qualification == null) {
				res = sendObjectSQLRequest(sql, nom, prenom);
			} else {
				sql += "AND qualification = ? ";
				res = sendObjectSQLRequest(sql, nom, prenom, qualification);
			}
		}

		if (res.size == 0) {
			return -1;
		}
		return (int) res.get(0, "id");
	}

	public int getUserID(String nom, String prenom) {
		return getUserID(nom, prenom, null);
	}

	public int getUserID(String nom) {
		return getUserID(nom, null, null);
	}

	public void addUser(String nom, String prenom, String qualification, String last_ip, long lastConnexion) {
		String sql = "INSERT INTO structure (nom, prenom, qualification, last_ip, last_connexion) VALUES (?, ?, ?, ?, ?)";
		sendVoidSQLRequest(sql, nom, prenom, qualification, last_ip, lastConnexion);
	}

	public ResultArray getAllUsers() {
		String sql = "SELECT * FROM structure ORDER BY last_connexion DESC";
		return sendObjectSQLRequest(sql);
	}

	public boolean existsUser(String nom, String prenom) {
		return getUserID(nom, prenom) >= 0;
	}

	public ResultArray getUserInfos(int persID) {
		String sql = "SELECT * FROM structure WHERE id = ?";
		return sendObjectSQLRequest(sql, persID);
	}

	public void updateIP(int persID, String newIP) {
		String sql = "UPDATE structure SET last_ip = ? WHERE id = ?";
		sendVoidSQLRequest(sql, newIP, persID);
	}

	public void resetIP(String ip) {
		String sql = "UPDATE structure SET last_ip = NULL WHERE last_ip = ?";
		sendVoidSQLRequest(sql, ip);
	}

	public int getUserIDbyIP(String ip) {
		String sql = "SELECT id FROM structure WHERE last_ip = ? LIMIT 1";
		ResultArray res = sendObjectSQLRequest(sql, ip);
		if (res.size == 0) {
			return -1;
		} else {
			return (int) res.get(0, "id");
		}
	}

	public void updateLastConnexion(int persID, long lastConnexion) {
		String sql = "UPDATE structure SET last_connexion = ? WHERE id =  ?";
		sendVoidSQLRequest(sql, lastConnexion, persID);
	}

	// TABLE PROJETS

	public ResultArray getMyProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE id IN "
				+ "(SELECT DISTINCT id_projet FROM travaux WHERE id_personne = ?) ORDER BY "
				+ "(SELECT date FROM travaux WHERE id_projet = projets.id AND id_personne = ? ORDER BY date DESC LIMIT 1) DESC";
		return sendObjectSQLRequest(sql, persID, persID);
	}

	public ResultArray getMyOngoingProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE date_fin IS NULL AND id IN ( "
				+ "SELECT id_projet FROM travaux WHERE id IN ("
				+ "SELECT DISTINCT id FROM travaux AS t WHERE id_personne = ? AND ("
				+ "SELECT mission FROM travaux WHERE id_personne = ? AND id_projet = t.id_projet AND (mission IN ('_NEW_', '_END_', '_QUIT_')) ORDER BY date DESC LIMIT 1) = '_NEW_'"
				+ "))"
				+ "ORDER BY (SELECT date FROM travaux WHERE id_projet = projets.id AND id_personne = ? ORDER BY date DESC LIMIT 1) DESC";
		return sendObjectSQLRequest(sql, persID, persID, persID);
	}

	public ResultArray getMyWaitingProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE date_fin IS NULL AND id IN ( "
				+ "SELECT id_projet FROM travaux WHERE id IN ("
				+ "SELECT DISTINCT id FROM travaux AS t WHERE id_personne = ? AND ("
				+ "SELECT mission FROM travaux WHERE id_personne = ? AND id_projet = t.id_projet AND (mission IN ('_NEW_', '_END_')) ORDER BY date DESC LIMIT 1) = '_END_'"
				+ ")) "
				+ "ORDER BY (SELECT date FROM travaux WHERE id_projet = projets.id AND id_personne = ? ORDER BY date DESC LIMIT 1) DESC";

		return sendObjectSQLRequest(sql, persID, persID, persID);
	}

	public ResultArray getMyEndedProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE date_fin IS NOT NULL AND id IN ( "
				+ "SELECT id_projet FROM travaux WHERE id IN ("
				+ "SELECT DISTINCT id FROM travaux AS t WHERE id_personne = ? AND ("
				+ "SELECT mission FROM travaux WHERE id_personne = ? AND id_projet = t.id_projet AND (mission IN ('_NEW_', '_END_')) ORDER BY date DESC LIMIT 1) = '_END_'"
				+ ")) "
				+ "ORDER BY (SELECT date FROM travaux WHERE id_projet = projets.id AND id_personne = ? ORDER BY date DESC LIMIT 1) DESC";
		return sendObjectSQLRequest(sql, persID, persID);
	}

	public ResultArray getOtherProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE id NOT IN "
				+ "(SELECT DISTINCT id_projet FROM travaux WHERE id_personne = ?) ORDER BY "
				+ "(SELECT date FROM travaux WHERE id_projet = projets.id ORDER BY date DESC LIMIT 1) DESC";
		return sendObjectSQLRequest(sql, persID);
	}

	public ResultArray getOtherOngoingProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE date_fin IS NULL AND id NOT IN "
				+ "(SELECT DISTINCT id_projet FROM travaux WHERE id_personne = ?) ORDER BY "
				+ "(SELECT date FROM travaux WHERE id_projet = projets.id ORDER BY date DESC LIMIT 1) DESC";
		return sendObjectSQLRequest(sql, persID);
	}

	public ResultArray getOtherEndedProjects(int persID) {
		String sql = "SELECT * FROM projets WHERE date_fin IS NOT NULL AND id NOT IN "
				+ "(SELECT DISTINCT id_projet FROM travaux WHERE id_personne = ?) ORDER BY "
				+ "(SELECT date FROM travaux WHERE id_projet = projets.id ORDER BY date DESC LIMIT 1) DESC";
		return sendObjectSQLRequest(sql, persID);
	}

	public void addProject(String nomProjet, String clientProjet) {
		String sql = "INSERT INTO projets (nom_projet, client_projet, date_debut) VALUES (?, ?, ?)";
		Date d = new Date();
		long date = d.getTime() / 1000;
		sendVoidSQLRequest(sql, nomProjet, clientProjet, date);
	}

	public int getProjectID(String nomProjet, String clientProjet) {
		String sql = "SELECT id FROM projets WHERE nom_projet = ? AND client_projet = ?";
		ResultArray res = sendObjectSQLRequest(sql, nomProjet, clientProjet);
		if (res.size == 0) {
			return -1;
		} else {
			return (int) res.get(0, "id");
		}
	}

	public boolean issetProject(String nomProjet, String clientProjet) {
		return getProjectID(nomProjet, clientProjet) >= 0;
	}

	public ResultArray getProject(int projectID) {
		String sql = "SELECT * FROM projets WHERE id = ?";
		return sendObjectSQLRequest(sql, projectID);
	}

	public void deleteProject(int projectID) {
		String sql = "DELETE FROM projets WHERE id = ?";
		sendVoidSQLRequest(sql, projectID);
	}

	public int getNbWaitingParticipants(int projectID) {
		String sql = "SELECT COUNT(DISTINCT id_personne) AS count FROM travaux WHERE id_projet = ? AND mission = '_END_';";
		return (int) sendObjectSQLRequest(sql, projectID).get(0, "count");
	}

	public void updateProjectEndDate(int projectID, Long date) {
		String sql = "UPDATE projets SET date_fin = ? WHERE id = ?";
		sendVoidSQLRequest(sql, date, projectID);
	}

	// TABLE TRAVAUX

	public ResultArray getLastWork(int projectID, int persID) {
		String sql;
		if (persID >= 0) {
			sql = "SELECT * FROM travaux WHERE id_projet = ? AND id_personne = ? ORDER BY date DESC LIMIT 1";
			return sendObjectSQLRequest(sql, projectID, persID);
		} else {
			sql = "SELECT * FROM travaux WHERE id_projet = ? ORDER BY date DESC LIMIT 1";
			return sendObjectSQLRequest(sql, projectID);
		}
	}

	public ResultArray getLastWork(int projectID) {
		return getLastWork(projectID, -1);
	}

	public long getTimeWork(int projectID, int persID) {
		String sql;
		if (persID >= 0) {
			sql = "SELECT SUM(temps_travail) AS sum FROM travaux WHERE id_projet = ? AND id_personne = ?";
			return ((Number) sendObjectSQLRequest(sql, projectID, persID).get(0, "sum")).longValue();
		} else {
			sql = "SELECT SUM(temps_travail) AS sum FROM travaux WHERE id_projet = ?";
			return ((Number) sendObjectSQLRequest(sql, projectID).get(0, "sum")).longValue();
		}
	}

	public long getTimeWork(int projectID) {
		return getTimeWork(projectID, -1);
	}

	public ResultArray getParticipants(int projectID) {
		String sql = "SELECT id, nom, prenom FROM structure WHERE id IN "
				+ "(SELECT DISTINCT id_personne FROM travaux WHERE id_projet = ?)";
		return sendObjectSQLRequest(sql, projectID);
	}

	public void addWorkSessions(int projectID, int persID, long date, int workTime, String mission) {
		String sql = "INSERT INTO travaux (id_projet, id_personne, date, temps_travail, mission) VALUES (?, ?, ?, ?, ?)";
		sendVoidSQLRequest(sql, projectID, persID, date, workTime, mission);
	}

	public void addWorkSessions(int projectID, int persID, int workTime, String mission) {
		String sql = "INSERT INTO travaux (id_projet, id_personne, date, temps_travail, mission) VALUES (?, ?, ?, ?, ?)";
		Date d = new Date();
		long date = d.getTime() / 1000;
		sendVoidSQLRequest(sql, projectID, persID, date, workTime, mission);
	}

	public ArrayList<String> getMissionsArrayList(int projectID) {
		String sql = "SELECT DISTINCT mission FROM travaux WHERE id_projet = ? ORDER BY date DESC";
		ResultArray res = sendObjectSQLRequest(sql, projectID);
		ArrayList<String> missions = new ArrayList<>();
		for (int i = 0; i < res.size; i++) {
			String m = (String) res.get(i, "mission");
			if (!m.equals("_NEW_") && !m.equals("_END_")) {
				missions.add(m);
			}
		}
		return missions;
	}

	public ResultArray getMySessions(int projectID, int persID) {
		String sql = "SELECT * FROM travaux WHERE id_projet = ? AND id_personne = ? ORDER BY date DESC";
		return sendObjectSQLRequest(sql, projectID, persID);
	}

	public void deleteSession(int sessionID) {
		String sql = "DELETE FROM travaux WHERE id = ?";
		sendVoidSQLRequest(sql, sessionID);
	}

	public ResultArray getSession(int sessionID) {
		String sql = "SELECT * FROM travaux WHERE id = ?";
		return sendObjectSQLRequest(sql, sessionID);
	}

	public void updateSession(int sessionID, long date, long workTime, String mission) {
		String sql = "UPDATE travaux SET date = ?, temps_travail = ?, mission = ? WHERE id = ?";
		sendVoidSQLRequest(sql, date, workTime, mission, sessionID);
	}

	public ResultArray getMyStatsGroupByMission(int projectID, int persID) {
		String sql = "SELECT mission, SUM(temps_travail) AS temps_travail_total, MIN(date) AS date_debut, MAX(date) AS date_fin "
				+ "FROM travaux "
				+ "WHERE id_projet = ? AND id_personne = ? AND mission NOT IN ('_NEW_', '_END_') "
				+ "GROUP BY mission "
				+ "ORDER BY date_debut";
		return sendObjectSQLRequest(sql, projectID, persID);
	}

	public ResultArray getMyMonthStatsGroupByMission(int projectID, int persID, long begMonth, long endMonth) {
		String sql = "SELECT mission, SUM(temps_travail) AS temps_travail_total, MIN(date) AS date_debut "
				+ "FROM travaux "
				+ "WHERE id_projet = ? AND id_personne = ? AND mission NOT IN ('_NEW_', '_END_') AND date > ? AND date < ? "
				+ "GROUP BY mission "
				+ "ORDER BY date_debut";
		return sendObjectSQLRequest(sql, projectID, persID, begMonth, endMonth);
	}

	public ResultArray getTotalMonthStatsGroupByMission(int projectID, long begMonth, long endMonth) {
		String sql = "SELECT mission, SUM(temps_travail) AS temps_travail_total, MIN(date) AS date_debut "
				+ "FROM travaux "
				+ "WHERE id_projet = ? AND mission NOT IN ('_NEW_', '_END_') AND date > ? AND date < ? "
				+ "GROUP BY mission "
				+ "ORDER BY date_debut";
		return sendObjectSQLRequest(sql, projectID, begMonth, endMonth);
	}

	public ResultArray getTotalStatsGroupByMission(int projectID) {
		String sql = "SELECT mission, SUM(temps_travail) AS temps_travail_total, MIN(date) AS date_debut, MAX(date) AS date_fin "
				+ "FROM travaux "
				+ "WHERE id_projet = ? AND mission NOT IN ('_NEW_', '_END_') "
				+ "GROUP BY mission "
				+ "ORDER BY date_debut";
		return sendObjectSQLRequest(sql, projectID);
	}

	public void deleteMySessions(int projectID, int persID) {
		String sql = "DELETE FROM travaux WHERE id_projet = ? AND id_personne = ?";
		sendVoidSQLRequest(sql, projectID, persID);
	}

	public void deleteMySessions(int projectID, int persID, String mission) {
		String sql = "DELETE FROM travaux WHERE id_projet = ? AND id_personne = ? AND mission = ?";
		sendVoidSQLRequest(sql, projectID, persID, mission);
	}

}