/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Clinic;

import java.text.NumberFormat;
import java.util.ArrayList;

import PatientManagement.Catalogs.DrugCatalog;
import PatientManagement.Catalogs.ThirdPartyServiceCatalog;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Patient.Patient;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Patient.Encounters.EncounterHistory;
import PatientManagement.Persona.PersonDirectory;

/**
 *
 * @author kal bugrara
 */
public class Clinic {
    PatientDirectory patientdirectory;
    PersonDirectory persondirectory;
    SiteCatalog sitecatalog;
    LocationList locationlist;
    DrugCatalog drugcatalog;
    EventSchedule eventschedule;
    VitalSignsCatalog vitalSignsCatalog;
    ThirdPartyServiceCatalog thirdPartyServiceCatalog;
    String name; // name of the clinic

    public Clinic(String n) {
        eventschedule = new EventSchedule();
        sitecatalog = new SiteCatalog();
        locationlist = new LocationList();
        persondirectory = new PersonDirectory();
        patientdirectory = new PatientDirectory(this);
        vitalSignsCatalog = new VitalSignsCatalog();
        thirdPartyServiceCatalog = new ThirdPartyServiceCatalog(this);
        name = n;
    }

    public SiteCatalog getSiteCatalog() {
        return sitecatalog;
    }

    public LocationList getLocationList() {
        return locationlist;
    }

    public Site newSite(String name, Location location) {

        Site s = sitecatalog.newSite(name, location);
        return s;
    }

    public VitalSignsCatalog getVitalSignsCatalog() {
        return vitalSignsCatalog;
    }

    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public PatientDirectory getPatientDirectory() {
        return patientdirectory;
    }

    public EventSchedule getEventschedule() {
        return eventschedule;
    }

    public ThirdPartyServiceCatalog getThirdPartyServiceCatalogs() {
        return thirdPartyServiceCatalog;
    }

    /*
     * Q1: Is there an easy way to find sick patients and where they were last seen?
     * Answer: We define a method that prints all sick patients and their last known
     * sites.
     * Print Format: Data and time last seen, Patient ID, Patient Name
     */
    public void sickPatientsReport() {
        // Print report header
        System.out.println("===========Sick Patients and Sites Report============");
        System.out.println("Date and sites last seen\tId\t\tName");

        // Iterate over each patient
        for (Patient p : patientdirectory.getPatients()) {
            // Check if the patient is confirmed to be sick in last encounter
            if (p.lastEncounterIsConfirmedSick()) {
                // Get the patient's encounters and extract the date and site of the last encounter
                ArrayList<Encounter> encounters = p.getEncounterHistory().getEncounterList();
                String date = encounters.get(encounters.size() - 1).getEvent().getDate();
                String sitesName = encounters.get(encounters.size() - 1).getEvent().getSite().getName();
                // Print the date, site, patient ID, and patient name for the last encounter
                System.out.println(
                        date + " " + sitesName + "\t" + p.getPerson().getPersonId() + "\t" + p.getPerson().getName());
            }
        }
    }

    /*
     * Q2: Is there a way to find patients that might have infected others in other
     * sites?
     * Answer: We define a method to print all potential Cross-Site infection
     * patients and the number of their visited sites.
     * Print Format: Number of visited sites, Patient ID, Patient Name.
     */
    public void multipleSitesPatientsReport() {
        // Print report header
        System.out.println("========Potential Cross-Site Infection Patients Report========");
        System.out.println("Visited sites\tId\tName");

        // Iterate over each patient
        for (Patient p : patientdirectory.getPatients()) {
             // Check if the patient was infectious in the past encounters and has visited more than one site
            if (p.isConfirmedInfectious() && p.countSites() > 1) { 
                // Print the number of visited sites, patient ID, and patient name
                System.out.println(p.countSites() + "\t" + p.getPerson().getPersonId()
                        + "\t" + p.getPerson().getName());

            }
        }
    }

