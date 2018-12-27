package com.technobytes.bustrackingsystem.data;

/**
 * Created by Sara on 12/27/2018.
 */
public class Scheduler {
    String route, time;

    public Scheduler() {
    }

    public Scheduler(String route, String time) {
        this.route = route;
        this.time = time;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "route='" + route + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
