/*
 * Click nbfs:nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DoctorsManagementUI {

    private DoctorsManagement doctorsMgmt;
    private DoctorsManagementMessageUI messageUI;
    private Scanner sc = new Scanner(System.in);

    public DoctorsManagementUI(DoctorsManagement doctorsMgmt) {
        this.doctorsMgmt = doctorsMgmt;
    }

    public void runDoctorsManagement() {
        Doctor doctor1 = new Doctor("D001", "Sam", "Mon-Fri 8am-9pm", true);

        Doctor doctor2 = new Doctor("D002", "Lim", "Mon,Wed,Fri 10am-5pm", true);

        Doctor doctor3 = new Doctor("D003", "Mary", "Tue-Thu 9am-3pm", true);

        boolean success1 = doctorsMgmt.addNewDoctor(doctor1);
        boolean success2 = doctorsMgmt.addNewDoctor(doctor2);
        boolean success3 = doctorsMgmt.addNewDoctor(doctor3);

        int choice = -1;
        do {
            System.out.println("\nDoctor Management Menu:");
            System.out.println("1. Add Doctor");
            System.out.println("2. Assign Duty Schedule");
            System.out.println("3. Search Doctor");
            System.out.println("4. Doctor Management List");
            System.out.println("5. Track Availability");
            System.out.println("6. Summary Reports");
            System.out.println("7. Edit/Update");
            System.out.println("8. Remove");
            System.out.println("9. Exit");
            System.out.print("Enter your choice (1-9): ");
            while (!sc.hasNextInt()) {
                messageUI.displayInvalidChoiceMessage();
                sc.next();
                System.out.print("Enter your choice (1-9): ");
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addDoctorUI();
                    break;
                case 2:
                    assignDuty();
                    break;
                case 3:
                    searchDoctor();
                    break;
                case 4:
                    doctorManagementList();
                    break;
                case 5:
                    trackAvailability();
                    break;
                case 6:
                    summaryReports();
                    break;
                case 7:
                    doctorManagementEdit();
                    break;
                case 8:
                    doctorManagementRemove();
                    break;
                case 9:
                    messageUI.displayExitMessage();
                    break;
                default:
                    messageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 9);
    }

    private void addDoctorUI() {
        String id = doctorsMgmt.generateNextDoctorId();

        System.out.println("Assigned Doctor ID: " + id);
        String name;
        while (true) {
            System.out.print("Enter Doctor Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                messageUI.displayDoctorNameCannotEmpty();
            } else {
                break;
            }
        }
        Doctor newDoctor = new Doctor(id, name);
        boolean success = doctorsMgmt.addNewDoctor(newDoctor);
        if (success) {
            messageUI.displayAddDoctorSuccessfully();
        } else {
            messageUI.displayInvalidAddDoctor();
        }
    }

    private void searchDoctor() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate today = dateTime.toLocalDate();
        LocalTime now = dateTime.toLocalTime();

        while (true) {
            System.out.print("\nEnter Doctor ID to search: ");
            String id = sc.nextLine().trim();
            Doctor doc = doctorsMgmt.findDoctorById(id);
            if (doc != null) {
                System.out.println("\nDoctor found");
                System.out.println("Time:" + dateTime);
                System.out.println("ID: " + doc.getId());
                System.out.println("Name: " + doc.getName());
                System.out.println("Duty Schedule: " + doc.getDutySchedule());
                AvailabilityStatus avail = doc.isAvailableAt(today, now);
                System.out.println("Availability: " + (avail.available ? "Available" : "Not Available"));
                if (!avail.available) {
                    System.out.println("Reason: " + avail.reason);
                }
                displayShifts(doc);
            } else {
                messageUI.displayDoctorNotFound();
                while (true) {
                    messageUI.displayChoiceYesOrNo();
                    String retry = sc.nextLine().trim();
                    if (retry.equalsIgnoreCase("Y")) {
                        break;
                    } else if (retry.equalsIgnoreCase("N")) {
                        return;
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            }
            return;
        }
    }

    private void viewDoctorsWithoutDuty() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        List<Doctor> doctorsWithoutDuty = new ArrayList<>();
        for (Doctor doc : doctorList) {
            String duty = doc.getDutySchedule();
            if (duty == null || "Not Assigned".equals(duty)) {
                doctorsWithoutDuty.add(doc);
            }
        }

        if (doctorsWithoutDuty.isEmpty()) {
            System.out.println("--------------------------");
            System.out.printf("| %-10s | %-10s|\n", "ID", "Name");
            System.out.println("--------------------------");
            System.out.println("|All doctors have duties assigned.|");
            System.out.println("--------------------------");
            return;
        }

        System.out.println("--------------------------");
        System.out.printf("| %-10s | %-10s|\n", "ID", "Name");
        System.out.println("--------------------------");
        for (Doctor doc : doctorsWithoutDuty) {
            System.out.printf("| %-10s | %-10s|\n", doc.getId(), doc.getName());
        }
        System.out.println("--------------------------");
    }

    private void assignDuty() {
        viewDoctorsWithoutDuty();
        while (true) {
            System.out.print("Enter Doctor ID to assign duty: ");
            String id = sc.nextLine().trim();
            Doctor doc = doctorsMgmt.findDoctorById(id);
            if (doc != null) {
                String duty = doc.getDutySchedule();
                if (duty != null && !"Not Assigned".equals(duty)) {
                    messageUI.displayAlreadyHasDutySchedule();
                    while (true) {
                        messageUI.displayChoiceYesOrNo();
                        String retry = sc.nextLine().trim();
                        if (retry.equalsIgnoreCase("Y")) {
                            break;
                        } else if (retry.equalsIgnoreCase("N")) {
                            messageUI.displayReturnToDoctorManagementMenu();
                            return;
                        } else {
                            messageUI.displayInvalidChoiceYesOrNo();
                        }
                    }
                    continue;
                }
                String pattern1 = "^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)-(Mon|Tue|Wed|Thu|Fri|Sat|Sun) \\d{1,2}(am|pm)-\\d{1,2}(am|pm)$";
                String pattern2 = "^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)(,(Mon|Tue|Wed|Thu|Fri|Sat|Sun))* \\d{1,2}(am|pm)-\\d{1,2}(am|pm)$";
                String schedule;
                while (true) {
                    System.out.print("Enter duty schedule (e.g., Mon-Fri 9am-5pm or Mon,Wed,Fri 9am-5pm): ");
                    schedule = sc.nextLine().trim();
                    if (schedule.matches(pattern1) || schedule.matches(pattern2)) {
                        break;
                    } else {
                        messageUI.displayInvalidAssignDuty();
                    }
                }
                boolean success = doctorsMgmt.assignDuty(id, schedule);
                doctorsMgmt.setDoctorAvailability(id, true);
                if (success) {
                    messageUI.displayDutyAssigned();
                } else {
                    messageUI.displayInvalidDutyAssigned();
                }
                return;
            } else {
                messageUI.displayDoctorNotFound();
                while (true) {
                    messageUI.displayChoiceYesOrNo();
                    String retry = sc.nextLine().trim();
                    if (retry.equalsIgnoreCase("Y")) {
                        break;
                    } else if (retry.equalsIgnoreCase("N")) {
                        messageUI.displayReturnToDoctorManagementMenu();
                        return;
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            }
        }
    }

    private void doctorManagementList() {
        while (true) {
            System.out.println("\nDoctor Management List");
            System.out.println("1. Doctor List");
            System.out.println("2. Duty Schedule List");
            System.out.println("3. Doctor Appointment List");
            System.out.println("4. Doctor Shift List");
            System.out.println("5. Doctor Leave List");
            System.out.println("6. Back To Doctor Management Menu");

            System.out.print("Enter choice (1-5): ");
            String action = sc.nextLine().trim();

            if (action.equals("1")) {
                doctorList();
            } else if (action.equals("2")) {
                dutyScheduleList();
            } else if (action.equals("3")) {
                doctorAppointmentList();
            } else if (action.equals("4")) {
                doctorShiftList();
            } else if (action.equals("5")) {
                doctorLeaveList();
            } else if (action.equals("6")) {
                messageUI.displayReturnToDoctorManagementMenu();
                return;
            } else {
                messageUI.displayInvalidChoiceMessage4();
            }
            continue;
        }

    }

    private void doctorList() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        if (doctorList.isEmpty()) {
            System.out.println("----------------------------------------");
            System.out.printf("| %-10s | %-20s | %-25s |\n", "ID", "Name", "Availability");
            System.out.println("----------------------------------------");
            System.out.println("|       No doctors in the system.      |");
            System.out.println("----------------------------------------");
            return;
        }
        System.out.println("-----------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-25s |\n", "ID", "Name", "Duty Schedule");
        System.out.println("-----------------------------------------------------------");
        for (Doctor doc : doctorList) {
            System.out.printf("| %-10s | %-20s | %-25s |\n", doc.getId(), doc.getName(), doc.isAvailable() ? "Available" : "Not Available");
        }
        System.out.println("-----------------------------------------------------------");
    }

    private void dutyScheduleList() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        if (doctorList.isEmpty()) {
            System.out.println("----------------------------------------");
            System.out.printf("| %-10s | %-20s | %-25s |\n", "ID", "Name", "Duty Schedule");
            System.out.println("----------------------------------------");
            System.out.println("|       No doctors in the system.      |");
            System.out.println("----------------------------------------");
            return;
        }
        System.out.println("-----------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-25s |\n", "ID", "Name", "Duty Schedule");
        System.out.println("-----------------------------------------------------------");
        for (Doctor doc : doctorList) {
            System.out.printf("| %-10s | %-20s | %-25s |\n", doc.getId(), doc.getName(), doc.getDutySchedule());
        }
        System.out.println("-----------------------------------------------------------");
    }

    private void doctorAppointmentList() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-5s |\n", "ID", "Name", "Patient Name", "Date", "Time");
        System.out.println("--------------------------------------------------------------------------------------");
        boolean hasAppointment = false;
        for (Doctor doc : doctorList) {
            List<Appointment> appts = doc.getAppointments();
            if (appts == null || appts.isEmpty()) {
                continue;
            }
            appts.sort((a1, a2) -> {
                int cmp = a1.getDate().compareTo(a2.getDate());
                if (cmp != 0) {
                    return cmp;
                }
                return a1.getTime().compareTo(a2.getTime());
            });

            boolean firstRow = true;
            for (Appointment appt : appts) {
                hasAppointment = true;
                System.out.printf("| %-10s | %-20s | %-20s | %-15s | %-5s |\n",
                        firstRow ? doc.getId() : "",
                        firstRow ? doc.getName() : "",
                        appt.getPatientName(),
                        appt.getDate(),
                        appt.getTime());
                firstRow = false;
            }
        }
        if (!hasAppointment) {
            System.out.println("|                      No appointments found in the system.                       |");
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private void doctorShiftList() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-25s | %-25s |\n", "ID", "Name", "Duty Schedule", "Shift");
        System.out.println("---------------------------------------------------------------------------------------------");
        boolean hasShift = false;

        for (Doctor doc : doctorList) {
            List<String> shiftStrings = new ArrayList<>();
            List<Shift> shifts = doc.getShifts();
            if (shifts != null && !shifts.isEmpty()) {
                for (Shift shift : shifts) {
                    List<TimeRange> ranges = shift.getShiftRanges();
                    if (ranges != null && !ranges.isEmpty()) {
                        for (TimeRange tr : ranges) {
                            shiftStrings.add(tr.toString());
                        }
                    }
                }
            }
            if (!shiftStrings.isEmpty()) {
                hasShift = true;
                System.out.printf("| %-10s | %-20s | %-25s | %-25s |\n",
                        doc.getId(),
                        doc.getName(),
                        doc.getDutySchedule(),
                        shiftStrings.get(0) + (shiftStrings.size() > 1 ? "," : ""));
                for (int i = 1; i < shiftStrings.size(); i++) {
                    System.out.printf("| %-10s | %-20s | %-25s | %-25s |\n",
                            "", "", "",
                            shiftStrings.get(i) + (i < shiftStrings.size() - 1 ? "," : ""));
                }
            }
        }
        if (!hasShift) {
            System.out.println("|                          No shift found in the system.                                  |");
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void doctorLeaveList() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-20s |\n", "ID", "Name", "Start Date", "End Date", "Reason");
        System.out.println("------------------------------------------------------------------------------------------------");
        boolean hasLeave = false;

        for (Doctor doc : doctorList) {
            List<Leave> leaves = doc.getLeave();
            if (leaves != null && !leaves.isEmpty()) {
                for (Leave leave : leaves) {
                    hasLeave = true;
                    System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-20s |\n",
                            doc.getId(),
                            doc.getName(),
                            leave.getStartDate(),
                            leave.getEndDate(),
                            leave.getReason());
                }
            }
        }
        if (!hasLeave) {
            System.out.println("|                              No leave found in the system.                                 |");
        }
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    private void trackAvailability() {
        List<Doctor> doctors = new ArrayList<>();
        for (Doctor doc : doctorsMgmt.getAllDoctors()) {
            if (doc.getDutySchedule() != null && !"Not Assigned".equals(doc.getDutySchedule())) {
                doctors.add(doc);
            }
        }
        if (doctors.isEmpty()) {
            messageUI.displayNoDutiesCannotMakeAppointmentOrShifts();
            return;
        }
        System.out.println("Doctors with assigned duties:");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-25s |\n", "ID", "Name", "Duty Schedule");
        System.out.println("-----------------------------------------------------------");
        for (Doctor doc : doctors) {
            System.out.printf("| %-10s | %-20s | %-25s |\n", doc.getId(), doc.getName(), doc.getDutySchedule());
        }
        System.out.println("-----------------------------------------------------------");

        Doctor selectedDoc = null;
        while (selectedDoc == null) {
            System.out.print("Enter Doctor ID to make appointment/assign shift: ");
            String id = sc.nextLine().trim();
            Doctor doc = doctorsMgmt.findDoctorById(id);

            if (doc == null) {
                messageUI.displayDoctorNotFound();
                while (true) {
                    messageUI.displayChoiceYesOrNo();
                    String retry = sc.nextLine().trim();
                    if (retry.equalsIgnoreCase("Y")) {
                        break;
                    } else if (retry.equalsIgnoreCase("N")) {
                        messageUI.displayReturnToDoctorManagementMenu();
                        runDoctorsManagement();
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            } else if (doc.getDutySchedule() == null || "Not Assigned".equals(doc.getDutySchedule())) {
                messageUI.displayCannotBookDoctorNoHaveDutySchedule();
                while (true) {
                    messageUI.displayChoiceYesOrNo();
                    String retry = sc.nextLine().trim();
                    if (retry.equalsIgnoreCase("Y")) {
                        break;
                    } else if (retry.equalsIgnoreCase("N")) {
                        messageUI.displayReturnToDoctorManagementMenu();
                        return;
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            } else {
                selectedDoc = doc;
            }
        }

        Doctor doc = selectedDoc;
        System.out.println("Selected Doctor: " + doc.getName());
        System.out.println("Duty Schedule: " + doc.getDutySchedule());
        while (true) {
            System.out.println("1. Make Appointment");
            System.out.println("2. Assign Shift");
            System.out.println("3. Make Leave");
            System.out.println("4. Back To Doctor Management Menu");

            System.out.print("Enter choice (1-4): ");
            String action = sc.nextLine().trim();

            if (action.equals("1")) {
                makeAppointment(doc);
            } else if (action.equals("2")) {
                assignShift(doc);
            } else if (action.equals("3")) {
                makeLeave(doc);
            } else if (action.equals("4")) {
                return;
            } else {
                messageUI.displayInvalidChoiceMessage2();
            }
            continue;
        }
    }

    private void makeAppointment(Doctor doc) {
        String id = doc.getId();
        String name = doc.getName();
        String patientName;
        LocalDate apptDate;
        LocalTime apptTime;

        List<Shift> shifts;
        while (true) {
            shifts = doc.getShifts();
            if (shifts == null || shifts.isEmpty()) {
                System.out.println("This doctor hasn't assigned Shift.");
                System.out.println("Do you want to try another doctor? (Y/N)");
                String retry = sc.nextLine().trim();
                if (retry.equalsIgnoreCase("Y")) {
                    trackAvailability();
                } else if (retry.equalsIgnoreCase("N")) {
                    System.out.println("Returning to previous menu.");
                    return;
                } else {
                    messageUI.displayInvalidChoiceYesOrNo();
                }
            } else {
                System.out.println("Doctor's Shifts:");
                int shiftIdx = 1;
                for (Shift s : shifts) {
                    System.out.print("Shift #" + shiftIdx + ": ");
                    List<TimeRange> ranges = s.getShiftRanges();
                    for (TimeRange tr : ranges) {
                        System.out.print(tr + "  ");
                    }
                    System.out.println();
                    shiftIdx++;
                }
                break;
            }
        }

        while (true) {
            System.out.print("Enter Patient Name: ");
            patientName = sc.nextLine().trim();
            if (patientName.isEmpty()) {
                messageUI.displayPatientNameCannotEmpty();
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            try {
                apptDate = LocalDate.parse(sc.nextLine().trim());
            } catch (Exception e) {
                messageUI.displayInvalidDateFormat();
                continue;
            }

            if (!doctorsMgmt.isDateWithinDutyDay(doc.getDutySchedule(), apptDate)) {
                messageUI.displayCannotBookDoctorNoHaveDutySchedule();
                continue;
            }

            if (doc.isOnLeave(apptDate)) {
                messageUI.displayCannotBookDoctorIsOnLeave();
                continue;
            }

            while (true) {
                System.out.print("Enter Appointment Time (e.g., 10am, 10:30am, 2pm, 2:15pm): ");
                String timeStr = sc.nextLine().trim().toLowerCase();
                apptTime = doctorsMgmt.parseTimeWithMin(timeStr);
                if (apptTime == null) {
                    messageUI.displayInvalidTimeFormat();
                    continue;
                }

                boolean withinShift = false;
                outer:
                for (Shift s : shifts) {
                    for (TimeRange tr : s.getShiftRanges()) {
                        if (!apptTime.isBefore(tr.getStart()) && apptTime.isBefore(tr.getEnd())) {
                            withinShift = true;
                            break outer;
                        }
                    }
                }
                if (!withinShift) {
                    messageUI.displayAppointmentDoctorNotInAssignedTime();
                    continue;
                }

                boolean conflict = false;
                for (Appointment appt : doc.getAppointments()) {
                    if (appt.getDate().equals(apptDate)) {
                        long mins = Math.abs(java.time.Duration.between(appt.getTime(), apptTime).toMinutes());
                        if (mins == 0) {
                            messageUI.displayCannotBookAppointmentAlreadyExactTime();
                            conflict = true;
                            break;
                        }
                        if (mins < 30) {
                            messageUI.displayCannotBookAppointmentwithin30min();
                            conflict = true;
                            break;
                        }
                    }
                }
                if (conflict) {
                    continue;
                }

                int qtyForDate = 0;
                for (Appointment appt : doc.getAppointments()) {
                    if (appt.getDate().equals(apptDate)) {
                        qtyForDate++;
                    }
                }
                int qty = qtyForDate + 1;
                Appointment newAppt = new Appointment(id, name, patientName, qty, apptDate, apptTime);
                doc.addAppointment(newAppt);
                System.out.println("Appointment booked for " + patientName + " with Doctor " + doc.getName()
                        + " on " + apptDate + " at " + apptTime);
                return;
            }
        }
    }

    private void assignShift(Doctor doc) {
        String id = doc.getId();
        String name = doc.getName();
        String specialty = null;
        System.out.println("Doctor Name: " + doc.getName());
        System.out.println("Doctor Duty Schedule: " + doc.getDutySchedule());

        List<TimeRange> dutyRanges = doctorsMgmt.parseDutySchedule(doc.getDutySchedule());
        if (dutyRanges.isEmpty()) {
            messageUI.displayCannotAssignShift();
            return;
        }
        messageUI.displayAllowedShiftPeriodsBased();
        for (TimeRange tr : dutyRanges) {
            System.out.println(" - " + tr);
        }
        List<TimeRange> assignedShifts = new ArrayList<>();

        while (true) {
            System.out.print("Enter Shift Time (e.g., 9am-11am). Leave blank to finish: ");
            String shiftTimeStr = sc.nextLine().trim();
            if (shiftTimeStr.isEmpty()) {
                break;
            }

            TimeRange shiftRange = doctorsMgmt.parseTimeRange(shiftTimeStr);
            if (shiftRange == null) {
                messageUI.displayInvalidTimeRange();
                continue;
            }

            boolean withinDuty = false;
            for (TimeRange duty : dutyRanges) {
                if (duty.contains(shiftRange)) {
                    withinDuty = true;
                    break;
                }
            }
            if (!withinDuty) {
                messageUI.displayShiftTimeNotWithinAllowed();
                continue;
            }

            boolean overlaps = false;
            for (TimeRange assigned : assignedShifts) {
                if (assigned.overlapsWith(shiftRange)) {
                    overlaps = true;
                    break;
                }
            }
            if (overlaps) {
                messageUI.displayShiftOverlaps();
                continue;
            }

            assignedShifts.add(shiftRange);
            System.out.println("Shift added: " + shiftRange);
        }

        if (assignedShifts.isEmpty()) {
            messageUI.displayNoShiftAssigned();
            return;
        }

        Shift newShift = new Shift(id, name, specialty, assignedShifts);
        doc.addShift(newShift);

        System.out.println("Shifts assigned to Doctor " + name + ":");
        for (TimeRange assigned : assignedShifts) {
            System.out.println(" - " + assigned);
        }
        return;
    }

    private void makeLeave(Doctor doc) {
        LocalDate startDate = null;
        String id = doc.getId();
        String name = doc.getName();

        while (startDate == null) {
            System.out.print("Enter start date for taking leave (YYYY-MM-DD): ");
            String startDateStr = sc.nextLine().trim();
            try {
                startDate = LocalDate.parse(startDateStr);
            } catch (Exception e) {
                messageUI.displayInvalidDateFormat();
            }
        }

        int days = 0;
        while (days <= 0) {
            System.out.print("Enter number of days doctor will be taking leave: ");
            String daysStr = sc.nextLine().trim();
            try {
                days = Integer.parseInt(daysStr);
                if (days <= 0) {
                    messageUI.displayDayNumberMustPositive();
                }
            } catch (NumberFormatException e) {
                messageUI.displayInvalidDayNumber();
            }
        }

        String reason = "";
        while (reason.isEmpty()) {
            System.out.print("Enter reason for taking leave: ");
            reason = sc.nextLine().trim();
            if (reason.isEmpty()) {
                messageUI.displayReasonCannotEmpty();
            }
        }

        int qty = doc.getLeave().size() + 1;

        Leave newLeave = new Leave(id, name, startDate, days, reason, qty);
        doc.addLeave(newLeave);

        LocalDate endDate = startDate.plusDays(days);
        System.out.printf("Doctor %s makes a leave from %s to %s. Reason: %s\n", name, startDate, endDate, reason);
    }

    private String getValidDoctorId() {
        while (true) {
            System.out.print("Enter Doctor ID: ");
            String id = sc.nextLine().trim();
            Doctor doc = doctorsMgmt.findDoctorById(id);
            if (doc != null) {
                return id;
            } else {
                messageUI.displayDoctorNotFound();
                while (true) {
                    messageUI.displayChoiceYesOrNo();
                    String retry = sc.nextLine().trim();
                    if (retry.equalsIgnoreCase("Y")) {
                        break;
                    } else if (retry.equalsIgnoreCase("N")) {
                        return null;
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            }
        }
    }

    private void summaryReports() {
        while (true) {
            System.out.println("\nDoctor Management Summary Reports");
            System.out.println("1. Doctor Appointment");
            System.out.println("2. Doctor Leave");
            System.out.println("3. Doctor Shift Edit");
            System.out.println("4. Back To Doctor Management Menu");

            System.out.print("Enter choice (1-4): ");
            String action = sc.nextLine().trim();

            if (action.equals("1")) {
                doctorAppointmentSummaryReports();
            } else if (action.equals("2")) {
                doctorLeaveSummaryReports();
            } else if (action.equals("3")) {

            } else if (action.equals("4")) {
                messageUI.displayReturnToDoctorManagementMenu();
                return;
            } else {
                messageUI.displayInvalidChoiceMessage2();
            }
            continue;
        }
    }

    private void doctorAppointmentSummaryReports() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        System.out.println("------------------------------------------------");
        System.out.println("|      Doctor Appointment Summary Report       |");
        System.out.println("------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-8s |\n", "ID", "Name", "Quantity");
        System.out.println("------------------------------------------------");
        boolean hasAppointment = false;
        for (Doctor doc : doctorList) {
            List<Appointment> appts = doc.getAppointments();
            int qty = appts.size();
            if (qty > 0) {
                hasAppointment = true;
                System.out.printf("| %-10s | %-20s | %-8d |\n", doc.getId(), doc.getName(), qty);
            }
        }
        if (!hasAppointment) {
            System.out.println("|    No appointments found in the system.      |");
        }
        System.out.println("------------------------------------------------");
    }

    private void doctorLeaveSummaryReports() {
        List<Doctor> doctorList = doctorsMgmt.getAllDoctors();
        System.out.println("------------------------------------------------");
        System.out.println("|      Doctor Leave Summary Report             |");
        System.out.println("------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-8s |\n", "ID", "Name", "Quantity");
        System.out.println("------------------------------------------------");
        boolean hasLeave = false;
        for (Doctor doc : doctorList) {
            List<Leave> leaves = doc.getLeave();
            int qtys = leaves.size();
            if (qtys > 0) {
                hasLeave = true;
                System.out.printf("| %-10s | %-20s | %-8d |\n", doc.getId(), doc.getName(), qtys);
            }
        }
        if (!hasLeave) {
            System.out.println("|    No leave found in the system.            |");
        }
        System.out.println("------------------------------------------------");
    }

    private void doctorManagementEdit() {
        while (true) {
            System.out.println("\nDoctor Management Edit");
            System.out.println("1. Doctor Edit");
            System.out.println("2. Doctor Appointment Edit");
            System.out.println("3. Doctor Shift Edit");
            System.out.println("4. Back To Doctor Management Menu");

            System.out.print("Enter choice (1-4): ");
            String action = sc.nextLine().trim();

            if (action.equals("1")) {
                editDoctor();
            } else if (action.equals("2")) {
                editDoctorAppointment();
            } else if (action.equals("3")) {
                editDoctorShift();
            } else if (action.equals("4")) {
                messageUI.displayReturnToDoctorManagementMenu();
                return;
            } else {
                messageUI.displayInvalidChoiceMessage2();
            }
            continue;
        }
    }

    private void editDoctor() {
        doctorList();
        String id = getValidDoctorId();
        if (id == null) {
            return;
        }
        Doctor doc = doctorsMgmt.findDoctorById(id);
        System.out.println("Editing Doctor: " + doc.getName() + " (" + doc.getId() + ")");

        String pattern1 = "^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)-(Mon|Tue|Wed|Thu|Fri|Sat|Sun) \\d{1,2}(am|pm)-\\d{1,2}(am|pm)$";
        String pattern2 = "^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)(,(Mon|Tue|Wed|Thu|Fri|Sat|Sun))* \\d{1,2}(am|pm)-\\d{1,2}(am|pm)$";
        String newSchedule;
        while (true) {
            System.out.print("Enter duty schedule (e.g., Mon-Fri 9am-5pm or Mon,Wed,Fri 9am-5pm): ");
            newSchedule = sc.nextLine().trim();
            if (newSchedule.isEmpty()) {
                newSchedule = doc.getDutySchedule();
                break;
            }
            if (newSchedule.matches(pattern1) || newSchedule.matches(pattern2)) {
                break;
            } else {
                messageUI.displayInvalidAssignDuty();
            }
        }

        boolean success = doctorsMgmt.editDoctor(id, newSchedule);
        if (success) {
            messageUI.displayDoctorUpdatedSuccessfully();
        } else {
            messageUI.displayInvalidDoctorUpdated();
        }
    }

    private void editDoctorAppointment() {
        doctorAppointmentList();
        String doctorId = getValidDoctorId();
        if (doctorId == null) {
            return;
        }

        Doctor doc = doctorsMgmt.findDoctorById(doctorId);
        List<Appointment> appts = doc.getAppointments();
        if (appts == null || appts.isEmpty()) {
            messageUI.displayDoctorNoAppointmentToEdit();
            return;
        }

        System.out.println("Appointments for Dr. " + doc.getName() + ":");
        for (int i = 0; i < appts.size(); i++) {
            Appointment appt = appts.get(i);
            System.out.printf("%d. Patient: %s  | Date: %s | Time: %s\n",
                    i + 1, appt.getPatientName(), appt.getDate(), appt.getTime());
        }

        int idx = -1;
        while (true) {
            System.out.print("Enter the number of the appointment you want to edit: ");
            String input = sc.nextLine().trim();
            try {
                idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < appts.size()) {
                    break;
                } else {
                    messageUI.displayInvalidAppointmentNumber();
                }
            } catch (NumberFormatException e) {
                messageUI.displayEnterAValidNumber();
            }
        }
        Appointment appt = appts.get(idx);

        System.out.print("Enter new patient name (leave blank to keep current: " + appt.getPatientName() + "): ");
        String newPatientName = sc.nextLine().trim();
        if (!newPatientName.isEmpty()) {
            appt.setPatientName(newPatientName);
        }

        LocalDate newDate = appt.getDate();
        while (true) {
            System.out.print("Enter new date (YYYY-MM-DD, leave blank to keep current: " + appt.getDate() + "): ");
            String dateStr = sc.nextLine().trim();
            if (dateStr.isEmpty()) {
                break;
            }
            try {
                newDate = LocalDate.parse(dateStr);
                if (doc.isOnLeave(newDate)) {
                    messageUI.displayCannotBookDoctorIsOnLeave();
                    continue;
                }
                break;
            } catch (Exception e) {
                messageUI.displayInvalidDateFormat();
            }
        }

        LocalTime newTime = appt.getTime();
        List<Shift> shifts = doc.getShifts();
        while (true) {
            System.out.print("Enter new time (e.g., 10am, 2:30pm, leave blank to keep current: " + appt.getTime() + "): ");
            String timeStr = sc.nextLine().trim();
            if (timeStr.isEmpty()) {
                break;
            }
            newTime = doctorsMgmt.parseTimeWithMin(timeStr);
            if (newTime == null) {
                messageUI.displayInvalidTimeFormat();
                continue;
            }
            boolean withinShift = false;
            outer:
            for (Shift s : shifts) {
                for (TimeRange tr : s.getShiftRanges()) {
                    if (!newTime.isBefore(tr.getStart()) && newTime.isBefore(tr.getEnd())) {
                        withinShift = true;
                        break outer;
                    }
                }
            }
            if (!withinShift) {
                messageUI.displayAppointmentDoctorNotInAssignedTime2();
                continue;
            }

            boolean conflict = false;
            for (Appointment other : appts) {
                if (other == appt) {
                    continue;
                }
                if (other.getDate().equals(newDate)) {
                    long mins = Math.abs(java.time.Duration.between(other.getTime(), newTime).toMinutes());
                    if (mins == 0) {
                        messageUI.displayCannotBookAppointmentAlreadyExactTime();
                        conflict = true;
                        break;
                    }
                    if (mins < 30) {
                        messageUI.displayCannotBookAppointmentwithin30min();
                        conflict = true;
                        break;
                    }
                }
            }
            if (conflict) {
                continue;
            }
            break;
        }

        appt.setDate(newDate);
        appt.setTime(newTime);

        messageUI.displayAppointmentUpdatedSuccessfully();
    }

    private void editDoctorShift() {
        doctorShiftList();
        String doctorId = getValidDoctorId();
        if (doctorId == null) {
            return;
        }

        Doctor doc = doctorsMgmt.findDoctorById(doctorId);
        List<Shift> shifts = doc.getShifts();
        if (shifts == null || shifts.isEmpty()) {
            System.out.println("This doctor has no shifts to edit.");
            return;
        }

        System.out.println("Shifts for Dr. " + doc.getName() + ":");

        int idx = -1;
        while (true) {
            System.out.print("Enter the number of the shift you want to edit: ");
            String input = sc.nextLine().trim();
            try {
                idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < shifts.size()) {
                    break;
                } else {
                    messageUI.displayInvalidShiftNumber();
                }
            } catch (NumberFormatException e) {
                messageUI.displayEnterAValidNumber();
            }
        }
        Shift shift = shifts.get(idx);

        List<TimeRange> newRanges = new ArrayList<>();
        System.out.println("Current shift time ranges: " + shift.getShiftRanges());
        System.out.println("Enter new shift time ranges (e.g., 09:00-11:00), one per line. Leave blank to keep current.");
        while (true) {
            System.out.print("Enter shift time range (leave blank to finish): ");
            String rangeStr = sc.nextLine().trim();
            if (rangeStr.isEmpty()) {
                break;
            }
            String[] times = rangeStr.split("-");
            if (times.length == 2) {
                try {
                    LocalTime start = LocalTime.parse(times[0].trim());
                    LocalTime end = LocalTime.parse(times[1].trim());
                    if (start.isBefore(end)) {
                        newRanges.add(new TimeRange(start, end));
                    } else {
                        messageUI.displayStartTimeMust();
                    }
                } catch (Exception e) {
                    messageUI.displayInvalidTimeRange2();
                }
            } else {
                messageUI.displayInvalidTimeFormat2();
            }
        }
        if (!newRanges.isEmpty()) {
            shift.setShiftRanges(newRanges);
        }

        messageUI.displayShiftUpdatedSuccessfully();
    }

    private void doctorManagementRemove() {
        while (true) {
            System.out.println("\nDoctor Management Remove or Cancelled");
            System.out.println("1. Doctor Remove");
            System.out.println("2. Doctor Appointment Cancelled");
            System.out.println("3. Back To Doctor Management Menu");

            System.out.print("Enter choice (1-3): ");
            String action = sc.nextLine().trim();

            if (action.equals("1")) {
                removeDoctor();
            } else if (action.equals("2")) {
                cancelledAppointment();
            } else if (action.equals("3")) {
                messageUI.displayReturnToDoctorManagementMenu();
                return;
            } else {
                messageUI.displayInvalidChoiceMessage3();
            }
            continue;
        }
    }

    private void removeDoctor() {
        doctorList();
        while (true) {
            System.out.print("Enter Doctor ID to remove: ");
            String id = sc.nextLine().trim();
            Doctor doc = doctorsMgmt.findDoctorById(id);
            if (doc != null) {
                System.out.println("Doctor found:");
                System.out.println("ID: " + doc.getId());
                System.out.println("Name: " + doc.getName());
                System.out.println("Duty Schedule: " + doc.getDutySchedule());
                System.out.println("Shifts");
                System.out.println("===========");
                displayShifts(doc);

                List<Appointment> appts = doc.getAppointments();
                if (appts != null && !appts.isEmpty()) {
                    messageUI.displayWarningDoctorHasAppointmentScheduled();
                    messageUI.displayAppointmentWillCancelledIfProceed();
                    for (Appointment appt : appts) {
                        System.out.println(" - Patient: " + appt.getPatientName() + " | Date: " + appt.getDate() + " | Time: " + appt.getTime());
                    }
                }

                while (true) {
                    messageUI.displaySureRemoveDoctorAndAppointmentsYesOrNo();
                    String confirm = sc.nextLine().trim();
                    if (confirm.equalsIgnoreCase("Y")) {
                        if (appts != null && !appts.isEmpty()) {
                            cancelDoctorAppointments(doc);
                        }
                        doctorsMgmt.removeDoctor(id);
                        messageUI.displayDoctorRemovedSuccessfully();
                        return;
                    } else if (confirm.equalsIgnoreCase("N")) {
                        messageUI.displayDoctorRemovedCancelled();
                        return;
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            } else {
                messageUI.displayDoctorNotFound();
                while (true) {
                    messageUI.displayChoiceYesOrNo();
                    String retry = sc.nextLine().trim();
                    if (retry.equalsIgnoreCase("Y")) {
                        break;
                    } else if (retry.equalsIgnoreCase("N")) {
                        return;
                    } else {
                        messageUI.displayInvalidChoiceYesOrNo();
                    }
                }
            }
        }
    }

    private void displayShifts(Doctor doc) {
        List<Shift> shifts = doc.getShifts();
        if (shifts == null || shifts.isEmpty()) {
            System.out.println("  No shifts assigned.");
        } else {
            for (Shift shift : shifts) {
                List<TimeRange> ranges = shift.getShiftRanges();
                if (ranges != null && !ranges.isEmpty()) {
                    for (int i = 0; i < ranges.size(); i++) {
                        System.out.print(ranges.get(i));
                        if (i < ranges.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                } else {
                    System.out.print("No specific time ranges");
                }
                System.out.println();
            }
        }
    }

    private void cancelDoctorAppointments(Doctor doc) {
        List<Appointment> appts = doc.getAppointments();
        appts.clear();
    }

    private void cancelledAppointment() {
        doctorAppointmentList();
        String doctorId = getValidDoctorId();
        if (doctorId == null) {
            return;
        }

        Doctor doc = doctorsMgmt.findDoctorById(doctorId);
        List<Appointment> appts = doc.getAppointments();
        if (appts == null || appts.isEmpty()) {
            messageUI.displayDoctorNoAppointment();
            return;
        }

        System.out.println("Appointments for Dr. " + doc.getName() + ":");
        for (int i = 0; i < appts.size(); i++) {
            Appointment appt = appts.get(i);
            System.out.printf("%d. Patient: %s | Date: %s | Time: %s\n",
                    i + 1, appt.getPatientName(), appt.getDate(), appt.getTime());
        }

        int idx = -1;
        while (true) {
            System.out.print("Enter the number of the appointment you want to cancel: ");
            String input = sc.nextLine().trim();
            try {
                idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < appts.size()) {
                    break;
                } else {
                    messageUI.displayInvalidAppointmentNumber();
                }
            } catch (NumberFormatException e) {
                messageUI.displayEnterAValidNumber();
            }
        }
        while (true) {
            Appointment appt = appts.get(idx);
            System.out.printf("Are you sure you want to cancel the appointment with %s on %s at %s? (Y/N): ",
                    appt.getPatientName(), appt.getDate(), appt.getTime());
            String confirm = sc.nextLine().trim();

            if (confirm.equalsIgnoreCase("Y")) {
                appts.remove(idx);
                messageUI.displayAppointmentCancellationSuccessfully();
            } else if (confirm.equalsIgnoreCase("N")) {
                messageUI.displayAppointmentCancellationAborted();
                return;
            } else {
                messageUI.displayInvalidChoiceYesOrNo();
            }

        }

    }
}
