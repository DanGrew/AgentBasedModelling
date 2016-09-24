package uk.dangrew.abm.model;

/**
 * Interface for an agent in the ABM system.
 */
public interface Agent {

   /**
    * Access to current {@link EnvironmentPosition}.
    * @return {@link EnvironmentPosition}.
    */
   public EnvironmentPosition position();

   /**
    * Method to move the {@link Agent} according to their current prerogative.
    * @param environment the {@link Environment} to move in.
    */
   public void move( Environment environment );

}//End Interface
