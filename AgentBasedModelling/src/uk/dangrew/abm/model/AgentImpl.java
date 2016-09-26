package uk.dangrew.abm.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * {@link Agent} implementation.
 */
public class AgentImpl implements Agent, ControllableAgent {

   private final ObjectProperty< Heading > heading;
   private final ObjectProperty< EnvironmentPosition > position;
   
   private final MovementInterpolator interpolator;
   private final HeadingAdjuster headingAdjuster;
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param heading the initial {@link Heading} of the {@link Agent}.
    */
   public AgentImpl( EnvironmentPosition position, Heading heading ) {
      this( new MovementInterpolator(), new HeadingAdjuster(), position, heading );
   }//End Constructor
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param interpolator the {@link MovementInterpolator} for controlling movement.
    * @param headingAdjuster the {@link HeadingAdjuster} for controlling the {@link Heading}.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param heading the initial {@link Heading} of the {@link Agent}.
    */
   AgentImpl( 
            MovementInterpolator interpolator, 
            HeadingAdjuster headingAdjuster, 
            EnvironmentPosition position, 
            Heading heading 
   ) {
      this.position = new SimpleObjectProperty<>( position );
      this.heading = new SimpleObjectProperty< Heading >( heading );
      
      this.interpolator = interpolator;
      this.interpolator.associate( this );
      this.headingAdjuster = headingAdjuster;
      this.headingAdjuster.associate( this );
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public ReadOnlyObjectProperty< EnvironmentPosition > position() {
      return position;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void setPosition( EnvironmentPosition position ) {
      this.position.set( position );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public ReadOnlyObjectProperty< Heading > heading() {
      return heading;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void setHeading( Heading heading ) {
      this.heading.set( heading );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void move( Environment environment ) {
      if ( interpolator.moveForVelocity( environment ) ) {
         headingAdjuster.changeHeading();
      }
   }//End Method
   
}//End Class
