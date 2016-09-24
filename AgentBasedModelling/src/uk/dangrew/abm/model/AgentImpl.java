package uk.dangrew.abm.model;

import java.util.Random;

/**
 * {@link Agent} implementation.
 */
public class AgentImpl implements Agent {

   static final int VELOCITY_DISTRIBUTION = 10;
   
   private final Random random;
   private int horizontalVelocity;
   private int verticalVelocity;
   private EnvironmentPosition position;
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param verticalVelocity the initial vertical velocity.
    * @param horizontalVelocity the initial horizontal velocity.
    */
   public AgentImpl( EnvironmentPosition position, int verticalVelocity, int horizontalVelocity ) {
      this( new Random(), position, verticalVelocity, horizontalVelocity );
   }//End Constructor
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param random the {@link Random} for randomization.
    * @param position the {@link EnvironmentPosition} to start at.
    * @param verticalVelocity the initial vertical velocity.
    * @param horizontalVelocity the initial horizontal velocity.
    */
   AgentImpl( Random random, EnvironmentPosition position, int verticalVelocity, int horizontalVelocity ) {
      this.position = position;
      this.horizontalVelocity = horizontalVelocity;
      this.verticalVelocity = verticalVelocity;
      
      this.random = random;
   }//End Constructor

   /**
    * {@inheritDoc}
    */
   @Override public EnvironmentPosition position() {
      return position;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void move( Environment environment ) {
      EnvironmentPosition proposedMove = position.translate( verticalVelocity, horizontalVelocity );
      if ( environment.isSpace( proposedMove ) ) {
         this.position = proposedMove;
      } else if ( environment.isBoundary( proposedMove ) ) {
         changeHeading();
         this.position = position.translate( verticalVelocity, horizontalVelocity );
      }
   }//End Method
   
   /**
    * Method to change the heading of the {@link Agent}.
    */
   private void changeHeading(){
      int proportion = random.nextInt( VELOCITY_DISTRIBUTION );
      
      int horizontalProportion = proportion;
      int verticalProportion = VELOCITY_DISTRIBUTION - horizontalProportion;
      
      if ( horizontalVelocity > 0 ) {
         horizontalProportion = Math.negateExact( horizontalProportion );
      }
      if ( verticalVelocity > 0 ) {
         verticalProportion = Math.negateExact( verticalProportion );
      }
      
      this.horizontalVelocity = horizontalProportion;
      this.verticalVelocity = verticalProportion;
   }//End Method

}//End Class
