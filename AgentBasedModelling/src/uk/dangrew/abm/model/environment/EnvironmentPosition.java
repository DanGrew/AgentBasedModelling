package uk.dangrew.abm.model.environment;

/**
 * The {@link EnvironmentPosition} represents a specific location in the {@link Environment}.
 */
public class EnvironmentPosition extends ArbitraryPosition {

   /**
    * Constructs a new {@link EnvironmentPosition}.
    * @param vertical the vertical grid reference, the row.
    * @param horizontal the horizontal grid reference, the column.
    */
   EnvironmentPosition( int vertical, int horizontal ) {
      super( vertical, horizontal );
   }//End Constructor

}//End Class
