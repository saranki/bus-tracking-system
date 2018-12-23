package com.technobytes.bustrackingsystem.data;

/**
 * Created by Sara on 12/23/2018.
 */
public class Driver {
    String BusNo, Password;

    public Driver(String BusNo, String Password) {
        this.BusNo = BusNo;
        this.Password = Password;
    }
    public void setBusNo(String BusNo)
    {
        this.BusNo=BusNo;
    }

    public String getBusNo() {
        return BusNo;
    }

    public void setPassword(String Password)
    {
        this.Password=Password;
    }

    public String getPassword()
    {
        return Password;
    }
}