    /*
     * Q3: Is there a way to show the number of infection incidents by site. How
     * diagnosed incidents are trending by site?
     * Answer: We define a method to print 5 data by sites
     * 1.incidents total
     * 2.confirmed incidents total
     * 3.infectious incidents(confirmed) total
     * 4.other
     * 5.infectious rate
     */
    public void diagnosisReport() {
        // Print report header
        System.out.println(
                "=====================================================Diagnosis Report by Site and Month============================================================");

        // get all visited sites and all date so far
        ArrayList<Site> sites = eventschedule.getSiteList();
        ArrayList<String> dates = eventschedule.getDateList();
        NumberFormat percentFormat = NumberFormat.getPercentInstance();

        // Iterate over each date
        for (String d : dates) {
            System.out.println("Month: " + d + "  Location: " + locationlist.getLocations().get(0).getName());
            System.out.println(
                    "\t\tIncidents\tConfirmed\tInfectious\tOther\tInfectious rate(Infections/Total Incidents)\tReferrals to ThirdPartyService");

            // Iterate over each site
            for (Site s : sites) {
                // Get all events for the current site and date
                ArrayList<Event> events = eventschedule.getEventsBySiteAndDate(s, d);

                // Count the number of incidents, confirmed encounters, confirmed infectious
                // encounters, and other confirmed encounters
                int incidents = eventschedule.countIncidents(events);
                int confirmed = eventschedule.countConfirmedEncounters(events);
                int infectious = eventschedule.countConfirmedInfectious(events);
                int others = confirmed - infectious;
                // Calculate the infectious rate (infections / total incidents)
                String infectiousRate = percentFormat.format((double) infectious / incidents);
                // get reterral number
                int referrals = eventschedule.countReferral(events);
                // Print the counts and infectious rate for the current site
                System.out.println(s.getName() + "\t" + incidents + "\t\t" + confirmed + "\t\t" +
                        infectious + "\t\t" + others + "\t\t" + infectiousRate + "\t\t\t\t\t" + referrals);
            }
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    /*
     * Q3: How diagnosed incidents are trending by sites?
     * Answer: we define a method to print infection rate and total infection
     * incidents by site and by month
     */
    public void trendingReport() {
        // Initialize a NumberFormat instance to format the infection rate as a
        // percentage
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        // Get the list of sites and dates from the event schedule
        ArrayList<Site> sites = eventschedule.getSiteList();
        ArrayList<String> dates = eventschedule.getDateList();
        // Print report header
        System.out.println(
                "=============Diagnosis Trend by Site and Month(show numbers and rates at the same time)=============");
        System.out.println("Month\t\t2023-01\t\t2023-02\t\t2023-03");
        // Iterate over each site in the site list
        for (Site s : sites) {
            ArrayList<Integer> infectiousHistory = new ArrayList<>();
            ArrayList<String> infRateHistoy = new ArrayList<>();

            // Iterate over each date in the date list
            for (String d : dates) {

                // Get the events at the site on the given date and count the number of
                // confirmed infectious incidents
                ArrayList<Event> events = eventschedule.getEventsBySiteAndDate(s, d);
                int infectiousCount = eventschedule.countConfirmedInfectious(events);
                infectiousHistory.add(infectiousCount);

                // Calculate the infection rate as a percentage and add it to the infection rate
                // history list
                String infectionRate = percentFormat
                        .format((double) infectiousCount / eventschedule.countIncidents(events));
                infRateHistoy.add(infectionRate);
            }

            // Print the infection history and infection rate history for the site
            System.out.println(s.getName() + "\t" + infectiousHistory.get(0) + "|" + infRateHistoy.get(0) + "\t\t"
                    + infectiousHistory.get(1) + "|" + infRateHistoy.get(1) + "\t\t" + infectiousHistory.get(2) + "|"
                    + infRateHistoy.get(2));
        }
    }
}
