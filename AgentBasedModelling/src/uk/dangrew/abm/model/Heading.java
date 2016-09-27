package uk.dangrew.abm.model;

/**
 * The {@link Heading} represents the velocity movement the {@link Agent} with attempt to make
 * if left to move.
 */
public class Heading {
   
   private final int horizontalVelocity;
   private final int verticalVelocity;
   
   /**
    * Constructs a new {@link Heading}.
    * @param verticalVelocity the vertical velocity.
    * @param horizontalVelocity the horizontal velocity.
    */
   public Heading( int verticalVelocity, int horizontalVelocity ) {
      this.verticalVelocity = verticalVelocity;
      this.horizontalVelocity = horizontalVelocity;
   }//End Constructor
   
   /**
    * Access to the horizontal velocity.
    * @return the velocity.
    */
   public int horizontalVelocity() {
      return horizontalVelocity;
   }//End Method

   /**
    * Access to the vertical velocity.
    * @return the velocity.
    */
   public int verticalVelocity() {
      return verticalVelocity;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + horizontalVelocity;
      result = prime * result + verticalVelocity;
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
      if ( !( obj instanceof Heading ) ) {
         return false;
      }
      Heading other = ( Heading ) obj;
      if ( horizontalVelocity != other.horizontalVelocity ) {
         return false;
      }
      if ( verticalVelocity != other.verticalVelocity ) {
         return false;
      }
      return true;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return "Heading: v( " + verticalVelocity + "), h( " + horizontalVelocity + " )";
   }//End Method
   
}//End Class
