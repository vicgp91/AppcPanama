package appcpanama.logicstudio.net.appcpanama.viewitem;

/**
 * Created by LogicStudio on 09/10/2015.
 */
public class GridViewItem {

    public String text;
    public String imgSrc;
    public int image;
    public int id;

    public GridViewItem() {
    }
    public GridViewItem(Integer image, String text) {
        this.image = image;
        this.text = text;
    }
}
