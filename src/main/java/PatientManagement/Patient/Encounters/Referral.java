package PatientManagement.Patient.Encounters;

import PatientManagement.Catalogs.ThirdPartyService;

public class Referral {
    ThirdPartyService thirdPartyService;

    Referral(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

    @Override
    public String toString() {
        return "type: " + thirdPartyService.getType() + "  name: " + thirdPartyService.getName();
    }

}
