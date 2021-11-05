package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Writer {
    private int id;
    private String firstName;
    private String lastName;
    private List<Post> posts = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder postIds = new StringBuilder();
        for(Post post : posts){
            postIds.append(post.getId()).append("\n");
        }
        return  "id:" + id + "\n" +
                "name: " + firstName + " " + lastName + "\n" +
                "posts id:\n" + postIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && firstName.equals(writer.firstName) && lastName.equals(writer.lastName) && posts.equals(writer.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
