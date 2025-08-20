/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment extends Doctor {

    private String patientName;
    private int qty = 0;
    private LocalDate date;
    private LocalTime time;

    public Appointment(String id, String name, String patientName, int qty, LocalDate date, LocalTime time) {
        super(id, name);
        this.patientName = patientName;
        this.qty = qty;
        this.date = date;
        this.time = time;
    }

    
    
    public String getPatientName() {
        return patientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return String.format("| %-20s | %-18s | %-20s|", patientName, date, time);
    }

}
