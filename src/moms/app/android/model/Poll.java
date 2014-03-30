package moms.app.android.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Steve on 3/30/14.
 *
 * Model class to represent a Poll in the ListView
 */
public class Poll {

    private String mainTitle;
    private String leftTitle;
    private String rightTitle;
    private Drawable leftImage;
    private Drawable rightImage;
    private Integer leftVotes;
    private Integer rightVotes;

    public Poll(String mainTitle, String leftTitle, String rightTitle, Drawable leftImage,
                Drawable rightImage, Integer leftVotes, Integer rightVotes){
        this.mainTitle = mainTitle;
        this.leftTitle = leftTitle;
        this.rightTitle = rightTitle;
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

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
    }

    public Drawable getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(Drawable leftImage) {
        this.leftImage = leftImage;
    }

    public Drawable getRightImage() {
        return rightImage;
    }

    public void setRightImage(Drawable rightImage) {
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

    public void incLeftVotes(){
        leftVotes = leftVotes + 1;
    }

    public void incRightVotes(){
        rightVotes = rightVotes + 1;
    }
}
