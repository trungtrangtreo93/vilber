package trunggiaothuy.vilber.model;

/**
 * Created by Administrator on 03/04/2017.
 */

public class PathStorage {

    private String uri;
    private boolean isClick;

    public PathStorage(String uri, boolean isClick) {
        this.uri = uri;
        this.isClick = isClick;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
