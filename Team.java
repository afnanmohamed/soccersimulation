/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soccer;


import javax.swing.*;
import java.util.*;

public class Team {
    private String name;
    private ImageIcon logo;
    private final java.util.List<Player> players = new ArrayList<>();
    private int played, won, drawn, lost, goalsFor, goalsAgainst, points;

    public Team(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ImageIcon getLogo() { return logo; }
    public void setLogo(ImageIcon logo) { this.logo = logo; }

    public java.util.List<Player> getPlayers() { return players; }

    public int getPlayed() { return played; }
    public int getWon() { return won; }
    public int getDrawn() { return drawn; }
    public int getLost() { return lost; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
    public int getGoalDiff() { return goalsFor - goalsAgainst; }
    public int getPoints() { return points; }

    public void addPlayer(Player p) { players.add(p); }

    public void recordMatch(int gf, int ga) {
        played++;
        goalsFor += gf;
        goalsAgainst += ga;
        if (gf > ga) { won++; points += 3; }
        else if (gf == ga) { drawn++; points += 1; }
        else { lost++; }
    }

    void applyResult(int awayGoals, int homeGoals) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


