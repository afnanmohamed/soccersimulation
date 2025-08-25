/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soccer;

import java.util.Random;

public class Game {
    private final Team home;
    private final Team away;
    private final Random random = new Random();

    public Game(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public GameResult play() {
        int homeGoals = random.nextInt(5); // 0..4
        int awayGoals = random.nextInt(5);
        home.applyResult(homeGoals, awayGoals);
        away.applyResult(awayGoals, homeGoals);
        return new GameResult(home, away, homeGoals, awayGoals);
    }
}

