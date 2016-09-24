package uk.dangrew.abm.model;

/**
 * The {@link EnvironmentPosition} represents a specific location in the {@link Environment}.
 */
public class EnvironmentPosition {

   private final int x;
   private final int y;
   
   /**
    * Constructs a new {@link EnvironmentPosition}.
    * @param x the x grid reference, the row.
    * @param y the y grid reference, the column.
    */
   public EnvironmentPosition( int x, int y ) {
      this.x = x;
      this.y = y;
   }//End Constructor

   /**
    * Access to the row of the grid.
    * @return the row.
    */
   public int x() {
      return x;
   }//End Method

   /**
    * Access to the column of the grid.
    * @return the column.
    */
   public int y() {
      return y;
   }//End Method
   
   /**
    * Method to construct a horizontal shift of this {@link EnvironmentPosition}.
    * @param offset the horizontal offset.
    * @return the {@link EnvironmentPosition} of this accounting for the offset.
    */
   public EnvironmentPosition horizontal( int offset ) {
      return new EnvironmentPosition( x, y + offset );
   }//End Method
   
   /**
    * Method to construct a vertical shift of this {@link EnvironmentPosition}.
    * @param offset the vertical offset.
    * @return the {@link EnvironmentPosition} of this accounting for the offset.
    */
   public EnvironmentPosition vertical( int offset ) {
      return new EnvironmentPosition( x + offset, y );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
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
      if ( x != other.x ) {
         return false;
      }
      if ( y != other.y ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return "Location: " + x + ", " + y;
   }//End Method
   
}//End Class
