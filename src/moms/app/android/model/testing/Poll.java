package moms.app.android.model.testing;

/**
 * Created by Steve on 3/30/14.
 *
 * Model class to represent a Poll in the ListView
 */
public class Poll {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String mainTitle;
    private String subTitle;
    private String leftImageURL;
    private String rightImageURL;
    private Integer leftVotes;
    private Integer rightVotes;

    public Poll(String mainTitle, String subTitle, String leftImageURL,
                String rightImageURL, Integer leftVotes, Integer rightVotes){
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.leftImageURL = leftImageURL;
        this.rightImageURL = rightImageURL;
        this.leftVotes = leftVotes;
        this.rightVotes = rightVotes;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() { return subTitle; }

    public void setSubTitle(String subTitle) { this.subTitle = subTitle; }

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
}
