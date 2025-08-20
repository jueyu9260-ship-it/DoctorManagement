/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalDate;

public class Leave extends Doctor {

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private int qtys;

    public Leave(String id, String name, LocalDate startDate, int days, String reason, int qtys) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = startDate.plusDays(days);
        this.reason = reason;
        this.qtys = qtys;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public int getQtys() {
        return qtys;
    }

    @Override
    public String toString() {
        return String.format("| %-20s | %-20s | %-20s|", startDate, endDate, reason);
    }
}
