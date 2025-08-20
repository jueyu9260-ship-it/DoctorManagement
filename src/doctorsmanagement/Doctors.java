/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package doctorsmanagement;

import java.util.*;

public interface Doctors {
    List<Doctor> getAllDoctors();
    Doctor findDoctorById(String id);
    public void addDoctor(Doctor doctor);
    public void updateDoctor(Doctor doctor);
    public void removeDoctor(Doctor doctor);
}
