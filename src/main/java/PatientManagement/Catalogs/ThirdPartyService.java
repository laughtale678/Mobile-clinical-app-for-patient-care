package PatientManagement.Catalogs;

import PatientManagement.Clinic.Site;

public class ThirdPartyService {
    String type;
    String name;
    Site site;

    public String getName() {
        return name;
    }

    public Site getSite() {
        return site;
    }

    ThirdPartyService() {

    }

    ThirdPartyService(String t, String n, Site s) {
        type = t;
        name = n;
        site = s;
    }

    public String getType() {
        return type;
    }
    
    
}
