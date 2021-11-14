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
    GET_ALL_POSTS("""
            select posts.id, content, created, updated, postStatus, l.id, name from posts
            left join posts_labels pl on posts.id = pl.post_id
            left join labels l on l.id = pl.label_id;"""),
    GET_POST_BY_ID("""
            select posts.id, content, created, updated, postStatus, l.id, name from posts
            left join posts_labels pl on posts.id = pl.post_id
            left join labels l on l.id = pl.label_id
            where posts.id = ?;"""),
    CREATE_NEW_POST("insert into posts(content, created, updated, postStatus) VALUES (?,?,?,?)"),
    ADD_POST_LABEL("insert into posts_labels values(?, ?)"),
    UPDATE_POST_BY_ID("update posts set content = ?, updated = ?, postStatus = ? where id = ?"),
    REMOVE_POST("delete from posts where id = ?"),
    REMOVE_POST_FROM_POST_LABELS("delete from posts_labels where post_id = ?"),
    REMOVE_POST_FROM_WRITERS_POSTS("delete from writers_posts where post_id = ?"),
    REMOVE_POST_LABELS("delete from posts_labels where post_id = ?"),

    //writer requests
    GET_ALL_WRITERS("""
            select id, firstName, lastName, post_id from writers
            left join writers_posts wp on writers.id = wp.writer_id"""),
    GET_WRITER_BY_ID("""
            select id, firstName, lastName, post_id from writers
            left join writers_posts wp on writers.id = wp.writer_id
            where id = ?"""),
    CREATE_NEW_WRITER("insert into writers(firstName, lastName) values(?,?)"),
    ADD_WRITER_POST("insert into writers_posts values(?, ?)"),
    UPDATE_WRITER("update writers set firstName = ?, lastName = ? where id = ?"),
    REMOVE_WRITER_POSTS_FROM_WRITERS_POSTS("delete from writers_posts where writer_id = ?"),
    REMOVE_WRITER("delete from writers where id = ?");
    private final String request;

    Requests(String s) {
        request = s;
    }

    public String toString() {
        return this.request;
    }

}
