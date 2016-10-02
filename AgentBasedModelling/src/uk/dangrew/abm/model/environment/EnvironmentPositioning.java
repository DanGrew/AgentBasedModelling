package uk.dangrew.abm.model.environment;

import uk.dangrew.abm.model.agent.Heading;

/**
 * The {@link EnvironmentPositioning} provides positioning operations in relation to the
 * {@link Environment} associated and its size.
 */
public interface EnvironmentPositioning {

   /**
    * Method to associated the {@link Environment} with this {@link EnvironmentPositioning}. 
    * @param environment the {@link Environment} to associate.
    */
   public void associate( Environment environment );
   
   /**
    * Method to locate the vertical and horizontal positions in a grid reference.
    * @param vertical the vertical position.
    * @param horizontal the horizontal position.
    */
   public EnvironmentPosition locate( int vertical, int horizontal );
   
   /**
    * Method to construct a horizontal shift of this {@link EnvironmentPosition}.
    * @param offset the horizontal offset.
    * @return the {@link EnvironmentPosition} of this accounting for the offset.
    */
   public EnvironmentPosition horizontalPositionOffset( EnvironmentPosition original, int offset );
   
   /**
    * Method to construct a vertical shift of this {@link EnvironmentPosition}.
    * @param offset the vertical offset.
    * @return the {@link EnvironmentPosition} of this accounting for the offset.
    */
   public EnvironmentPosition verticalPositionOffset( EnvironmentPosition original, int offset );
   
   /**
    * Method to construct a shift of this {@link EnvironmentPosition}.
    * @param verticalOffset the vertical offset.
    * @param horizontalOffset the horizontal offset.
    * @return the {@link EnvironmentPosition} of this accounting for the offset.
    */
   public EnvironmentPosition translate( EnvironmentPosition original, Heading heading );

}//End Interface