package moms.app.android.model.testing;

/**
 * Created by Steve on 4/5/14.
 */
public class Comment {

    private String username;
    private String body;

    public Comment(String username, String body){
        this.username = username;
        this.body = body;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

}
