package me.mustakimov.scetovod.model;

/**
 * Created by mikhail on 29/02/16.
 */

import android.support.annotation.NonNull;

public class PurchaseItem implements Comparable<PurchaseItem> {

    // Has been deleted this item?
    private boolean deleted = false;

    // Title ans description
    private String title = "";
    private String description = "";

    // Count and price
    private double count = 0;
    private double price = 0;

    // Id of category purchase assigned to
    private long categoryId = 0;

    // Date
    private long date = 0;

    private int id = 0;

    @Override
    public int compareTo(@NonNull PurchaseItem another) {
        return this.date < another.date ? -1 :
                (this.date > another.date ? 1 : 0);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = (deleted != 0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
