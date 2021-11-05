package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Writer {
    private int id;
    private String firstName;
    private String secondName;
    private List<Post> posts = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder postIds = new StringBuilder();
        for(Post post : posts){
            postIds.append(post.getId()).append("\n");
        }
        return  "id:" + id + "\n" +
                "name: " + firstName + " " + secondName + "\n" +
                "posts id:\n" + postIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && firstName.equals(writer.firstName) && secondName.equals(writer.secondName) && posts.equals(writer.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, posts);
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
