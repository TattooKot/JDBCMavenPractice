package model;

import java.util.Objects;

public class Label {
    private int id;
    private String name;

    public Label(String name) {
        this.name = name;
    }

    public Label(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Label() {
    }

    @Override
    public String toString() {
        return "Tag: " + id +
                ", " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id && name.equals(label.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

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
}
