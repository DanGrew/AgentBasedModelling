package uk.dangrew.abm.model.agent;

import javafx.beans.property.ReadOnlyObjectProperty;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

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
    * Access to the age of the {@link Agent}.
    * @return the age.
    */
   public ReadOnlyObjectProperty< Integer > age();
   
   /**
    * Method to get the {@link AgeBracket} of the {@link Agent}.
    * @return the {@link AgeBracket} according to the age. 
    */
   public AgeBracket getAgeBracket();
   
   /**
    * Access to the life expectancy of the {@link Agent}.
    * @return the life expectancy.
    */
   public ReadOnlyObjectProperty< Integer > lifeExpectancy();
   
   /**
    * Access to the {@link Gender} of the {@link Agent}.
    * @return the {@link Gender}.
    */
   public Gender gender();
   
   /**
    * Access to the mating cycle of the {@link Agent}.
    * @return the time between each mating attempt.
    */
   public int matingCycle();
   
   /**
    * Access to the current movement speed of the {@link Agent}.
    * @return the property.
    */
   public ReadOnlyObjectProperty< Double > movementSpeed();

   /**
    * Method to move the {@link Agent} according to their current prerogative.
    * @param environment the {@link Environment} to move in.
    */
   public void move( Environment environment );

   /**
    * Method to mate with this {@link Agent}.
    * @param agent the {@link Agent} wishing to mate this with this.
    */
   public void mate( Agent agent );

}//End Interface
