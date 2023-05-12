package PatientManagement.ConfigureAClinic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.github.javafaker.Faker;

import PatientManagement.Catalogs.AgeGroup;
import PatientManagement.Catalogs.ThirdPartyServiceCatalog;
import PatientManagement.Catalogs.VitalSignLimits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Clinic.EventSchedule;
import PatientManagement.Clinic.Location;
import PatientManagement.Clinic.LocationList;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.Clinic.Site;
import PatientManagement.Clinic.SiteCatalog;
import PatientManagement.Patient.Patient;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Patient.Encounters.EncounterHistory;
import PatientManagement.Persona.Person;
import PatientManagement.Persona.PersonDirectory;

public class ConfigureAClinic {
    static Faker magicBox = new Faker();
    public static Clinic createAClinicWithALotsOfData(String name, int patientNumber, int encounterNumber) {
        Clinic clinic = new Clinic(name);

        // load vitalsigns
        loadVitalSigns(clinic);


        // load person and patients, also load history record for the first 50 patients
        loadPatients(clinic,patientNumber);        

        //load locations and sites
        loadLocationsAndSites(clinic);

        //load third party services
        loadThirdPartyService(clinic);

        //load events and encounters
        loadEventsAndEncounters(clinic, encounterNumber);
    
        return clinic;
    }

    public static int getRandom(int lower, int upper) {
        Random r = new Random();
        int randomInt = lower + r.nextInt(upper - lower);
        return randomInt;
      }
    
    // Prepare random ID list for person
    public static ArrayList<String> getRandomIdSet(int number) {
        ArrayList<String> randomIdSet = new ArrayList<>();
        // String containing all possible characters for the IDs
        String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // StringBuilder for creating each ID
        StringBuilder sb = new StringBuilder();
        // Prefix for each ID
        sb.append("WDL");
        Random random = new Random();
        // Generate the specified number of IDs
        for(int i = 0; i < number + number; i ++){
            // Generate each character for the ID
            for (int j = 0; j < 9; j++) {
                int indexForThisID = random.nextInt(alphaNumeric.length());
                sb.append(alphaNumeric.charAt(indexForThisID));
            }
            // Avoid duplicates
            if(!randomIdSet.contains(sb.toString())) randomIdSet.add(sb.toString());
            // Reset the StringBuilder for creating the next ID
            sb.delete(3, 12);
        }
        return randomIdSet;
    }

    // Load vital signs
    static void loadVitalSigns(Clinic c) {
        VitalSignsCatalog vitalSignsCatalog = c.getVitalSignsCatalog();
        AgeGroup ageGroup18_70 = vitalSignsCatalog.newAgeGroup("Age18-70", 18, 70);
        VitalSignLimits heartRateLimits = vitalSignsCatalog.newVitalSignLimits("HR");
        VitalSignLimits bloodPressureLimits = vitalSignsCatalog.newVitalSignLimits("BP");
        VitalSignLimits respiratoryRate = vitalSignsCatalog.newVitalSignLimits("RR");
        heartRateLimits.addLimits(ageGroup18_70, 55, 105);
        bloodPressureLimits.addLimits(ageGroup18_70, 80, 120);
        respiratoryRate.addLimits(ageGroup18_70, 12, 18);
    }

    // load patients
    static void loadPatients(Clinic c, int number) {
        // Create person
        PersonDirectory personDirectory = c.getPersonDirectory();
        for(int i = 0; i < number; i++) {
            String name = magicBox.name().fullName();
            int age = getRandom(18, 70);
            String id = getRandomIdSet(number).get(i); 
            personDirectory.newPerson(id, name, age);
        }

        // Create patients
        ArrayList<Person> personList = personDirectory.getPersonlist();
        PatientDirectory patientDirectory = c.getPatientDirectory();
        for(Person p : personList) {
            patientDirectory.newPatient(p);
        }

        // First 50 patients have alergy history and vaccination history
        ArrayList<Patient> patients = patientDirectory.getPatients();
        for(int i = 0; i < 50; i++) {
            patients.get(i).getAlergyhistory().addAlergy("Peanut");
            patients.get(i).getVachistory().addVaccination("Flu");
        }
        
    }

    // load locations and sites
    static void loadLocationsAndSites(Clinic c) {
        LocationList locationList = c.getLocationList();
        Location se = locationList.newLocation("Seattle");
        SiteCatalog siteCatalog = c.getSiteCatalog();
        // Add four new sites to the site catalog, each associated with the "Seattle" location created above.
        siteCatalog.newSite("SouthLakeUnion", se);
        siteCatalog.newSite("CapitolHill", se);
        siteCatalog.newSite("QueenAnne", se);
        siteCatalog.newSite("DownTownSea", se);
    }
    
