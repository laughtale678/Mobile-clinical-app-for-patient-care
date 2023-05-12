/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Patient;

import PatientManagement.Catalogs.Limits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.Patient.ClinicalHistory.AlergyHistory;
import PatientManagement.Patient.ClinicalHistory.MedicationHistory;
import PatientManagement.Patient.ClinicalHistory.VaccinationHistory;
import PatientManagement.Patient.Encounters.Diagnosis;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Patient.Encounters.EncounterHistory;
import PatientManagement.Persona.Person;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.transform.Source;

/**
 *
 * @author kal bugrara
 */
public class Patient {
    Clinic clinic;
    EncounterHistory encounterhistory;
    VaccinationHistory vachistory;
    Person person;
    AlergyHistory alergyhistory;

    public Patient(Person p, Clinic clinic) {
        encounterhistory = new EncounterHistory(this); // link this patient object back
        vachistory = new VaccinationHistory();
        alergyhistory = new AlergyHistory();
        person = p;
        this.clinic = clinic;
    }

    public EncounterHistory getEncounterHistory() {
        return encounterhistory;
    }
    // the below method will return the encounter where the infection occured
    // from the returned encounter you pull the event, the site, etc.

    public Encounter getConfirmedEncounter() {
        ArrayList<Encounter> patientencounterlist = encounterhistory.getEncounterList();

        for (Encounter currentencounter : patientencounterlist) {
            Diagnosis diag = currentencounter.getDiagnosis();
            if (diag.isConfirmed()) {
                return currentencounter;// send back the whole encounter so we extract event and site
            }
        }
        return null;
    }

    // Check if the patient is confirmed to be sick in last encounter.
    public boolean lastEncounterIsConfirmedSick() {
        ArrayList<Encounter> patientencounterlist = encounterhistory.getEncounterList();
        if(patientencounterlist.size() == 0) return false;
        Encounter lastEncounter = patientencounterlist.get(patientencounterlist.size() - 1);
        Diagnosis diag = lastEncounter.getDiagnosis();
        return diag.isConfirmed();
    }

    // Check if the patient was infectious in the past encounters.
    public boolean isConfirmedInfectious() {
        ArrayList<Encounter> patientencounterlist = encounterhistory.getEncounterList();
        for (Encounter currentencounter : patientencounterlist) {
            Diagnosis diag = currentencounter.getDiagnosis();
            if(diag.isConfirmed() == true && diag.getCategory().equals("infectious")) return diag.isConfirmed();
        }
        return false;
    }

    public Person getPerson() {
        return person;
    }

    public Encounter newEncounter(String chiefcomplaint, Event ev) {
        return encounterhistory.newEncounter(chiefcomplaint, ev);
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void printInfo() {
        System.out.println(person.getPersonId() + "\t " + person.getAge() + "\t" + person.getName());
    }

    public VaccinationHistory getVachistory() {
        return vachistory;
    }

    public AlergyHistory getAlergyhistory() {
        return alergyhistory;
    }

    public void printDetailInfo() {
        System.out.println("========Patient Detailed Info========");
        System.out.println("ID: " + person.getPersonId() + " Age: " + person.getAge() + " Name: " + person.getName());
        System.out.println("VaccinationHistory:" + vachistory.getVaccinations());
        System.out.println("AlergyHistory:" + alergyhistory.getAlergies());
        System.out.println("--------EncounterHistorys:--------");
        this.encounterhistory.printInfo();

    }

    public int countSites() {
        HashSet<String> countSet = new HashSet<>();
        for (Encounter e : encounterhistory.getEncounterList()) {
            countSet.add(e.getEvent().getSite().getName());
        }
        return countSet.size();
    }

}
