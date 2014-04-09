package moms.app.android.model;

/**
 * Created by Steve on 4/9/14.
 */
public class PostPoll {

    String authToken;
    String question;
    String titleOne;
    String titleTwo;

    public void setTitleTwo(String titleTwo) {
        this.titleTwo = titleTwo;
    }

    public void setTitleOne(String titleOne) {
        this.titleOne = titleOne;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
