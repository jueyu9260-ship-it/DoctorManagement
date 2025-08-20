/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doctorsmanagement;

import java.util.ArrayList;
import java.util.List;

public class DoctorRepository implements Doctors {

    private final List<Doctor> doctorList = new ArrayList<>();

    @Override
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorList);
    }

    @Override
    public Doctor findDoctorById(String id) {
        for (Doctor doctor : doctorList) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorList.add(doctor);
    }

    @Override
    public void updateDoctor(Doctor doctor) {

    }

    @Override
    public void removeDoctor(Doctor doctor) {
        doctorList.remove(doctor);
    }

}
