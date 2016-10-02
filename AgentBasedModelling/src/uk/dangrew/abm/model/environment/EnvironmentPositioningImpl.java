package uk.dangrew.abm.model.environment;

import uk.dangrew.abm.model.agent.Heading;

/**
 * {@link EnvironmentPositioningImpl} provides a basic method of constructing {@link EnvironmentPosition}s at the
 * coordinates given, resulting in the width and height providing explicit boundaries.
 */
public class EnvironmentPositioningImpl implements EnvironmentPositioning {
   
   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition locate( int vertical, int horizontal ) {
      return new EnvironmentPosition( vertical, horizontal );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition horizontalPositionOffset( EnvironmentPosition original, int offset ) {
      return new EnvironmentPosition( original.vertical(), original.horizontal() + offset );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition verticalPositionOffset( EnvironmentPosition original, int offset ) {
      return new EnvironmentPosition( original.vertical() + offset, original.horizontal() );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition translate( EnvironmentPosition original, Heading heading ) {
      return new EnvironmentPosition( original.vertical() + heading.verticalVelocity(), original.horizontal() + heading.horizontalVelocity() );
   }//End Method
   
}//End Class
