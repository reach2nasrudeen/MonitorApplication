package com.myapplication.monitor.ViewModels;

import com.myapplication.monitor.Model.Place;
import com.myapplication.monitor.Model.User;
import com.myapplication.monitor.Model.UserResponse;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class RegisterBaseViewModel {
    private String userPhone;
    private Place place;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
