package org.gandroid.motif;

public class CycleEvents {
    private String NPD;
    private String FD;
    private String OD;

    public CycleEvents(String NPDates, String FDates, String ODate){
        NPD=NPDates;
        FD=FDates;
        OD=ODate;
    }

    public String getNPD() {
        return NPD;
    }

    public String getFD() {
        return FD;
    }

    public String getOD() {
        return OD;
    }
}
