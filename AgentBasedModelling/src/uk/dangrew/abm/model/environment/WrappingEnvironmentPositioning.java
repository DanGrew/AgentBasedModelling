package uk.dangrew.abm.model.environment;

import uk.dangrew.abm.model.agent.Heading;

/**
 * {@link WrappingEnvironmentPositioning} provides {@link EnvironmentPositioning} where moving
 * outside the boundaries of the {@link Environment} wrap around to the opposite point on the {@link Environment}.
 */
class WrappingEnvironmentPositioning implements EnvironmentPositioning {

   private Environment environment;
   
   /**
    * Constructs a new {@link WrappingEnvironmentPositioning}.
    */
   WrappingEnvironmentPositioning() {}
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( Environment environment ) {
      if ( this.environment != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.environment = environment;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition locate( int vertical, int horizontal ) {
      int wrappedV = vertical % environment.height();
      if ( wrappedV < 0 ) {
         wrappedV += environment.height();
      }
      int wrappedH = horizontal % environment.width();
      if ( wrappedH < 0 ) {
         wrappedH += environment.width();
      }
      return new EnvironmentPosition( wrappedV, wrappedH );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition horizontalPositionOffset( EnvironmentPosition original, int offset ) {
      return locate( original.vertical(), original.horizontal() + offset );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition verticalPositionOffset( EnvironmentPosition original, int offset ) {
      return locate( original.vertical() + offset, original.horizontal() );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition translate( EnvironmentPosition original, Heading heading ) {
      return locate( original.vertical() + heading.verticalVelocity(), original.horizontal() + heading.horizontalVelocity() );
   }//End Method

}//End Class
