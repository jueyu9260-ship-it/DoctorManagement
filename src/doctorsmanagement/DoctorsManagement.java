/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DoctorsManagement {

    private Doctors doct;
    private DoctorsManagementUI doctorsUI;

    public DoctorsManagement(Doctors doct) {
        this.doct = doct;
        this.doctorsUI = new DoctorsManagementUI(this);
    }

    public String generateNextDoctorId() {
        int maxNum = 0;
        List<Doctor> allDoctors = getAllDoctors();
        for (Doctor doc : allDoctors) {
            String docId = doc.getId();
            if (docId != null && docId.startsWith("D")) {
                try {
                    int num = Integer.parseInt(docId.substring(1));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (NumberFormatException e) {

                }
            }
        }
        int nextNum = maxNum + 1;
        return String.format("D%03d", nextNum);
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

    public void runDoctorsManagement() {
        doctorsUI.runDoctorsManagement();
    }

    public boolean addNewDoctor(Doctor newDoctor) {
        if (newDoctor == null) {
            return false;
        }

        if (doct.findDoctorById(newDoctor.getId()) != null) {
            return false;
        }

        for (Doctor existingDoctor : doct.getAllDoctors()) {
            if (existingDoctor.getName().trim().equalsIgnoreCase(newDoctor.getName().trim())
                    && existingDoctor.getDutySchedule().trim().equalsIgnoreCase(newDoctor.getDutySchedule().trim())) {
                return false;
            }
        }

        doct.addDoctor(newDoctor);
        return true;
    }

    public List<Doctor> getAllDoctors() {
        return doct.getAllDoctors();
    }

    public boolean assignDuty(String doctorId, String dutySchedule) {
        Doctor doc = doct.findDoctorById(doctorId);
        if (doc == null) {
            return false;
        }
        doc.setDutySchedule(dutySchedule);
        doct.updateDoctor(doc);
        return true;
    }

    public List<String> getDutySchedules() {
        return doct.getAllDoctors().stream().map(Doctor::getDutySchedule).toList();
    }

    public boolean setDoctorAvailability(String doctorId, boolean isAvailable) {
        Doctor doc = doct.findDoctorById(doctorId);
        if (doc == null) {
            return false;
        }
        doc.setAvailable(isAvailable);
        doct.updateDoctor(doc);
        return true;
    }

    public boolean editDoctor(String doctorId, String dutySchedule) {
        Doctor doc = findDoctorById(doctorId);
        if (doc == null) {
            return false;
        }
        if (dutySchedule != null && !dutySchedule.isEmpty()) {
            doc.setDutySchedule(dutySchedule);
        }
        doct.updateDoctor(doc);
        return true;
    }

    public boolean removeDoctor(String doctorId) {
        Doctor doc = doct.findDoctorById(doctorId);
        if (doc == null) {
            return false;
        }
        doct.removeDoctor(doc);
        return true;
    }

    public Doctor findDoctorById(String id) {
        return doct.findDoctorById(id);
    }

    public static void main(String[] args) {
        Doctors repo = new DoctorRepository();
        DoctorsManagement doctorsMgmt = new DoctorsManagement(repo);
        doctorsMgmt.runDoctorsManagement();
    }
}
