package com.caseynguyen.p02b;


public class Congress {

    private String Name ;
    private int Pic ;
    private String CongressType ;
    private String Party;
    private String email;
    private String website;
    private String LastTweet;
    private String EndOfTerm;
    private String Committee;
    private String BillSponsored;

    public Congress(String name, int pic, String congressType, String party,
                    String email, String website,
                    String lastTweet, String endOfTerm, String committee,
                    String billSponsored) {
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

    public int getPic() {
        return Pic;
    }

    public void setPic(int pic) {
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

    public String getCommittee() {
        return Committee;
    }

    public void setCommittee(String committee) {
        Committee = committee;
    }

    public String getBillSponsored() {
        return BillSponsored;
    }

    public void setBillSponsored(String billSponsored) {
        BillSponsored = billSponsored;
    }
}
