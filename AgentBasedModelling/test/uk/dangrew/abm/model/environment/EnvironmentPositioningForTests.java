package uk.dangrew.abm.model.environment;

/**
 * {@link EnvironmentPositioningForTests} exposes some {@link EnvironmentPosition} construction
 * for testing purposes.
 */
public class EnvironmentPositioningForTests {

   /**
    * Constructs an {@link EnvironmentPosition} at the given vertical and horizontal positions.
    * @param v the vertical position.
    * @param h the horizontal position.
    * @return the {@link EnvironmentPosition}.
    */
   public static EnvironmentPosition environmentPosition( int v, int h ) {
      return new EnvironmentPosition( v, h );
   }//End Method
   
   /**
    * Method to offset the given {@link EnvironmentPosition} by the given offsets.
    * @param v the vertical offset.
    * @param h the horizontal offset.
    * @return the offset {@link EnvironmentPosition}.
    */
   public static EnvironmentPosition translate( EnvironmentPosition position, int v, int h ) {
      return new EnvironmentPosition( position.vertical() + v, position.horizontal() + h );
   }//End Method
}//End Class
