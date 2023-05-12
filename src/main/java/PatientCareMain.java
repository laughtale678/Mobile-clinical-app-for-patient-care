
import java.util.Scanner;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.ConfigureAClinic.ConfigureAClinic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kal bugrara
 */

public class PatientCareMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean condition = true;
        Scanner scanner = new Scanner(System.in);
        // Configure a clinic and add 200 patients and 200 encounters
        Clinic healthExpress = ConfigureAClinic.createAClinicWithALotsOfData("Health Express", 200, 200);

        // Run the program in a loop until the user chooses to exit
        do {
            // Display the main menu options to the user
            System.out.println("===================Health Express Clinic===================");
            System.out.println("\t\t\t1. Patient Dictionary");
            System.out.println("\t\t\t2. Locations and Sites");
            System.out.println("\t\t\t3. Events");
            System.out.println("\t\t\t4. Sick Patients and Sites Report");
            System.out.println("\t\t\t5. Potential Cross-Site Infection Patients Report");
            System.out.println("\t\t\t6. Diagnosis Report by Site and Month");
            System.out.println("\t\t\t7. Diagnosis Trend by Site and Month");
            System.out.println("\t\t\t8. Third Party Services");
            System.out.println("\t\t\t9. Exit");
            System.out.println("Please inpute a number from 1-9: ");
            // Read the user's choice from the console
            int choice = scanner.nextInt();
            /*
             * Based on the user's choice, execute the corresponding menu option.
             * Case 1-3:
             * Show 200 paitents and their encounter history and clinic histroy >> Input the
             * index of a patient and print his infomation
             * Show 1 location and 4 sites
             * Show 12 events
             * 
             * Case 4: Sick Patients and Locations Report.
             * For Q1: Is there an easy way to find sick patients and locations were last
             * seen?
             * 
             * Case 5: Potential Cross-Site Infection Patients Report.
             * For Q2: Is there a way to find patients that might have infected others in
             * other locations?
             * 
             * Case 6 +Case 7: Diagnosis Report by Sites + Diagnosis Trend by Sites.
             * For Q3: Is there a way to show the number of infection incidents by location.
             * How diagnosed incidents are trending by location?
             * Case 8: Show local third party services information.
             * For Q4: What if we want to include local services completing the mobile
             * clinic such as local mental health care services to be leveraged in response
             * to sick patients?
             * 
             * Case 9: Exit
             * 
             */
            switch (choice) {
                case 1:
                    patientsMenu(healthExpress);
                    break;
                case 2:
                    locationsAndSitsMenu(healthExpress);
                    break;
                case 3:
                    eventsMenu(healthExpress);
                    break;
                case 4:
                    healthExpress.sickPatientsReport();
                    break;
                case 5:
                    healthExpress.multipleSitesPatientsReport();
                    break;
                case 6:
                    healthExpress.diagnosisReport();
                    break;
                case 7:
                    healthExpress.trendingReport();
                    break;
                case 8:
                    thirdPartyMenu(healthExpress);
                    break;
                case 9:
                    condition = false;
                    break;
                default:
                    System.out.println("Wrong input, try again");
            }
        } while (condition);
    }

    /*
     * Input the index of a patient and print his infomation
     * Information includes:
     * Id, Age, Name
     * Vaccination History, Allergy History
     * Encounter Historys(Date, Site, Chief Complaint, Vital Signs, Diagnosis,
     * Refferal Status)
     */
    private static void patientsMenu(Clinic c) {
        Scanner s = new Scanner(System.in);
        PatientDirectory pd = c.getPatientDirectory();
        pd.printInfo();
        System.out.println("Input the index of a patient to see detailed info:");
        int input = s.nextInt();
        if (input < 0 || input > pd.getPatients().size()) {
            System.out.println("wrong index");
        } else {
            pd.getPatients().get(input - 1).printDetailInfo();
        }

    }

    // Print all site
    private static void locationsAndSitsMenu(Clinic c) {
        c.getSiteCatalog().printInfo();
    }

    // Print all event
    private static void eventsMenu(Clinic c) {
        c.getEventschedule().printInfo();
    }

    // Print all local third party sevices
    private static void thirdPartyMenu(Clinic c) {
        c.getThirdPartyServiceCatalogs().printInfo();
    }

}
