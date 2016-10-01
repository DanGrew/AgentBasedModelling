package uk.dangrew.abm.model.agent;

import javafx.collections.ObservableList;
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
    * Access to the {@link ObservableList} of {@link Agent}s in the {@link NeighbourHood}.
    * @return an unmodifiable version of the nieghbours.
    */
   public ObservableList< Agent > neighbours();

   /**
    * Method to identify the {@link Agent}s in the {@link NeighbourHood}.
    * @param environment the {@link Environment} to identify the {@link NeighbourHood} in.
    */
   public void identifyNeighbourHood( Environment environment );
   
   /**
    * Method to respond to the {@link Agent}s in the {@link NeighbourHood} according to the rules
    * defined in this {@link NeighbourHood}.
    * @return true if affected by neighbouring {@link Agent}s.
    */
   public boolean respondToNeighbours();

}//End Interface
