package service;

public enum Requests {
    //label requests
    GET_ALL_LABELS("select * from labels"),
    GET_LABEL_BY_ID("select name from labels where id = ?"),
    GET_LAST_LABEL("SELECT * FROM labels ORDER BY ID DESC LIMIT 1"),
    CREATE_NEW_LABEL("insert into labels(name) values (?)"),
    UPDATE_LABEL("update labels set name = ? where id = ?"),
    REMOVE_LABEL("delete from labels where id = ?"),
    REMOVE_LABEL_FROM_POSTS("delete from posts_labels where label_id = ?"),

    //post requests
    GET_ALL_POSTS("select * from posts"),
    GET_POST_BY_ID("select * from posts where id = ?"),
    GET_LAST_POST("SELECT * FROM posts ORDER BY ID DESC LIMIT 1"),
    CREATE_NEW_POST("insert into posts(content, created, updated, postStatus) VALUES (?,?,?,?)"),
    ADD_POST_LABEL("insert into posts_labels values(?, ?)");

    private final String request;

    Requests(String s) {
        request = s;
    }

    public String toString() {
        return this.request;
    }

}
