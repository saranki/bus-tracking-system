package com.technobytes.bustrackingsystem.data;

/**
 * Created by Sara on 12/23/2018.
 */
public class Driver {
    String busNo, password;

    public Driver() {
    }

    public Driver(String BusNo, String Password) {
        this.busNo = BusNo;
        this.password = Password;
    }

    public void setBusNo(String BusNo) {
        this.busNo = BusNo;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "busNo='" + busNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
