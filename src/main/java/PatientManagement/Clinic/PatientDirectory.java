/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Clinic;

import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Patient.Patient;
import PatientManagement.Persona.Person;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class PatientDirectory {
    Clinic clinic;
    ArrayList<Patient> patients;

    PatientDirectory(Clinic clinic) {
        this.clinic = clinic;
        patients = new ArrayList<Patient>();
    }

    public Patient newPatient(Person person) {
        Patient patient = new Patient(person, clinic);
        patients.add(patient);
        return patient;
    }

    public void printInfo() {
        System.out.println("====================Patient Directory====================");
        System.out.println("Index\tID\t\tAge\t\tName");
        for(Patient p : patients) {
            int index = patients.indexOf(p);
            System.out.print((index+1) + "\t");
            p.printInfo();
        }
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }


}