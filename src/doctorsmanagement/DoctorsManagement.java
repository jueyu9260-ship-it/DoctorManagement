/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package doctorsmanagement;

import java.time.LocalDate;
import java.util.*;

public class DoctorsManagement {

    private Doctors doct;
    private DoctorsManagementUI doctorsUI;

    public DoctorsManagement(Doctors doct) {
        this.doct = doct;
        this.doctorsUI = new DoctorsManagementUI(this);
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
