package service;

public enum Requests {
    //label requests
    GET_ALL_LABELS("select * from labels"),
    GET_LABEL_BY_ID("select name from labels where id = ?"),
    GET_LAST_LABEL("SELECT * FROM labels ORDER BY ID DESC LIMIT 1"),
    CREATE_NEW_LABEL("insert into labels(name) values (?)"),
    UPDATE_LABEL("update labels set name = ? where id = ?"),
    REMOVE_LABEL("delete from labels where id = ?"),
    REMOVE_LABEL_FROM_POST_LABELS("delete from posts_labels where label_id = ?"),

    //post requests
    GET_ALL_POSTS("select * from posts"),
    GET_POST_BY_ID("select * from posts where id = ?"),
    GET_LAST_POST("SELECT * FROM posts ORDER BY ID DESC LIMIT 1"),
    GET_ALL_POST_LABELS("select label_id from posts_labels where post_id = ?"),
    CREATE_NEW_POST("insert into posts(content, created, updated, postStatus) VALUES (?,?,?,?)"),
    ADD_POST_LABEL("insert into posts_labels values(?, ?)"),
    UPDATE_POST_BY_ID("update posts set content = ?, updated = ?, postStatus = ? where id = ?"),
    REMOVE_POST("delete from posts where id = ?"),
    REMOVE_POST_FROM_POST_LABELS("delete from posts_labels where post_id = ?"),
    REMOVE_POST_FROM_WRITERS_POSTS("delete from writers_posts where post_id = ?"),
    REMOVE_POST_LABELS("delete from posts_labels where post_id = ?"),

    //writer requests
    GET_ALL_WRITERS("select * from writers"),
    GET_ALL_WRITERS_POSTS("select post_id from writers_posts where writer_id = ?"),
    GET_WRITER_BY_ID("select * from writers where id = ?"),
    GET_LAST_WRITER("select * from writers order by id desc limit 1"),
    CREATE_NEW_WRITER("insert into writers(firstName, lastName) values(?,?)"),
    ADD_WRITER_POST("insert into writers_posts values(?, ?)"),
    UPDATE_WRITER("update writers set firstName = ?, lastName = ? where id = ?"),
    REMOVE_WRITER_POSTS_FROM_WRITERS_POSTS("delete from writers_posts where writer_id = ?"),
    REMOVE_WRITER("delete from writers where id = ?")
    ;

    private final String request;

    Requests(String s) {
        request = s;
    }

    public String toString() {
        return this.request;
    }

}
