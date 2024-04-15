package com.example.firstapp.model;
import java.util.Date;
import java.util.List;


public class Event {
    private int id;
    private String name;
    private Date dateCreate;
    private Date beginTime;
    private Date endTime;
    private int capacity;

    private String description;

    private int status;
    private User user;


    private List<EventJob> listEventJobs;
    private AddressOrganization addressOrganization;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<EventJob> getListEventJobs() {
        return listEventJobs;
    }

    public void setListEventJobs(List<EventJob> listEventJobs) {
        this.listEventJobs = listEventJobs;
    }

    public AddressOrganization getAddressOrganization() {
        return addressOrganization;
    }

    public void setAddressOrganization(AddressOrganization addressOrganization) {
        this.addressOrganization = addressOrganization;
    }
}
