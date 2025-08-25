/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package soccer.play;
 public class DisplayString implements IDisplayDataItem {
 
private final int id;
 private final String value;
 public DisplayString(int id, String value) {
 this.id = id; this.value = value;
 }
 @Override public boolean isDetailAvailable() { return value != null && !
 value.isEmpty(); }
 @Override public String getDisplayDetail() { return value; }
 @Override public int getID() { return id; }
 @Override public String getDetailType() { return "STRING"; }
 @Override public String toString() { return value; }
 }