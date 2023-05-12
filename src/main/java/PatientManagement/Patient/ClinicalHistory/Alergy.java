/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Patient.ClinicalHistory;

/**
 *
 * @author kal bugrara
 */
public class Alergy {
    String name;

    public Alergy(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
