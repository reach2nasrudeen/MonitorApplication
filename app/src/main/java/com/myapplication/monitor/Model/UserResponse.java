package com.myapplication.monitor.Model;

import java.io.Serializable;

/**
 * Created by Mohamed on 05/15/2017.
 */

public class UserResponse implements Serializable {
    public boolean error;
    public Place place;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
