package uk.dangrew.abm.model.environment;

/**
 * The {@link EnvironmentPosition} represents a specific location in the {@link Environment}.
 */
public class EnvironmentPosition {

   private final int vertical;
   private final int horizontal;
   
   /**
    * Constructs a new {@link EnvironmentPosition}.
    * @param vertical the vertical grid reference, the row.
    * @param horizontal the horizontal grid reference, the column.
    */
   EnvironmentPosition( int vertical, int horizontal ) {
      this.vertical = vertical;
      this.horizontal = horizontal;
   }//End Constructor

   /**
    * Access to the row of the grid.
    * @return the row.
    */
   public int vertical() {
      return vertical;
   }//End Method

   /**
    * Access to the column of the grid.
    * @return the column.
    */
   public int horizontal() {
      return horizontal;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + vertical;
      result = prime * result + horizontal;
      return result;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean equals( Object obj ) {
      if ( this == obj ) {
         return true;
      }
      if ( obj == null ) {
         return false;
      }
      if ( !( obj instanceof EnvironmentPosition ) ) {
         return false;
      }
      EnvironmentPosition other = ( EnvironmentPosition ) obj;
      if ( vertical != other.vertical ) {
         return false;
      }
      if ( horizontal != other.horizontal ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return "Location: " + vertical + ", " + horizontal;
   }//End Method
   
}//End Class
