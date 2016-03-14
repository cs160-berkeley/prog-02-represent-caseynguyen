package com.cs160.joleary.catnip;

/**
 * Created by congnguyen on 3/10/16.
 */
public class BillSponsoredObj {
    String last_action_at;
    String short_title;

    public BillSponsoredObj(String last_action_at, String short_title) {
        this.last_action_at = last_action_at;
        this.short_title = short_title;
    }

    public String getLast_action_at() {
        return last_action_at;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public void setLast_action_at(String last_action_at) {
        this.last_action_at = last_action_at;
    }
}
