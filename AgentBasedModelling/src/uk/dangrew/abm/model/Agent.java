package uk.dangrew.abm.model;

import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Interface for an agent in the ABM system.
 */
public interface Agent {

   /**
    * Access to current {@link EnvironmentPosition}.
    * @return {@link EnvironmentPosition}.
    */
   public ReadOnlyObjectProperty< EnvironmentPosition > position();
   
   /**
    * Access to the {@link Heading} of the {@link Agent}.
    * @return the {@link Heading}.
    */
   public ReadOnlyObjectProperty< Heading > heading();

   /**
    * Method to move the {@link Agent} according to their current prerogative.
    * @param environment the {@link Environment} to move in.
    */
   public void move( Environment environment );

}//End Interface
