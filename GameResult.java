/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soccer;

public class GameResult {
    private final Team home;
    private final Team away;
    private final int homeGoals;
    private final int awayGoals;

    public GameResult(Team home, Team away, int homeGoals, int awayGoals) {
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public Team getHome() { return home; }
    public Team getAway() { return away; }
    public int getHomeGoals() { return homeGoals; }
    public int getAwayGoals() { return awayGoals; }

    @Override
    public String toString() {
        return home.getName() + " " + homeGoals + " - " + awayGoals + " " + away.getName();
    }
}
