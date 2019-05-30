package Support;

/**
 * Generalised implementation of {@code IItem} used typically in array lists.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class TItem implements IItem {

    /**
     * Unique key of the item.
     */
    private int key;
    /**
     * Value object of the item.
     */
    private Object value;

    /**
     * Creates a {@code TItem} instance.
     *
     * @param key   The key value of item.
     * @param value The value associated to the key.
     */
    public TItem(int key, Object value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a {@code TItem} instance.
     *
     * @param key The key value of item.
     */
    public TItem(int key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValueAsString() {
        return (String) value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValueAsInt() {
        return Integer.valueOf((Integer) this.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAsString(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Equals if the unique indexes are equals.
     *
     * @param obj Object to compare.
     *
     * @return True if equal otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final TItem other = (TItem) obj;
        if(this.key != other.key) {
            return false;
        }
        return true;
    }

}
