/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package soccer.event;
 public class Goal extends GameEvent {
 public Goal(String teamName) { super(teamName); }
 @Override public String toString() { return "GOAL! for " +
 getTeamName(); }
 }
