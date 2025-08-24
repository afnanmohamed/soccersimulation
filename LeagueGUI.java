
package soccer;

import soccer.util.PlayerDatabase;
import soccer.util.PlayerDatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class LeagueGUI extends JFrame {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final DefaultListModel<String> playerListModel = new DefaultListModel<>();
    private final JList<String> playerList = new JList<>(playerListModel);
    private final JComboBox<String> themeBox;
    private final JComboBox<Team> homeBox = new JComboBox<>();
    private final JComboBox<Team> awayBox = new JComboBox<>();
    private final JSpinner homeGoals = new JSpinner(new SpinnerNumberModel(0,0,99,1));
    private final JSpinner awayGoals = new JSpinner(new SpinnerNumberModel(0,0,99,1));
    private final JTextField teamNameField = new JTextField();
    private final JTextField playerNameField = new JTextField();
    private final JTextField playerClubField = new JTextField();
    private final JButton addTeamBtn = new JButton("Add Team");
    private final JButton addPlayerBtn = new JButton("Add Player");
    private final JButton recordMatchBtn = new JButton("Record Match");
    private final JButton simulateBtn = new JButton("Simulate Random Match");
    private final JButton loadSampleBtn = new JButton("Load Sample Data");
    private final JLabel logoLabel = new JLabel("", SwingConstants.CENTER);

    private final java.util.List<Team> teams = new ArrayList<>();

    public LeagueGUI() {
        super("Soccer League Simulator — Enhanced");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);

        String[] cols = {"Team","P","W","D","L","GF","GA","GD","Pts"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);

        // Sidebar
        JPanel side = new JPanel(new BorderLayout(8,8));
        side.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        // Top controls: Theme + Sample
        JPanel topControls = new JPanel(new GridLayout(1,2,8,8));
        themeBox = new JComboBox<>(new String[]{"Ocean Blue","Emerald","Sunset","Dark"});
        topControls.add(labeled(themeBox, "Theme"));
        topControls.add(loadSampleBtn);
        side.add(topControls, BorderLayout.NORTH);

        // Middle: Logo + Player list
        JPanel center = new JPanel(new BorderLayout(8,8));
        logoLabel.setPreferredSize(new Dimension(220,220));
        logoLabel.setBorder(BorderFactory.createTitledBorder("Team Logo"));
        center.add(logoLabel, BorderLayout.NORTH);

        playerList.setBorder(BorderFactory.createTitledBorder("Players (Top Scorers)"));
        center.add(new JScrollPane(playerList), BorderLayout.CENTER);
        side.add(center, BorderLayout.CENTER);

        // Bottom: Add team/player
        JPanel bottom = new JPanel(new GridLayout(0,1,8,8));
        bottom.add(labeled(teamNameField, "New Team Name"));
        JButton pickLogoBtn = new JButton("Pick Team Logo");
        bottom.add(pickLogoBtn);
        bottom.add(addTeamBtn);

        bottom.add(labeled(playerNameField, "Player Name"));
        bottom.add(labeled(playerClubField, "Club"));
        bottom.add(addPlayerBtn);
        side.add(bottom, BorderLayout.SOUTH);

        // Main center: Table + Match panel
        JPanel main = new JPanel(new BorderLayout(8,8));
        main.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel matchPanel = new JPanel(new GridLayout(2,1,8,8));
        JPanel row1 = new JPanel(new GridLayout(1,0,8,8));
        row1.add(labeled(homeBox, "Home"));
        row1.add(labeled(homeGoals, "HG"));
        row1.add(labeled(awayBox, "Away"));
        row1.add(labeled(awayGoals, "AG"));
        row1.add(recordMatchBtn);
        matchPanel.add(row1);

        JPanel row2 = new JPanel(new GridLayout(1,0,8,8));
        row2.add(simulateBtn);
        matchPanel.add(row2);
        matchPanel.setBorder(BorderFactory.createTitledBorder("Match Center"));

        main.add(matchPanel, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, side, main);
        split.setDividerLocation(300);
        setContentPane(split);

        // Actions
        themeBox.addActionListener(e -> applyTheme((String) themeBox.getSelectedItem()));
        loadSampleBtn.addActionListener(e -> {
            try {
                teams.clear();
                teams.addAll(PlayerDatabase.loadSampleTeams());
                refreshBoxes();
                refreshTable();
                refreshPlayersList();
            } catch (PlayerDatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addTeamBtn.addActionListener(e -> {
            String n = teamNameField.getText().trim();
            if (n.isEmpty()) { tip("Enter a team name."); return; }
            Team t = new Team(n);
            teams.add(t);
            teamNameField.setText("");
            refreshBoxes();
            refreshTable();
        });

        addPlayerBtn.addActionListener(e -> {
            Team selected = (Team) JOptionPane.showInputDialog(this, "Add player to which team?",
                    "Select Team", JOptionPane.QUESTION_MESSAGE, null, teams.toArray(), null);
            if (selected == null) return;
            String pn = playerNameField.getText().trim();
            String pc = playerClubField.getText().trim();
            if (pn.isEmpty() || pc.isEmpty()) { tip("Enter player name and club."); return; }
            selected.addPlayer(new Player(pn, pc));
            playerNameField.setText("");
            playerClubField.setText("");
            refreshPlayersList();
        });

        recordMatchBtn.addActionListener(e -> {
            Team h = (Team) homeBox.getSelectedItem();
            Team a = (Team) awayBox.getSelectedItem();
            if (h == null || a == null || h == a) { tip("Select two different teams."); return; }
            int hg = (int) homeGoals.getValue();
            int ag = (int) awayGoals.getValue();
            new Match(h, a).setScore(hg, ag);
            // Randomly credit goals to players on each team (if any)
            creditGoals(h, hg);
            creditGoals(a, ag);
            refreshTable();
            refreshPlayersList();
            showLogo(h);
        });

        simulateBtn.addActionListener(e -> simulateRandom());

        pickLogoBtn.addActionListener(e -> {
            Team target = (Team) JOptionPane.showInputDialog(this, "Pick logo for which team?",
                    "Team Logo", JOptionPane.QUESTION_MESSAGE, null, teams.toArray(), null);
            if (target == null) return;
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ImageIcon icon = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
                target.setLogo(icon);
                showLogo(target);
            }
        });

        // Default theme
        applyTheme("Ocean Blue");
    }

    private void simulateRandom() {
        if (teams.size() < 2) { tip("Add at least two teams or load sample data."); return; }
        Random r = new Random();
        Team h = teams.get(r.nextInt(teams.size()));
        Team a = teams.get(r.nextInt(teams.size()));
        while (a == h) a = teams.get(r.nextInt(teams.size()));
        int hg = r.nextInt(5);
        int ag = r.nextInt(5);
        new Match(h, a).setScore(hg, ag);
        creditGoals(h, hg);
        creditGoals(a, ag);
        refreshTable();
        refreshPlayersList();
        showLogo(h);
        tip("Simulated: " + h.getName() + " " + hg + " - " + ag + " " + a.getName());
    }

    private void creditGoals(Team t, int goals) {
        if (t.getPlayers().isEmpty()) return;
        Random r = new Random();
        for (int i = 0; i < goals; i++) {
            Player p = t.getPlayers().get(r.nextInt(t.getPlayers().size()));
            p.addGoals(1);
        }
    }

    private void showLogo(Team t) {
        if (t == null || t.getLogo() == null) { logoLabel.setIcon(null); return; }
        Image img = t.getLogo().getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(img));
    }

    private void refreshBoxes() {
        homeBox.removeAllItems();
        awayBox.removeAllItems();
        for (Team t : teams) { homeBox.addItem(t); awayBox.addItem(t); }
        homeBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Team) setText(((Team) value).getName());
                return this;
            }
        });
        awayBox.setRenderer(homeBox.getRenderer());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        java.util.List<Team> sorted = teams.stream()
                .sorted(Comparator.comparingInt(Team::getPoints).reversed()
                        .thenComparingInt(Team::getGoalDiff).reversed()
                        .thenComparingInt(Team::getGoalsFor).reversed()
                        .thenComparing(Team::getName))
                .collect(Collectors.toList());

        for (Team t : sorted) {
            tableModel.addRow(new Object[]{
                    t.getName(), t.getPlayed(), t.getWon(), t.getDrawn(), t.getLost(),
                    t.getGoalsFor(), t.getGoalsAgainst(), t.getGoalDiff(), t.getPoints()
            });
        }
    }

    private void refreshPlayersList() {
        java.util.List<Player> all = new ArrayList<>();
        for (Team t : teams) all.addAll(t.getPlayers());
        all.sort(Comparator.comparingInt(Player::getGoals).reversed());
        playerListModel.clear();
        for (int i=0; i<Math.min(15, all.size()); i++) {
            Player p = all.get(i);
            playerListModel.addElement((i+1) + ". " + p.toString());
        }
    }

    private JPanel labeled(java.awt.Component c, String label) {
        JPanel p = new JPanel(new BorderLayout(4,4));
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private void applyTheme(String name) {
        Color bg, fg, panel;
        switch (name) {
            case "Emerald" -> { bg = new Color(230, 245, 233); fg = new Color(0, 80, 60); panel = new Color(210, 240, 220); }
            case "Sunset" -> { bg = new Color(255, 243, 230); fg = new Color(110, 40, 20); panel = new Color(255, 232, 210); }
            case "Dark" -> { bg = new Color(30, 34, 40); fg = new Color(230,230,230); panel = new Color(50,55,65); }
            default -> { bg = new Color(232, 244, 255); fg = new Color(10, 40, 90); panel = new Color(215, 230, 250); }
        }
        java.util.List<java.awt.Component> all = allComponents(this.getContentPane());
        for (java.awt.Component comp : all) {
            if (comp instanceof JPanel pan) pan.setBackground(panel);
            else comp.setBackground(bg);
            comp.setForeground(fg);
            if (comp instanceof JTable t) {
                t.setBackground(Color.WHITE);
                t.setForeground(Color.BLACK);
            }
        }
        this.getContentPane().setBackground(bg);
        table.getTableHeader().setBackground(panel);
        table.getTableHeader().setForeground(fg);
        repaint();
    }

    private java.util.List<java.awt.Component> allComponents(java.awt.Container c) {
        java.util.List<java.awt.Component> list = new ArrayList<>();
        for (java.awt.Component comp : c.getComponents()) {
            list.add(comp);
            if (comp instanceof java.awt.Container container) list.addAll(allComponents(container));
        }
        return list;
    }

    private void tip(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}

