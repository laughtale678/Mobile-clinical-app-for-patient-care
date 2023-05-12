/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Patient.Encounters;

import PatientManagement.Clinic.Event;
import PatientManagement.Patient.Patient;
import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class EncounterHistory {
    ArrayList<Encounter> encounters;
    Patient patient;

    public EncounterHistory(Patient p) {
        patient = p;
        encounters = new ArrayList<Encounter>();
    }

    // each encounter must link to the event at the site
    public Encounter newEncounter(String chiefcomplaint, Event ev) {
        Encounter e = new Encounter(patient, chiefcomplaint, ev, this);
        encounters.add(e);
        return e;
    }

    public ArrayList<Encounter> getEncounterList() {
        return encounters;
    }

    public Patient getPatient() {
        return patient;
    }
    
    // Print encounter history for this person
    public void printInfo() {
        if(encounters == null) {
            System.out.println("No encounter info for this patient.");
        }else {
            for(Encounter e : encounters) {
                System.out.println("Date: " + e.event.getDate() + " Site: " + e.event.getSite().getName());
                System.out.println("ChiefComplaint: " + e.getChiefComplaint());
                System.out.println("VitalSigns: " + e.getVitalSigns().getVitalSignMetrics());
                System.out.println("Diagnosis: " + e.getDiagnosis());
                System.out.println("Referral: " + e.getReferral());
            }
        }
    }
    
    

}
