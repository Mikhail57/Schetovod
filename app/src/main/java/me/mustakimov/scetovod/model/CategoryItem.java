package me.mustakimov.scetovod.model;

/**
 * Created by mikhail on 22/05/16.
 */
public class CategoryItem {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    private String title;
    private String description;
    private int id;
    private boolean deleted;
}
