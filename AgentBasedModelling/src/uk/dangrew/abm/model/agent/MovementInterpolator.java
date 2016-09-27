package uk.dangrew.abm.model.agent;

import uk.dangrew.abm.model.environment.Environment;

/**
 * The {@link MovementInterpolator} provides an interface for moving {@link Agent}s.
 */
public interface MovementInterpolator {
   
   /**
    * Method to associated the {@link ControllableAgent} with this {@link MovementInterpolator}.
    * @param agent the {@link ControllableAgent} to associate.
    */
   public void associate( ControllableAgent agent );
   
   /**
    * Method to move for the {@link Heading} associated with the {@link Agent}. This will identify
    * collisions and move the {@link Agent} when not colliding.
    * @param environment the {@link Environment} to move in.
    * @return true if a collision stopped movement.
    */
   public boolean move( Environment environment );

}//End Interface
