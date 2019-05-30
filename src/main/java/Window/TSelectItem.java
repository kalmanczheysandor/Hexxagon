package Window;

import Support.TItem;

/**
 * Extended implementation of {@code IItem} used typically in select box graphical components.
 * The class extends the general purpose {@code TItem} class.
 * In one item instance the key associated to both the value of the item and the caption
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class TSelectItem extends TItem {

    /**
     * Caption of the selectable item.
     */
    private String caption;

    /**
     * Creates a {@code TSelectItem} instance.
     *
     * @param key Unique key of the item.
     */
    public TSelectItem(int key) {
        super(key);
    }

    /**
     * Creates a {@code JFXPage} instance.
     *
     * @param key     Unique key of the item.
     * @param value   Value of the item.
     * @param caption Caption of the item.
     */
    public TSelectItem(int key, Object value, String caption) {
        super(key, value);
        this.caption = caption;
    }

    /**
     * Retrieves the caption of the item.
     *
     * @return String object.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the caption of the item.
     *
     * @param caption The content of the caption.
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

}
