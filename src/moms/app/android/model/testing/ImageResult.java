package moms.app.android.model.testing;

/**
 * Object to store url and thumbnail url of images from google image search
 */
public class ImageResult {

    private String Url;
    private String ThumbUrl;

    public String getUrl() { return Url; }

    public void setUrl(String url) { Url = url; }

    public String getThumbUrl() { return ThumbUrl; }

    public void setThumbUrl(String thumbUrl) { ThumbUrl = thumbUrl; }
}
