/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package soccer.event;
 
public class Kickoff extends GameEvent {
 public Kickoff(String desc) { super(desc); }
 @Override public String toString() { return "Kickoff: " +
 getTeamName(); }
 }