package com.cs160.joleary.catnip;


import java.util.ArrayList;

public class Congress {

    private String Name ;
    private String Pic ;
    private String CongressType ;
    private String Party;
    private String email;
    private String website;
    private String LastTweet;
    private String EndOfTerm;
    private ArrayList<CommiteeObj> Committee;
    private ArrayList<BillSponsoredObj> BillSponsored;

    public Congress(String name, String pic, String congressType, String party,
                    String email, String website,
                    String lastTweet, String endOfTerm, ArrayList<CommiteeObj> committee,
                    ArrayList<BillSponsoredObj> billSponsored) {
        Name = name;
        Pic = pic;
        CongressType = congressType;
        Party = party;
        this.email = email;
        this.website = website;
        LastTweet = lastTweet;
        EndOfTerm = endOfTerm;
        Committee = committee;
        BillSponsored = billSponsored;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getCongressType() {
        return CongressType;
    }

    public void setCongressType(String congressType) {
        CongressType = congressType;
    }

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLastTweet() {
        return LastTweet;
    }

    public void setLastTweet(String lastTweet) {
        LastTweet = lastTweet;
    }

    public String getEndOfTerm() {
        return EndOfTerm;
    }

    public void setEndOfTerm(String endOfTerm) {
        EndOfTerm = endOfTerm;
    }

    public ArrayList<CommiteeObj> getCommittee() {
        return Committee;
    }

    public void setCommittee(ArrayList<CommiteeObj> committee) {
        Committee = committee;
    }

    public ArrayList<BillSponsoredObj> getBillSponsored() {
        return BillSponsored;
    }

    public void setBillSponsored(ArrayList<BillSponsoredObj> billSponsored) {
        BillSponsored = billSponsored;
    }
}
