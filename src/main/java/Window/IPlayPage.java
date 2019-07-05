package Window;

import Hexxagon.View.IInfoBoard;
import Hexxagon.View.IPlayBoard;
import Hexxagon.View.IFaceBoard;

/**
 * Contains method specifications of play page of application.
 *
* @author Sandor Kalmanchey
 * @version 1.0
 * @since 1.0
 */
public interface IPlayPage {

    /**
     * Retrieves the so called play board graphical component of Hexxagon module.
     * @return Reference to the object of component node.
     */
    public IPlayBoard getPlayBoard();

    /**
     * Retrieves the so called info board graphical component of Hexxagon module.
     * @return Reference to the object of component node.
     */
    public IInfoBoard getInfoBoard();

     /**
     * Retrieves the so called face graphical component of Hexxagon module.
     * @return Reference to the object of component node.
     */
     public IFaceBoard getFaceBoard();
    
}
