package uk.dangrew.abm.model.agent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * {@link Agent} implementation.
 */
public class AgentImpl implements Agent, ControllableAgent {

   private final ObjectProperty< Heading > heading;
   private final ObjectProperty< EnvironmentPosition > position;
   
   private final MovementInterpolator interpolator;
   private final HeadingAdjuster headingAdjuster;
   private final NeighbourHood neighbourHood;
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param heading the initial {@link Heading} of the {@link Agent}.
    */
   public AgentImpl( EnvironmentPosition position, Heading heading ) {
      this( new FluidMovementInterpolator(), new HeadingAdjuster(), new NeighbourHoodImpl(), position, heading );
   }//End Constructor
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param interpolator the {@link MovementInterpolator} for controlling movement.
    * @param headingAdjuster the {@link HeadingAdjuster} for controlling the {@link Heading}.
    * @param neighbourHood the {@link NeighbourHood} for responding to.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param heading the initial {@link Heading} of the {@link Agent}.
    */
   AgentImpl( 
            MovementInterpolator interpolator, 
            HeadingAdjuster headingAdjuster, 
            NeighbourHood neighbourHood,
            EnvironmentPosition position, 
            Heading heading 
   ) {
      this.position = new SimpleObjectProperty<>( position );
      this.heading = new SimpleObjectProperty< Heading >( heading );
      
      this.interpolator = interpolator;
      this.interpolator.associate( this );
      this.headingAdjuster = headingAdjuster;
      this.headingAdjuster.associate( this );
      this.neighbourHood = neighbourHood;
      this.neighbourHood.associate( this );
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
      neighbourHood.respondToNeighbours( environment );
      if ( interpolator.move( environment ) ) {
         headingAdjuster.changeHeading();
      }
   }//End Method
   
}//End Class
