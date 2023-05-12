/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Patient.Encounters;

import java.util.ArrayList;

import PatientManagement.Catalogs.Limits;
import PatientManagement.Patient.Patient;

/**
 *
 * @author kal bugrara
 */
public class VitalSigns {
    Patient patient;
    Encounter encounter;
    ArrayList<VitalSignMetric> vitalSignMetrics;

    public ArrayList<VitalSignMetric> getVitalSignMetrics() {
        return vitalSignMetrics;
    }

    public VitalSigns(Encounter e) {
        encounter = e;
        vitalSignMetrics = new ArrayList<VitalSignMetric>();
    }

    public VitalSignMetric addNewVitals(String name, int value) {
        Patient patient = encounter.getEncounterHistory().getPatient();
        int age = patient.getPerson().getAge();
        Limits limits = encounter.getVitalSignLimits(age, name);
        if (limits == null)
            return null;
        VitalSignMetric newVitals = new VitalSignMetric(patient, name, limits, value);
        vitalSignMetrics.add(newVitals);
        return newVitals;
    }

    public Boolean areNormal() {
        boolean normal = true;
        for (VitalSignMetric vsm : vitalSignMetrics) {
            if (!vsm.isNormal())
                normal = false;
        }

        return normal;
    }

    

}
