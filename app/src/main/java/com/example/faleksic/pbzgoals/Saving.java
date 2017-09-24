package com.example.faleksic.pbzgoals;

import java.util.Date;

public class Saving {
    private String title, dateEnd;
    private int id, category, frequency;
    private double amount, paid;
    private boolean active;

    public Saving() {
    }

    public Saving(int id, String title, String dateEnd, double amount, int frequency, int category,  double paid, boolean active) {
        this.id = id;
        this.title = title;
        this.dateEnd = dateEnd;
        this.amount = amount;
        this.frequency = frequency;
        this.category = category;
        this.paid = paid;
        this.active = active;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCategory() {
        int id = R.drawable.ic_9;
        switch (category) {
            case 1:
                id = R.drawable.ic_0;
                break;
            case 2:
                id = R.drawable.ic_1;
                break;
            case 3:
                id = R.drawable.ic_2;
                break;
            case 4:
                id = R.drawable.ic_3;
                break;
            case 5:
                id = R.drawable.ic_4;
                break;
            case 6:
                id = R.drawable.ic_5;
                break;
            case 7:
                id = R.drawable.ic_6;
                break;
            case 8:
                id = R.drawable.ic_7;
                break;
            case 9:
                id = R.drawable.ic_8;
                break;
            case 10:
                id = R.drawable.ic_9;
                break;
        }
        return id;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPaid() {
        return (int)((paid/amount)*100);
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
