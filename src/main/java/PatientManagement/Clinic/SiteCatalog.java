/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Clinic;

import java.util.ArrayList;

import javax.print.event.PrintEvent;

/**
 *
 * @author kal bugrara
 */
public class SiteCatalog {

    ArrayList<Site> sites;

    public ArrayList<Site> getSites() {
        return sites;
    }

    public SiteCatalog() {
        sites = new ArrayList<Site>();
    }

    public Site newSite(String n, Location l) {
        Site s = new Site(n,l);
        sites.add(s);
        return s;
    }

    public void printInfo(){
        System.out.println("==========Site Catalog==========");
        System.out.println("Location\tSite");
        for(Site s : sites) {
            System.out.println(s.getLocation().getName() + "\t\t" + s.name);
        }
    }
}
