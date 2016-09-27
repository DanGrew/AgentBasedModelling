package uk.dangrew.abm.model.agent;

import uk.dangrew.abm.model.environment.Environment;

/**
 * Interface for the neighbourhood of an {@link Agent} in the {@link Environment}.
 */
interface NeighbourHood {
   
   /**
    * Method to associated the given {@link ControllableAgent} with the {@link NeighbourHood}.
    * @param agent the {@link ControllableAgent} to associate.
    */
   public void associate( ControllableAgent agent );

   /**
    * Method to respond to the {@link Agent}s in the {@link NeighbourHood} according to the rules
    * defined in this {@link NeighbourHood}.
    * @param environment the {@link Environment} to move in.
    * @return true if affected by neighbouring {@link Agent}s.
    */
   public boolean respondToNeighbours( Environment environment );

}//End Interface
