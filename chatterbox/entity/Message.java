
package chatterbox.entity;
import java.util.Date;

public class Message {
    private int id;
    private String content;
    private User author;
    private Date timestamp;

    public Message(int id, String content, User author, Date timestamp) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public User getAuthor() { return author; }
    public Date getTimestamp() { return timestamp; }
}
