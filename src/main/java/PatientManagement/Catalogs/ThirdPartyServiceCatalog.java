package PatientManagement.Catalogs;

import java.util.ArrayList;

import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Site;

public class ThirdPartyServiceCatalog {
    Clinic clinic;
    ArrayList<ThirdPartyService> thirdPartyServices;

    public ArrayList<ThirdPartyService> getThirdPartyServices() {
        return thirdPartyServices;
    }

    public ThirdPartyServiceCatalog(Clinic c) {
        clinic = c;
        thirdPartyServices = new ArrayList<ThirdPartyService>();
    }

    public ThirdPartyService newThirdPartyService(String t, String n, Site s) {
        ThirdPartyService tps = new ThirdPartyService(t, n, s);
        thirdPartyServices.add(tps);
        return tps;
    }

    public int countThirdPartyService(String type, Site site) {
        int count = 0;
        for (ThirdPartyService t : thirdPartyServices) {
            if (t.type.equals(type) && t.site == site) {
                count++;
            }
        }
        return count;
    }


    /* 
    This method prints the third party services information for each site in the clinic.
    It counts the number of each type of third party service (pharmacy, mental health, and physical therapy)
    available at each site and prints the information in a table format.
    */
    public void printInfo() {
        ArrayList<Site> sites = clinic.getSiteCatalog().getSites();
        // Print the header of the table
        System.out.println("========================Third Party Services Info========================");
        System.out.println("\t\t\tPharmarcy\tMentalHealth\tPhysicalTherapy");

        // Iterate through each site in the list
        for (Site s : sites) {
            // Count the number of each type of third party service available at the site
            System.out.println(s.getName() + ":\t\t" + countThirdPartyService("PM", s) + "\t\t"
                    + countThirdPartyService("MH", s) +"\t\t"+ countThirdPartyService("PT", s));
        }
    }

}
