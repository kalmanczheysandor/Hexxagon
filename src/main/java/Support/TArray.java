/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Support;

/**
 *
 * @author ALX
 */
public class TArray {
    
    public static byte[][] copy2D(byte[][] originalArray) throws TError {
        // Input controll
        if(originalArray==null) {
            throw new TError("Parameter must not be null!");
        }
        
        //
        byte[][] newArray    = null;
        for(int i=0;i<originalArray.length;i++) {
            //
            for(int j=0;j<originalArray[i].length;j++) {
                if(newArray==null) {
                    newArray = new byte[originalArray.length][originalArray[i].length];
                }
                newArray[i][j]    = originalArray[i][j];
            }
        }
        return newArray;
    }
}
