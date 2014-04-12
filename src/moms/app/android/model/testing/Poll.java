package moms.app.android.model.testing;

/**
 * Created by Steve on 3/30/14.
 *
 * Model class to represent a Poll in the ListView
 */
public class Poll {

    private String mainTitle;
    private String subTitle;
    private Integer leftImage;
    private Integer rightImage;
    private Integer leftVotes;
    private Integer rightVotes;

    public Poll(String mainTitle, String subTitle, Integer leftImage,
                Integer rightImage, Integer leftVotes, Integer rightVotes){
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
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

    public Integer getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(Integer leftImage) {
        this.leftImage = leftImage;
    }

    public Integer getRightImage() {
        return rightImage;
    }

    public void setRightImage(Integer rightImage) {
        this.rightImage = rightImage;
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
