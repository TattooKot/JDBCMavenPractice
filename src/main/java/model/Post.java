package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private String content;
    private Date created;
    private Date updated;
    private PostStatus status;
    private List<Label> labels = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && content.equals(post.content) && created.equals(post.created) && updated.equals(post.updated) && status == post.status && Objects.equals(labels, post.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, status, labels);
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "content: \n" + content +
                "================\n" +
                "created: " + created + "\n" +
                "updated: " + updated + "\n" +
                "status: " + status + "\n" +
                "labels:\n" + labels + "\n" +
                "==================================\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
