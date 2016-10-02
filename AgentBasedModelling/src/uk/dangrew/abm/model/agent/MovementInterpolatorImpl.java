package uk.dangrew.abm.model.agent;

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
      
      if ( initialVertical == targetVertical ) {
         return flatHorizontalInterpolation( environment, agent.position().get(), targetHorizontal );
      }
      
      if ( initialHorizontal == targetHorizontal ) {
         return flatVerticalInterpolation( environment, agent.position().get(), targetVertical );
      }
      
      if ( Math.abs( agent.heading().get().verticalVelocity() ) > Math.abs( agent.heading().get().horizontalVelocity() ) ) {
         return interpolateVertically( 
                  environment, 
                  initialVertical, 
                  initialHorizontal, 
                  targetVertical, 
                  targetHorizontal 
         );
      } else {
         return interpolateHorizontally( 
                  environment, 
                  initialVertical, 
                  initialHorizontal, 
                  targetVertical, 
                  targetHorizontal 
         );
      }
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
   
   /**
    * Method to interpolate horizontally, where there is no gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialPosition the initial position to move from.
    * @param targetHorizontal the target horizontal position.
    * @return true if this position collides with something.
    */
   private boolean flatHorizontalInterpolation( Environment environment, EnvironmentPosition initialPosition, int targetHorizontal ) {
      double horizontalDelta = targetHorizontal - initialPosition.horizontal();
      
      if ( horizontalDelta >= 0 ) {
         for ( int horizontalProgress = initialPosition.horizontal(); horizontalProgress <= targetHorizontal; horizontalProgress++ ) { 
            if ( interpolate( environment, initialPosition.vertical(), horizontalProgress ) ) {
               return true;
            }
         }
      } else {
         for ( int horizontalProgress = initialPosition.horizontal(); horizontalProgress >= targetHorizontal; horizontalProgress-- ) { 
            if ( interpolate( environment, initialPosition.vertical(), horizontalProgress ) ) {
               return true;
            }
         }
      }
      return false;
   }//End Method
   
   /**
    * Method to interpolate vertically, where there is no gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialPosition the initial position to move from.
    * @param targetVertical the target vertical position.
    * @return true if this position collides with something.
    */
   private boolean flatVerticalInterpolation( Environment environment, EnvironmentPosition initialPosition, int targetVertical ) {
      double verticalDelta = targetVertical - initialPosition.vertical();
      
      if ( verticalDelta >= 0 ) {
         for ( int verticalProgress = initialPosition.vertical(); verticalProgress <= targetVertical; verticalProgress++ ) { 
            if ( interpolate( environment, verticalProgress, initialPosition.horizontal() ) ) {
               return true;
            }
         }
      } else {
         for ( int verticalProgress = initialPosition.vertical(); verticalProgress >= targetVertical; verticalProgress-- ) { 
            if ( interpolate( environment, verticalProgress, initialPosition.horizontal() ) ) {
               return true;
            }
         }
      }
      return false;
   }//End Method
   
   /**
    * Method to interpolate vertically, where the vertical gradient is greater than or equal to the 
    * horizontal gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialVertical the initial vertical position.
    * @param initialHorizontal the initial horizontal position.
    * @param targetHorizontal the target horizontal position.
    * @param targetVertical the target vertical position.
    * @return true if this position collides with something.
    */
   private boolean interpolateVertically( Environment environment, int initialVertical, int initialHorizontal, int targetVertical, int targetHorizontal ){
      double verticalDelta = targetVertical - initialVertical;
      double horizontalDelta = targetHorizontal - initialHorizontal;
      double verticalStepSize = verticalDelta >= 0 ? 1 : -1;
      int horizontalStepSize = horizontalDelta >= 0 ? 1 : -1;
      double error = -1.0;
      double deltaError = Math.abs( horizontalDelta / verticalDelta ); 
      int horizontalProgress = initialHorizontal;
      
      if ( verticalStepSize >= 0 ) {
         for ( int verticalProgress = initialVertical; verticalProgress <= targetVertical; verticalProgress++ ) {
            if ( interpolate( environment, verticalProgress, horizontalProgress ) ) {
               return true;
            }
            
            error = error + deltaError;
            if ( error >= 0.0 ) {
               horizontalProgress = horizontalProgress + horizontalStepSize;
               error = error - 1;
            }
         }
      } else {
         for ( int verticalProgress = initialVertical; verticalProgress >= targetVertical; verticalProgress-- ) {
            if ( interpolate( environment, verticalProgress, horizontalProgress ) ) {
               return true;
            }
            error = error + deltaError;
            if ( error >= 0.0 ) {
               horizontalProgress = horizontalProgress + horizontalStepSize;
               error = error - 1;
            }
         }
      }
      return false;
   }//End Method
   
   /**
    * Method to interpolate horizontally, where the horizontal gradient is greater than or equal to the 
    * vertical gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialVertical the initial vertical position.
    * @param initialHorizontal the initial horizontal position.
    * @param targetHorizontal the target horizontal position.
    * @param targetVertical the target vertical position.
    * @return true if this position collides with something.
    */
   private boolean interpolateHorizontally( Environment environment, int initialVertical, int initialHorizontal, int targetVertical, int targetHorizontal ){
      double verticalDelta = targetVertical - initialVertical;
      double horizontalDelta = targetHorizontal - initialHorizontal;
      int verticalStepSize = verticalDelta >= 0 ? 1 : -1;
      int horizontalStepSize = horizontalDelta >= 0 ? 1 : -1;
      double error = -1.0;
      double deltaError = Math.abs( verticalDelta / horizontalDelta ); 
      int verticalProgress = initialVertical;
      
      if ( horizontalStepSize >= 0 ) {
         for ( int horizontalProgress = initialHorizontal; horizontalProgress <= targetHorizontal; horizontalProgress++ ) {
            if ( interpolate( environment, verticalProgress, horizontalProgress ) ) {
               return true;
            }
            
            error = error + deltaError;
            if ( error >= 0.0 ) {
               verticalProgress = verticalProgress + verticalStepSize;
               error = error - 1;
            }
         }
      } else {
         for ( int horizontalProgress = initialHorizontal; horizontalProgress >= targetHorizontal; horizontalProgress-- ) {
            if ( interpolate( environment, verticalProgress, horizontalProgress ) ) {
               return true;
            }
            error = error + deltaError;
            if ( error >= 0.0 ) {
               verticalProgress = verticalProgress + verticalStepSize;
               error = error - 1;
            }
         }
      }
      return false;
   }//End Method
   
}//End Class
