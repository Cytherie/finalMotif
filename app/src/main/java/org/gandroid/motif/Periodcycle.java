package org.gandroid.motif;


public class Periodcycle {

    private String LPeriod;
    private String Cdays;
    private String DEnded;


    public Periodcycle(String LP, String CD, String DE){
        LPeriod = LP;
        Cdays = CD;
        DEnded = DE;

    }

    public String getLPeriod() {return LPeriod;}

    public String getCdays() {
        return Cdays;
    }

    public String getDEnded() {
        return DEnded;
    }
}
