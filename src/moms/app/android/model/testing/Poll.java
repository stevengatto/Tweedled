package moms.app.android.model.testing;

import com.savagelook.android.Lazy;

/**
 * Created by Steve on 3/30/14.
 *
 * Model class to represent a Poll in the ListView
 */
public class Poll {

    private int id;
    private String mainTitle;
    private String subTitleLeft;
    private String subTitleRight;
    private String leftImageURL;
    private String rightImageURL;
    private Integer leftVotes;
    private Integer rightVotes;
    private String description;

    public Poll(Integer id, String mainTitle, String subTitleLeft, String subTitleRight, String leftImageURL,
                String rightImageURL, Integer leftVotes, Integer rightVotes, String description){
        this.id = id;
        this.mainTitle = mainTitle;
        this.subTitleLeft = subTitleLeft;
        this.subTitleRight = subTitleRight;
        this.leftImageURL = leftImageURL;
        this.rightImageURL = rightImageURL;
        this.leftVotes = leftVotes;
        this.rightVotes = rightVotes;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitleLeft() { return subTitleLeft; }

    public void setSubTitleLeft(String subTitleLeft) { this.subTitleLeft = subTitleLeft; }

    public String getSubTitleRight() { return subTitleRight; }

    public void setSubTitleRight(String subTitleRight) { this.subTitleRight = subTitleRight; }

    public String getLeftImageUrl() {
        return leftImageURL;
    }

    public void setLeftImageUrl(String leftImage) {
        this.leftImageURL = leftImage;
    }

    public String getRightImageUrl() {
        return rightImageURL;
    }

    public void setRightImageUrl(String rightImage) {
        this.rightImageURL = rightImage;
    }

    public Integer getLeftVotes() {
        return leftVotes;
    }

    public void setLeftVotes(Integer leftVotes) {
        this.leftVotes = leftVotes;
    }

    public Integer getRightVotes() {
        return rightVotes;
    }

    public void setRightVotes(Integer rightVotes) {
        this.rightVotes = rightVotes;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
