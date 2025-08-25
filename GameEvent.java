/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package soccer.event;
 public abstract class GameEvent {
 private final String teamName;
 public GameEvent(String teamName) { this.teamName = teamName; }
 public String getTeamName() { return teamName; }
 @Override public String toString() { return getClass().getSimpleName() +
 "(" + teamName + ")"; }
 }

