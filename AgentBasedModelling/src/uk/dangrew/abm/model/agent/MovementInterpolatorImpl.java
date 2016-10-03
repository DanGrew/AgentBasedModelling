package uk.dangrew.abm.model.agent;

import uk.dangrew.abm.algorithm.GridInterpolation;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * The {@link MovementInterpolatorImpl} is responsible for interpolating a general {@link Heading}
 * into a collision detecting movement.
 */
class MovementInterpolatorImpl implements MovementInterpolator {
   
   private ControllableAgent agent;
   
   /**
    * Constructs a new {@link MovementInterpolatorImpl}. 
    */
   public MovementInterpolatorImpl(){} 
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( ControllableAgent agent ) {
      if ( this.agent != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.agent = agent;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public boolean move( Environment environment ) {
      final int initialVertical = agent.position().get().vertical();
      final int initialHorizontal = agent.position().get().horizontal();
      final int targetVertical = initialVertical + agent.heading().get().verticalVelocity();
      final int targetHorizontal = initialHorizontal + agent.heading().get().horizontalVelocity();
      
      GridInterpolation interpolation = new GridInterpolation( ( v, h ) -> interpolate( environment, v, h ) );
      return interpolation.interpolate( initialVertical, initialHorizontal, targetVertical, targetHorizontal );
   }//End Method
   
   /**
    * Method to interpolate a single movement to the given position.
    * @param environment the {@link Environment} to move in.
    * @param vertical the new vertical location.
    * @param horizontal the new horizontal location.
    * @return true if this position collides with something.
    */
   private boolean interpolate( Environment environment, int vertical, int horizontal ) {
      System.out.println( "checking: " + vertical + ", " + horizontal );
      EnvironmentPosition position = environment.locate( vertical, horizontal );
      if ( agent.position().get().equals( position ) ) {
         return false;
      }
      if ( !environment.isAvailable( position ) ) {
         return true;
      }
      agent.setPosition( position );
      return false;
   }//End Method
   
}//End Class
