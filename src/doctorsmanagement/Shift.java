/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalTime;
import java.util.List;

public class Shift extends Doctor {

    private List<TimeRange> shiftRanges;

    public Shift(String id, String name, String specialty, List<TimeRange> shiftRanges) {
        super(id, name, specialty);
        this.shiftRanges = shiftRanges;
    }

    public List<TimeRange> getShiftRanges() {
        return shiftRanges;
    }

    public void setShiftRanges(List<TimeRange> shiftRanges) {
        this.shiftRanges = shiftRanges;
    }

    @Override
    public String toString() {
        return shiftRanges.toString();
    }

}
