/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soccer;

/**
 *
 * @author hp
 */

public class Match {
    private final Team home;
    private final Team away;
    private int homeGoals;
    private int awayGoals;

    public Match(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public Team getHome() { return home; }
    public Team getAway() { return away; }
    public int getHomeGoals() { return homeGoals; }
    public int getAwayGoals() { return awayGoals; }

    public void setScore(int hg, int ag) {
        this.homeGoals = Math.max(0, hg);
        this.awayGoals = Math.max(0, ag);
        home.recordMatch(homeGoals, awayGoals);
        away.recordMatch(awayGoals, homeGoals);
    }
}
