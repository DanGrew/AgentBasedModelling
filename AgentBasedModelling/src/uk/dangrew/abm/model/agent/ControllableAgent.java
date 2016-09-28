package uk.dangrew.abm.model.agent;

import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * The {@link ControllableAgent} provides a package level interface that allows compositie
 * support within the {@link Agent} to control its attributes.
 */
interface ControllableAgent extends Agent {

   /**
    * Setter for the {@link EnvironmentPosition} of the {@link Agent}.
    * @param position the {@link EnvironmentPosition} to set.
    */
   public void setPosition( EnvironmentPosition position );

   /**
    * Setter for the {@link Heading} of the {@link Agent}.
    * @param heading the {@link Heading} to set.
    */
   public void setHeading( Heading heading );
   
   /**
    * Setter for the age of the {@link Agent}.
    * @param age the age to set.
    */
   public void setAge( int age );

   /**
    * Setter for the life expectancy of the {@link Agent}.
    * @param lifeExpectancy the life expectancy to set.
    */
   public void setLifeExpectancy( int lifeExpectancy );
}//End Interface
