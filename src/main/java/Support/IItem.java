package Support;

/**
 * Describes the general methods of key value pair in items used typically in array lists.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IItem {

    /**
     * Retrieves the key value.
     *
     * @return The value of the key.
     */
    public int getKey();

    /**
     * Retrieves the value object of the value associated to the key.
     *
     * @return Value associated to the key.
     */
    public Object getValue();

    /**
     * Retrieves the value object as {@code String} object.
     *
     * @return Value object as {@code String} object.
     */
    public String getValueAsString();

    /**
     * Sets the object of value associated to the key as a {@code String} object.
     *
     * @param value {@code String} representation of value object.
     */
    public void setValueAsString(String value);

    /**
     * Sets the object of value associated to the key.
     *
     * @param value Representation of value object.
     */
    public void setValue(Object value);

    /**
     * Retrieves the value object as {@code int} primitive type.
     *
     * @return Value object as {@code int} primitive type.
     */
    public int getValueAsInt();

    /**
     * Sets the key of the object .
     *
     * @param key {@code int} .
     */
    public void setKey(int key);
}
