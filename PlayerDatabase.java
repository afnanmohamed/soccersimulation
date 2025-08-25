package soccer.util;


import javax.swing.*;
import java.util.*;
import soccer.Player;
import soccer.Team;

public class PlayerDatabase {
    private PlayerDatabase() {}

    public static java.util.List<Team> loadSampleTeams() throws PlayerDatabaseException {
        try {
            java.util.List<Team> teams = new ArrayList<>();

            Team city = new Team("Manchester City");
            city.setLogo(loadLogo("images/man_city.png"));
            city.addPlayer(new Player("Erling Haaland", "Manchester City"));
            city.addPlayer(new Player("Kevin De Bruyne", "Manchester City"));

            Team liverpool = new Team("Liverpool");
            liverpool.setLogo(loadLogo("images/liverpool.png"));
            liverpool.addPlayer(new Player("Mohamed Salah", "Liverpool"));
            liverpool.addPlayer(new Player("Darwin Núñez", "Liverpool"));

            Team real = new Team("Real Madrid");
            real.setLogo(loadLogo("images/real_madrid.png"));
            real.addPlayer(new Player("Kylian Mbappé", "Real Madrid"));
            real.addPlayer(new Player("Jude Bellingham", "Real Madrid"));

            Team arsenal = new Team("Arsenal");
            arsenal.setLogo(loadLogo("images/arsenal.png"));
            arsenal.addPlayer(new Player("Bukayo Saka", "Arsenal"));
            arsenal.addPlayer(new Player("Martin Ødegaard", "Arsenal"));

            teams.add(city);
            teams.add(liverpool);
            teams.add(real);
            teams.add(arsenal);

            return teams;
        } catch (Exception e) {
            throw new PlayerDatabaseException("Failed to load sample data", e);
        }
    }

    private static ImageIcon loadLogo(String resourcePath) {
        java.net.URL url = PlayerDatabase.class.getClassLoader().getResource(resourcePath);
        if (url == null) return null;
        return new ImageIcon(url);
    }
}

