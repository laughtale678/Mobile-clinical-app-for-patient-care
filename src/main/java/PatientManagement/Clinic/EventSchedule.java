/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatientManagement.Clinic;

import java.util.ArrayList;

import PatientManagement.Patient.Encounters.Encounter;

/**
 *
 * @author kal bugrara
 */

public class EventSchedule {

    ArrayList<Event> scheduledevents;
    // collect all date data
    ArrayList<String> dateList;
    // collect all sites data
    ArrayList<Site> siteList;

    public ArrayList<Site> getSiteList() {
        return siteList;
    }

    public ArrayList<Event> getScheduledevents() {
        return scheduledevents;
    }

    public EventSchedule() {
        scheduledevents = new ArrayList<Event>();
        dateList = new ArrayList<String>();
        siteList = new ArrayList<Site>();

    }

    public Event newEvent(String date, Site site, String budgetnumer) {
        Event newevent = new Event(date, site, budgetnumer);
        // add a date to the date list if it is not already present in the list. The purpose is to avoid duplicates in the date list.
        if(!dateList.contains(date)){
            dateList.add(date);
        }
        // add a site to the site list if it is not already present in the list. The purpose is to avoid duplicates in the site list.
        if(!siteList.contains(site)){
            siteList.add(site);
        }
        scheduledevents.add(newevent);
        return newevent;
    }

    // finds all positive confirmations for all events that match the budgetnumber
    public int getEventConfirmedCasesByBudgetNumber(String budgetnumber) {
        int sum = 0;
        for (Event e : scheduledevents) {
            if (e.isMatch(budgetnumber))
                sum = sum + e.getConfirmedTotals();
        }
        return sum;
    }

    // print all event info
    public void printInfo() {
        System.out.println("====================Event Info====================");
        System.out.println("date\t\tsite\t\tencounter count");
        for (Event e : scheduledevents) {
            e.printInfo();
        }
    }

    public ArrayList<String> getDateList() {
        return dateList;
    }

    
    public ArrayList<Event> getEventsBySiteAndDate(Site s, String d) {
        ArrayList<Event> list = new ArrayList<>();
        for(Event e : scheduledevents) {
            if(e.getSite() == s && e.getDate().equals(d)) {
                list.add(e);
            }
        }
        return list;
    }

    public int countIncidents(ArrayList<Event> events) {
        int count = 0;
        for(Event e : events) {
            count += e.getEncounters().size();
        }
        return count;
    }

    public int countConfirmedEncounters(ArrayList<Event> events) {
        int count = 0;
        for(Event e : events) {
            count += e.getConfirmedTotals();
        }
        return count;
    }

    public int countConfirmedInfectious(ArrayList<Event> events) {
        int count = 0;
        for(Event e : events) {
            count += e.getConfirmedInfectiousTotals();
        }
        return count;

    }

    public int countReferral(ArrayList<Event> events) {
        int count = 0;
        for(Event e : events) {
            for(Encounter encounter : e.getEncounters()) {
                if(encounter.getReferral() != null) {
                    count++;
                }
            }
        }
        return count;
    }

    


}