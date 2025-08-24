/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:soccer.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS matches (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "team1 TEXT, score1 INTEGER, team2 TEXT, score2 INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMatch(String team1, int score1, String team2, int score2) {
        String sql = "INSERT INTO matches(team1,score1,team2,score2) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, team1);
            ps.setInt(2, score1);
            ps.setString(3, team2);
            ps.setInt(4, score2);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet selectAllMatches() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        return st.executeQuery("SELECT team1, score1, team2, score2 FROM matches ORDER BY id DESC");
    }

    public static void clear() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM matches");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
