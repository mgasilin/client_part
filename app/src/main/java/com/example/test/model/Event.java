package com.example.test.model;


import java.util.ArrayList;

public class Event {
    private String date;
    private long id;
    private long owner_id;
    private String name;
    private String description;
    private String place;
    private double coordX;
    private double coordY;
    private boolean isStreet;
    private boolean isGroup;
    private boolean isFamily;
    private boolean isFree;
    private boolean hasCovid;
    private boolean hasRegister;
    private boolean isSport;
    private boolean hasAgeRestrictions;
    private String author;

    public Event(String date, long id, long owner_id, String name, String description, String place, double coordX, double coordY, boolean isStreet, boolean isGroup, boolean isFamily, boolean isFree, boolean hasCovid, boolean hasRegister, boolean isSport, boolean hasAgeRestrictions) {
        this.date = date;
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.coordX = coordX;
        this.coordY = coordY;
        this.isStreet = isStreet;
        this.isGroup = isGroup;
        this.isFamily = isFamily;
        this.isFree = isFree;
        this.hasCovid = hasCovid;
        this.hasRegister = hasRegister;
        this.isSport = isSport;
        this.hasAgeRestrictions = hasAgeRestrictions;
    }

    public Event(String date, long owner_id, String name, String description, String place, double coordX, double coordY, boolean isStreet, boolean isGroup, boolean isFamily, boolean isFree, boolean hasCovid, boolean hasRegister, boolean isSport, boolean hasAgeRestrictions) {
        this.date = date;
        this.owner_id = owner_id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.coordX = coordX;
        this.coordY = coordY;
        this.isStreet = isStreet;
        this.isGroup = isGroup;
        this.isFamily = isFamily;
        this.isFree = isFree;
        this.hasCovid = hasCovid;
        this.hasRegister = hasRegister;
        this.isSport = isSport;
        this.hasAgeRestrictions = hasAgeRestrictions;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public double getX() {
        return coordX;
    }

    public double getY() {
        return coordY;
    }

    public int isStreet() {
        return (isStreet ? 1 : 0);
    }

    public int isGroup() {
        return (isGroup ? 1 : 0);
    }

    public int isFamily() {
        return (isFamily ? 1 : 0);
    }

    public int isFree() {
        return (isFree ? 1 : 0);
    }

    public int isHasCovid() {
        return (hasCovid ? 1 : 0);
    }

    public int isHasRegister() {
        return (hasRegister ? 1 : 0);
    }

    public int isSport() {
        return (isSport ? 1 : 0);
    }

    public int isHasAgeRestrictions() {
        return (hasAgeRestrictions ? 1 : 0);
    }

    public ArrayList<Boolean> getCategories() {
        ArrayList<Boolean> result = new ArrayList<>();
        result.add(isStreet);
        result.add(isGroup);
        result.add(isFamily);
        result.add(isFree);
        result.add(hasCovid);
        result.add(hasRegister);
        result.add(isSport);
        result.add(hasAgeRestrictions);
        return result;
    }

    public Event(String date, long id, long owner_id, String name, String description, String place, double coordX, double coordY, boolean isStreet, boolean isGroup, boolean isFamily, boolean isFree, boolean hasCovid, boolean hasRegister, boolean isSport, boolean hasAgeRestrictions, String author) {
        this.date = date;
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.coordX = coordX;
        this.coordY = coordY;
        this.isStreet = isStreet;
        this.isGroup = isGroup;
        this.isFamily = isFamily;
        this.isFree = isFree;
        this.hasCovid = hasCovid;
        this.hasRegister = hasRegister;
        this.isSport = isSport;
        this.hasAgeRestrictions = hasAgeRestrictions;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
