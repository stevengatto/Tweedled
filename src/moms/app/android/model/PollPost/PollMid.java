package moms.app.android.model.PollPost;

/**
 * Created by Steve on 4/9/14.
 */
public class PollMid {

    private String utf8 = "/u2713";
    private String authenticityToken = "";
    private PollInner poll;
    private String commit = "submit";
    private String controller = "polls";
    private String action = "create";

    public void setPoll(PollInner poll) {
        this.poll = poll;
    }
}
