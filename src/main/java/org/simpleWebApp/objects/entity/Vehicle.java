package org.simpleWebApp.objects.entity;

public class Vehicle {
    private long vehicleId;
    private String title;
    public long getVehicleId(){
        return vehicleId;
    }
    public void setVehicleId(long vehicleId){
        this.vehicleId = vehicleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
