/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soccer;



public class Player {
    private final String name;
    private final String club;
    private int goals;

    public Player(String name, String club) {
        this.name = name;
        this.club = club;
        this.goals = 0;
    }

    public String getName() { return name; }
    public String getClub() { return club; }
    public int getGoals() { return goals; }
    public void addGoals(int g) { this.goals += Math.max(0, g); }

    @Override
    public String toString() {
        return name + " (" + club + ") - " + goals + " goals";
    }
}
