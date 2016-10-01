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
   private final ObjectProperty< Integer > age;
   private final ObjectProperty< Integer > lifeExpectancy;
   private Gender gender;
   private int reproductiveDrive;
   
   private final MovementInterpolator interpolator;
   private final HeadingAdjuster headingAdjuster;
   private final NeighbourHood neighbourHood;
   private final Lifecycle lifecycle;
   private ParentHood parentHood;
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param heading the initial {@link Heading} of the {@link Agent}.
    */
   public AgentImpl( EnvironmentPosition position, Heading heading ) {
      this( 
               new FluidMovementInterpolator(), 
               new HeadingAdjuster(), 
               new NeighbourHoodImpl(), 
               new Lifecycle(),
               position, heading 
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param interpolator the {@link MovementInterpolator} for controlling movement.
    * @param headingAdjuster the {@link HeadingAdjuster} for controlling the {@link Heading}.
    * @param neighbourHood the {@link NeighbourHood} for responding to.
    * @param lifecycle the {@link Lifecycle} of the {@link Agent}.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param heading the initial {@link Heading} of the {@link Agent}.
    */
   AgentImpl( 
            MovementInterpolator interpolator, 
            HeadingAdjuster headingAdjuster, 
            NeighbourHood neighbourHood,
            Lifecycle lifecycle,
            EnvironmentPosition position, 
            Heading heading 
   ) {
      this.position = new SimpleObjectProperty<>( position );
      this.heading = new SimpleObjectProperty< Heading >( heading );
      this.age = new SimpleObjectProperty<>();
      this.lifeExpectancy = new SimpleObjectProperty<>();
      
      this.interpolator = interpolator;
      this.interpolator.associate( this );
      this.headingAdjuster = headingAdjuster;
      this.headingAdjuster.associate( this );
      this.neighbourHood = neighbourHood;
      this.neighbourHood.associate( this );
      this.lifecycle = lifecycle;
      this.lifecycle.associate( this );
      this.lifecycle.birth();
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
   @Override public ReadOnlyObjectProperty< Integer > age() {
      return age;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public AgeBracket getAgeBracket() {
      return lifecycle.getAgeBracket();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void setAge( int age ) {
      this.age.set( age );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public ReadOnlyObjectProperty< Integer > lifeExpectancy() {
      return lifeExpectancy;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void setLifeExpectancy( int lifeExpectancy ) {
      this.lifeExpectancy.set( lifeExpectancy );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public Gender gender() {
      return gender;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public int matingCycle() {
      return reproductiveDrive;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void setMatingCycle( int drive ) {
      this.reproductiveDrive = drive;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void setGender( Gender gender, ParentHood parentHood ) {
      this.gender = gender;
      this.parentHood = parentHood;
      this.parentHood.associate( this, neighbourHood );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void mate( Agent agent ) {
      parentHood.mate( agent );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void move( Environment environment ) {
      lifecycle.age( environment );
      
      if ( getAgeBracket() == AgeBracket.Complete ) {
         return;
      }
      neighbourHood.identifyNeighbourHood( environment );
      neighbourHood.respondToNeighbours();
      parentHood.mingle( environment );
      if ( interpolator.move( environment ) ) {
         headingAdjuster.changeHeading();
      }
   }//End Method
   
}//End Class
