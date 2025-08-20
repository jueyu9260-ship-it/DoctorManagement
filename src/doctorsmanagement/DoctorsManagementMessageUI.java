/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

public class DoctorsManagementMessageUI {

    public static void displayInvalidChoiceMessage() {
        System.out.println("\nInvalid choice.Please enter 1-9 only.");
    }

    public static void displayInvalidChoiceMessage2() {
        System.out.println("\nInvalid choice.Please enter 1-4 only.");
    }

    public static void displayInvalidChoiceMessage3() {
        System.out.println("\nInvalid choice.Please enter 1-3 only.");
    }

    public static void displayInvalidChoiceMessage4() {
        System.out.println("\nInvalid choice.Please enter 1-6 only.");
    }

    public static void displayReturnToDoctorManagementMenu() {
        System.out.println("Returning to Doctor Management Menu...");
    }

    public static void displayExitMessage() {
        System.out.println("\nExiting system");
    }

    public static void displayDoctorIdCannotEmpty() {
        System.out.println("\nDoctor ID cannot be empty.");
    }

    public static void displayDoctorNameCannotEmpty() {
        System.out.println("\nDoctor name cannot be empty.");
    }

    public static void displayPatientNameCannotEmpty() {
        System.out.println("Patient name cannot be empty.");
    }

    public static void displayDoctorNotFound() {
        System.out.println("\nDoctor not found.");
    }

    public static void displayDoctorIdAlreadyExists() {
        System.out.println("\nError: Doctor ID already exists! Please enter a different ID.");
    }

    public static void displayChoiceYesOrNo() {
        System.out.print("\nDo you want to enter the Doctor ID again? (Y/N): ");
    }

    public static void displayInvalidChoiceYesOrNo() {
        System.out.println("\nInvalid input.Please type 'Y' for yes or 'N' for no.");
    }

    public static void displayAddDoctorSuccessfully() {
        System.out.println("\nDoctor added successfully!");
    }

    public static void displayInvalidAddDoctor() {
        System.out.println("\nError adding doctor!");
    }

    public static void displayAlreadyHasDutySchedule() {
        System.out.println("Invalid. This doctor already has a Duty Schedule");
    }

    public static void displayNoDutiesCannotMakeAppointmentOrShifts() {
        System.out.println("No doctors with assigned duties available for appointments/shifts.");
    }

    public static void displayCannotBookDoctorNoHaveDutySchedule() {
        System.out.println("Cannot book: This doctor doesn't have a duty schedule.");
    }

    public static void displayCannotBookDoctorIsOnLeave() {
        System.out.println("Cannot book: The doctor is on leave on this date.");
    }

    public static void displayDutyAssigned() {
        System.out.println("\nDuty assigned.");
    }

    public static void displayInvalidDutyAssigned() {
        System.out.println("\nError assigning duty.");
    }

    public static void displayInvalidAssignDuty() {
        System.out.println("\nInvalid format! Please use 'Mon-Fri 9am-5pm' or 'Mon,Wed,Fri 9am-5pm'.");
    }

    public static void displayInvalidDateFormat() {
        System.out.println("\nInvalid date format. Please use YYYY-MM-DD.");
    }

    public static void displayDayNumberMustPositive() {
        System.out.println("\nDays must be a positive number.");
    }

    public static void displayInvalidDayNumber() {
        System.out.println("\nInvalid number. Please enter a positive integer.");
    }

    public static void displayReasonCannotEmpty() {
        System.out.println("\nReason cannot be empty.");
    }

    public static void displayDoctorNoAppointmentToEdit() {
        System.out.println("\nThis doctor has no appointments to edit.");
    }

    public static void displayAppointmentDoctorNotInAssignedTime() {
        System.out.println("\nAppointment time is not within the doctor's assigned shift times. Please choose a time shown above.");
    }

    public static void displayAppointmentDoctorNotInAssignedTime2() {
        System.out.println("\nAppointment time is not within the doctor's assigned shift times.");
    }

    public static void displayCannotBookAppointmentAlreadyExactTime() {
        System.out.println("\nCannot book: There is already an appointment at this exact time.");
    }

    public static void displayCannotBookAppointmentwithin30min() {
        System.out.println("\nCannot book: There is already an appointment within 30 minutes of this time.");
    }

    public static void displayDoctorUpdatedSuccessfully() {
        System.out.println("\nDoctor updated successfully!");
    }

    public static void displayAppointmentUpdatedSuccessfully() {
        System.out.println("\nAppointment updated successfully.");
    }

    public static void displayShiftUpdatedSuccessfully() {
        System.out.println("\nShift updated successfully.");
    }

    public static void displayInvalidDoctorUpdated() {
        System.out.println("\nError updating doctor!");
    }

    public static void displayDoctorRemovedSuccessfully() {
        System.out.println("\nDoctor remove successfully!");
    }

    public static void displayDoctorRemovedCancelled() {
        System.out.println("\nDoctor removal cancelled.");
    }

    public static void displayWarningDoctorHasAppointmentScheduled() {
        System.out.println("\nWarning: This doctor still has appointments scheduled!");
    }

    public static void displayAppointmentWillCancelledIfProceed() {
        System.out.println("\nThese appointments will be cancelled if you proceed.");
    }

    public static void displaySureRemoveDoctorAndAppointmentsYesOrNo() {
        System.out.print("\nAre you sure you want to remove this doctor and cancel all their appointments? (Y/N): ");
    }

    public static void displayInvalidTimeFormat() {
        System.out.println("Invalid time format. Use e.g., 10am, 10:30am, 2pm, 2:15pm.");
    }

    public static void displayInvalidTimeFormat2() {
        System.out.println("Invalid format. Use e.g., 09:00-11:00");
    }

    public static void displayInvalidTimeRange() {
        System.out.println("Invalid time range format. Try again, Use e.g., 9am-11am.");
    }

    public static void displayInvalidTimeRange2() {
        System.out.println("Invalid time range format.");
    }

    public static void displayStartTimeMust() {
        System.out.println("Start time must be before end time.");
    }

    public static void displayInvalidAppointmentNumber() {
        System.out.println("\nInvalid number. Please select a valid appointment.");
    }

    public static void displayInvalidShiftNumber() {
        System.out.println("\nInvalid number. Please select a valid Shift.");
    }

    public static void displayNoShiftAssigned() {
        System.out.println("No shift assigned.");
    }

    public static void displayCannotAssignShift() {
        System.out.println("Cannot assign shift: Invalid or missing duty schedule.");
    }

    public static void displayAllowedShiftPeriodsBased() {
        System.out.println("Allowed shift periods based on duty schedule:");
    }

    public static void displayShiftTimeNotWithinAllowed() {
        System.out.println("Shift time is not within allowed duty periods.");
    }

    public static void displayShiftOverlaps() {
        System.out.println("Shift overlaps with already assigned shift. Please enter a non-overlapping period.");
    }

    public static void displayDoctorNoAppointment() {
        System.out.println("\nThis doctor has no appointments to cancel.");
    }

    public static void displayEnterAValidNumber() {
        System.out.println("\nPlease enter a valid number.");
    }

    public static void displayAppointmentCancellationSuccessfully() {
        System.out.println("\nAppointment cancelled successfully.");
    }

    public static void displayAppointmentCancellationAborted() {
        System.out.println("\nAppointment cancellation aborted.");
    }

}
