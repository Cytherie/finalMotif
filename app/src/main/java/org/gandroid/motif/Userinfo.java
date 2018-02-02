package org.gandroid.motif;

public class Userinfo {

    private String Username;
    private  String Plength;

    public Userinfo(String username,String periodlength){
        Username= username;
        Plength = periodlength;
    }

    public String getUsername() {
        return Username;
    }

    public String getPlength() {
        return Plength;
    }
}
