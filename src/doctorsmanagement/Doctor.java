/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Doctor {

    private String id;
    private String name;
    private String dutySchedule;
    private boolean isAvailable;
    private List<Appointment> appointments = new ArrayList<>();
    private List<Shift> shifts = new ArrayList<>();
    private List<Leave> leaves = new ArrayList<>();
    private DoctorsManagement doctorsMgmt;

    public Doctor(String id, String name, String dutySchedule, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.dutySchedule = dutySchedule;
        this.isAvailable = true;

    }

    public Doctor(String id, String name, String dutySchedule) {
        this.id = id;
        this.name = name;
        this.dutySchedule = dutySchedule;
    }

    public Doctor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDutySchedule() {
        if (dutySchedule == null) {
            dutySchedule = "Not Assigned";
        }
        return dutySchedule;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDutySchedule(String dutySchedule) {
        this.dutySchedule = dutySchedule;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public AvailabilityStatus isAvailableAt(LocalDate date, LocalTime time) {
        for (Leave leave : leaves) {
            if ((date.isEqual(leave.getStartDate()) || date.isAfter(leave.getStartDate()))
                    && (date.isEqual(leave.getEndDate()) || date.isBefore(leave.getEndDate()))) {
                return new AvailabilityStatus(false, "Doctor is on leave from " + leave.getStartDate() + " to " + leave.getEndDate());
            }
        }

        if (!doctorsMgmt.isDateWithinDutyDay(dutySchedule, date)) {
            return new AvailabilityStatus(false, "Not within duty schedule day");
        }

        if (!doctorsMgmt.isTimeWithinDutyTime(dutySchedule, date, time)) {
            return new AvailabilityStatus(false, "Not within duty schedule time");
        }

        boolean withinShift = false;
        for (Shift s : shifts) {
            for (TimeRange tr : s.getShiftRanges()) {
                if (!time.isBefore(tr.getStart()) && time.isBefore(tr.getEnd())) {
                    withinShift = true;
                    break;
                }
            }
            if (withinShift) {
                break;
            }
        }
        if (!withinShift) {
            return new AvailabilityStatus(false, "Not within shift time");
        }

        for (Appointment appt : appointments) {
            if (appt.getDate().equals(date)) {
                long mins = Math.abs(java.time.Duration.between(appt.getTime(), time).toMinutes());
                if (mins < 30) {
                    return new AvailabilityStatus(false, "Appointment conflict within 30 minutes at " + appt.getTime());
                }
            }
        }

        return new AvailabilityStatus(true, "Available");
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public List<Leave> getLeave() {
        return leaves;
    }

    public void addLeave(Leave leave) {
        leaves.add(leave);
    }

    public boolean isOnLeave(LocalDate date) {
        for (Leave leave : leaves) {
            if ((date.isEqual(leave.getStartDate()) || date.isAfter(leave.getStartDate()))
                    && (date.isEqual(leave.getEndDate()) || date.isBefore(leave.getEndDate()))) {
                return true;
            }
        }
        return false;
    }
}
