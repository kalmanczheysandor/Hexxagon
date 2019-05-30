package Support;

/**
 * Represents two dimensional coordinates.
 *
 * @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public class TCoordinate2D {

    /**
     * Stores the x coordinate.
     */
    private int x;
    /**
     * Stores the y coordinate.
     */
    private int y;

    /**
     * Creates a {@code TCoordinate2D} instance.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public TCoordinate2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the X coordinate.
     *
     * @return An {@code int } value.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the Y coordinate.
     *
     * @return An {@code int } value.
     */
    public int getY() {
        return y;
    }

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
        final TCoordinate2D other = (TCoordinate2D) obj;
        if(this.x != other.x) {
            return false;
        }
        if(this.y != other.y) {
            return false;
        }
        return true;
    }
}
