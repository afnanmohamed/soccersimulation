/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soccer;

import java.util.ArrayList;
import java.util.List;

public class League {
    private final List<Team> teams = new ArrayList<>();
    private final List<GameResult> results = new ArrayList<>();

    public void addTeam(Team t) { teams.add(t); }
    public List<Team> getTeams() { return teams; }
    public List<GameResult> getResults() { return results; }

    public void scheduleAndPlayRoundRobin() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Game g1 = new Game(teams.get(i), teams.get(j));
                results.add(g1.play());
                Game g2 = new Game(teams.get(j), teams.get(i));
                results.add(g2.play());
            }
        }
    }

    public List<Team> getTable() {
        List<Team> copy = new ArrayList<>(teams);
    
        return copy;
    }

}
