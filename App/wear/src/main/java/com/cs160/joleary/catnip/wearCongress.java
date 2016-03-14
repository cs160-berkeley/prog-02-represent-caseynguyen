package com.cs160.joleary.catnip;


public class wearCongress {
    private String Name;
    private String Party;
    private int Party_icon;

    public wearCongress(String name, String party, int party_icon) {
        Name = name;
        Party = party;
        Party_icon = party_icon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }

    public int getParty_icon() {
        return Party_icon;
    }

    public void setParty_icon(int party_icon) {
        Party_icon = party_icon;
    }
}


