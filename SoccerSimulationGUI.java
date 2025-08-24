/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import soccer.*;
import soccer.util.PlayerDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import soccer.util.PlayerDatabaseException;

public class SoccerSimulationGUI extends JFrame {
    private final DefaultTableModel tableModel;
    private final League league = new League();

    public SoccerSimulationGUI() {
        setTitle("Soccer Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Top bar
        JPanel control = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton simulateBtn = new JButton("Simulate Season");
        JButton resetBtn = new JButton("Reset DB");
        control.add(simulateBtn);
        control.add(resetBtn);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Home", "Score", "Away"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        add(control, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Init DB and sample data
        DatabaseHelper.init();
        try {
            for (Team t : PlayerDatabase.loadSampleTeams()) {
                league.addTeam(t);
            }
        } catch (PlayerDatabaseException e) {
            JOptionPane.showMessageDialog(this, "Failed to load teams: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        simulateBtn.addActionListener(this::onSimulate);
        resetBtn.addActionListener(e -> {
            DatabaseHelper.clear();
            tableModel.setRowCount(0);
        });
    }

    private void onSimulate(ActionEvent e) {
        league.scheduleAndPlayRoundRobin();
        tableModel.setRowCount(0);
        for (GameResult gr : league.getResults()) {
            tableModel.addRow(new Object[]{
                    gr.getHome().getName(),
                    gr.getHomeGoals() + " - " + gr.getAwayGoals(),
                    gr.getAway().getName()
            });
            DatabaseHelper.insertMatch(gr.getHome().getName(), gr.getHomeGoals(),
                    gr.getAway().getName(), gr.getAwayGoals());
        }

        // Show standings
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-18s %2s %2s %2s %2s %3s %3s %3s %3s\n",
                "Team","P","W","D","L","GF","GA","GD","Pts"));
        for (Team t : league.getTable()) {
            sb.append(String.format("%-18s %2d %2d %2d %2d %3d %3d %3d %3d\n",
                    t.getName(), t.getPlayed(), t.getWon(), t.getDrawn(), t.getLost(),
                    t.getGoalsFor(), t.getGoalsAgainst(), t.getPoints()));
        }
        JTextArea ta = new JTextArea(sb.toString(), 15, 40);
        ta.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Standings", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SoccerSimulationGUI().setVisible(true));
    }
}