    // load ThirdPartyServices
    static void loadThirdPartyService(Clinic c) {
       ThirdPartyServiceCatalog tpc= c.getThirdPartyServiceCatalogs();
       ArrayList<Site> sites = c.getSiteCatalog().getSites();
       tpc.newThirdPartyService("PM","PharmarcySLU",sites.get(0));
       tpc.newThirdPartyService("PM","PharmarcySLU2",sites.get(0));
       tpc.newThirdPartyService("MH","MentalHealthSLU",sites.get(0));
       tpc.newThirdPartyService("PT","PhysicalTherapySLU",sites.get(0));
       tpc.newThirdPartyService("PM","PharmarcyCH",sites.get(1));
       tpc.newThirdPartyService("MH","MentalHealthCH",sites.get(1));
       tpc.newThirdPartyService("MH","MentalHealthCH2",sites.get(1));
       tpc.newThirdPartyService("PT","PhysicalTherapyCH",sites.get(1));
       tpc.newThirdPartyService("PM","PharmarcyQA",sites.get(2));
       tpc.newThirdPartyService("MH","MentalHealthQA",sites.get(2));
       tpc.newThirdPartyService("PT","PhysicalTherapyQA",sites.get(2));
       tpc.newThirdPartyService("PT","PhysicalTherapyQA2",sites.get(2));
       tpc.newThirdPartyService("PM","PharmarcyDT",sites.get(3));
       tpc.newThirdPartyService("PM","PharmarcyDT2",sites.get(3));
       tpc.newThirdPartyService("MH","MentalHealthDT",sites.get(3));       
       tpc.newThirdPartyService("MH","MentalHealthDT2",sites.get(3));
       tpc.newThirdPartyService("PT","PhysicalTherapyDT",sites.get(3));
       tpc.newThirdPartyService("PT","PhysicalTherapyDT2",sites.get(3));

    }

    // Load events and encounters
    static void loadEventsAndEncounters(Clinic clinic, int number) {
        EventSchedule eventSchedule = clinic.getEventschedule();
        ArrayList<Site> sites= clinic.getSiteCatalog().getSites();
        // Create a series of events, one for each site and month
        Event eventSLU01 = eventSchedule.newEvent("2023-01",sites.get(0), "0");
        Event eventSLU02 = eventSchedule.newEvent("2023-02",sites.get(0), "0");
        Event eventSLU03 = eventSchedule.newEvent("2023-03",sites.get(0), "0");

        Event eventCH01 = eventSchedule.newEvent("2023-01",sites.get(1), "1");
        Event eventCH02 = eventSchedule.newEvent("2023-02",sites.get(1), "1");
        Event eventCH03 = eventSchedule.newEvent("2023-03",sites.get(1), "1");

        Event eventQA01 = eventSchedule.newEvent("2023-01",sites.get(2), "2");
        Event eventQA02 = eventSchedule.newEvent("2023-02",sites.get(2), "2");
        Event eventQA03 = eventSchedule.newEvent("2023-03",sites.get(2), "2");

        Event eventDT01 = eventSchedule.newEvent("2023-01",sites.get(3), "3");
        Event eventDT02 = eventSchedule.newEvent("2023-02",sites.get(3), "3");
        Event eventDT03 = eventSchedule.newEvent("2023-03",sites.get(3), "3");

        ArrayList<Event> eventsCatalog = eventSchedule.getScheduledevents();
        //load 200 encounters for random event with random patients
        PatientDirectory pd = clinic.getPatientDirectory();
        ArrayList<Patient> patients = pd.getPatients();
        Boolean[] booleans = {true, false};
        String[] diseaseTypes = {"infectious","others"};
        String[] thirdPartyTypes = {"PM", "MH", "PT"};
        for(int i = 0; i < number; i++) {
            // Choose a random patient from the patient directory.
            Patient p = patients.get(getRandom(0, patients.size()));
            // Create a new encounter for the patient with a random event and chief complaint of "Sore Throat".
            Event event = eventsCatalog.get(getRandom(0, eventsCatalog.size()));
            Encounter encounter = p.newEncounter("Sore Throat", event);
            // Add random vital sign measurements to the encounter.
            encounter.addNewVitals("BP", getRandom(70, 150));
            // Add a new diagnosis to the encounter with a random disease type and confirmation status.
            Boolean b = booleans[getRandom(0, booleans.length)];
            encounter.newDiagnosis(diseaseTypes[getRandom(0, diseaseTypes.length)], b);
            // Add some referral to confirmed patients
            if(b && i%2 ==0) encounter.newReferral(thirdPartyTypes[getRandom(0, thirdPartyTypes.length)]);
        }
    }
}
