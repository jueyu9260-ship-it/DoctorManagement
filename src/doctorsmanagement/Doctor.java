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

    public boolean isDateWithinDutyDay(String dutySchedule, LocalDate date) {
        String[] parts = dutySchedule.split(" ");
        if (parts.length < 1) {
            return false;
        }
        String daysPart = parts[0];

        Set<Integer> dutyDays = new HashSet<>();
        if (daysPart.contains("-")) {
            String[] range = daysPart.split("-");
            int start = dayOfWeekToInt(range[0]);
            int end = dayOfWeekToInt(range[1]);
            for (int i = start; i <= end; i++) {
                dutyDays.add(i);
            }
        } else {
            String[] days = daysPart.split(",");
            for (String d : days) {
                dutyDays.add(dayOfWeekToInt(d));
            }
        }
        int apptDay = date.getDayOfWeek().getValue();
        return dutyDays.contains(apptDay);
    }

    public boolean isTimeWithinDutyTime(String dutySchedule, LocalDate date, LocalTime time) {
        String[] parts = dutySchedule.split(" ");
        if (parts.length < 2) {
            return false;
        }
        String timePart = parts[1];
        String[] timeRange = timePart.split("-");
        if (timeRange.length != 2) {
            return false;
        }
        LocalTime startTime = parseTimeWithMin(timeRange[0]);
        LocalTime endTime = parseTimeWithMin(timeRange[1]);
        if (startTime == null || endTime == null) {
            return false;
        }

        if (!time.isBefore(endTime.minusMinutes(20))) {
            return false;
        }
        return !time.isBefore(startTime) && time.isBefore(endTime);
    }

    public LocalTime parseTimeWithMin(String timeStr) {
        try {
            timeStr = timeStr.replace(" ", "");
            int hour = 0, minute = 0;
            if (timeStr.endsWith("am") || timeStr.endsWith("pm")) {
                String base = timeStr.substring(0, timeStr.length() - 2);
                String[] parts = base.split(":");
                hour = Integer.parseInt(parts[0]);
                if (parts.length == 2) {
                    minute = Integer.parseInt(parts[1]);
                }
                if (timeStr.endsWith("am")) {
                    if (hour == 12) {
                        hour = 0;
                    }
                } else {
                    if (hour != 12) {
                        hour += 12;
                    }
                }
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    return null;
                }
                return LocalTime.of(hour, minute);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public int dayOfWeekToInt(String day) {
        switch (day.trim()) {
            case "Mon":
                return 1;
            case "Tue":
                return 2;
            case "Wed":
                return 3;
            case "Thu":
                return 4;
            case "Fri":
                return 5;
            case "Sat":
                return 6;
            case "Sun":
                return 7;
            default:
                return -1;
        }
    }

    public AvailabilityStatus isAvailableAt(LocalDate date, LocalTime time) {
        // Check leave
        for (Leave leave : leaves) {
            if ((date.isEqual(leave.getStartDate()) || date.isAfter(leave.getStartDate()))
                    && (date.isEqual(leave.getEndDate()) || date.isBefore(leave.getEndDate()))) {
                return new AvailabilityStatus(false, "Doctor is on leave from " + leave.getStartDate() + " to " + leave.getEndDate());
            }
        }

        // Check duty day
        if (!isDateWithinDutyDay(dutySchedule, date)) {
            return new AvailabilityStatus(false, "Not within duty schedule day");
        }

        // Check duty time
        if (!isTimeWithinDutyTime(dutySchedule, date, time)) {
            return new AvailabilityStatus(false, "Not within duty schedule time");
        }

        // Check shift
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

        // Check appointment conflict (0-30 min)
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
