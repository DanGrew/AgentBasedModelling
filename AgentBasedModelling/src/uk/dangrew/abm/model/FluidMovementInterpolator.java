package uk.dangrew.abm.model;

/**
 * The {@link FluidMovementInterpolator} provides interpolation of movement where the {@link Agent}
 * can have a more detailed approach, hitting every square as it moves.
 */
public class FluidMovementInterpolator implements MovementInterpolator {
   
   static final double MOVEMENT_PROPORTION = 0.1;
   private static final double BOUND = 0.5;
   private static final int STEP = 1;
   
   private ControllableAgent agent;
   private final double proportion;
   private double horizontal;
   private double vertical;
   
   /**
    * Constructs a new {@link FluidMovementInterpolator}.
    */
   FluidMovementInterpolator() {
      this( MOVEMENT_PROPORTION );
   }//End Constructor

   /**
    * Constructs a new {@link FluidMovementInterpolator}.
    * @param proportion the propertion to move each movement.
    */
   FluidMovementInterpolator( double proportion ) {
      this.proportion = proportion;
      this.horizontal = 0;
      this.vertical = 0;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public void associate( ControllableAgent agent ) {
      if ( this.agent != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.agent = agent;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean move( Environment environment ) {
      double hStep = agent.heading().get().horizontalVelocity() * proportion;
      double vStep = agent.heading().get().verticalVelocity() * proportion;
      
      horizontal += hStep;
      vertical += vStep;
      
      if ( hStep >= 0 ) {
         if( positiveHorizontalMove( environment ) ) {
            return true;
         }
      } else {
         if ( negativeHorizontalMove( environment ) ) {
            return true;
         }
      }
      
      if ( vStep >= 0 ) {
         if ( positiveVerticalMove( environment ) ) {
            return true;
         }
      } else {
         if ( negativeVerticalMove( environment ) ) {
            return true;
         }
      }
      
      return false;
   }//End Method
   
   /**
    * Method to calculate the positive horizontal movement.
    * @param environment the {@link Environment} to move in.
    * @return true if collision prevents movement.
    */
   private boolean positiveHorizontalMove( Environment environment ) {
      while( horizontal >= BOUND ) {
         horizontal -= STEP;
         if ( collides( environment, agent.position().get().horizontal( STEP ) ) ) {
            return true;
         }
      }
      
      return false;
   }//End Method
   
   /**
    * Method to calculate the negative horizontal movement.
    * @param environment the {@link Environment} to move in.
    * @return true if collision prevents movement.
    */
   private boolean negativeHorizontalMove( Environment environment ) {
      while( horizontal <= -BOUND ) {
         horizontal += STEP;
         if ( collides( environment, agent.position().get().horizontal( -STEP ) ) ) {
            return true;
         }
      }
      
      return false;
   }//End Method
   
   /**
    * Method to calculate the positive vertical movement.
    * @param environment the {@link Environment} to move in.
    * @return true if collision prevents movement.
    */
   private boolean positiveVerticalMove( Environment environment ) {
      while( vertical >= BOUND ) {
         vertical -= STEP;
         if ( collides( environment, agent.position().get().vertical( STEP ) ) ) {
            return true;
         }
      }
      
      return false;
   }//End Method
   
   /**
    * Method to calculate the negative vertical movement.
    * @param environment the {@link Environment} to move in.
    * @return true if collision prevents movement.
    */
   private boolean negativeVerticalMove( Environment environment ) {
      while( vertical <= -BOUND ) {
         vertical += STEP;
         if ( collides( environment, agent.position().get().vertical( -STEP ) ) ) {
            return true;
         }
      }
      
      return false;
   }//End Method
   
   /**
    * Method to check for collisions on movements.
    * @param environment the {@link Environment} to move in.
    * @param position the proposed {@link EnvironmentPosition}.
    * @return true if collision occurs.
    */
   private boolean collides( Environment environment, EnvironmentPosition position ) {
      if ( agent.position().get().equals( position ) ) {
         return false;
      }
      
      if ( environment.isAvailable( position ) ) {
         agent.setPosition( position );
         return false;
      }
      
      return true;
   }//End Method

}//End Class
