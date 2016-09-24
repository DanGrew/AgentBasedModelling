package uk.dangrew.abm.model;

/**
 * {@link Agent} implementation.
 */
public class AgentImpl implements Agent {

   private int horizontalVelocity;
   private int verticalVelocity;
   private EnvironmentPosition position;
   
   /**
    * Constructs a new {@link AgentImpl}.
    * @param position the {@link EnvironmentPosition} to start at.
    */
   public AgentImpl( EnvironmentPosition position ) {
      this.position = position;
      this.horizontalVelocity = 0;
      this.verticalVelocity = 1;
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
      EnvironmentPosition proposedMove = position.vertical( verticalVelocity );
      if ( environment.isSpace( proposedMove ) ) {
         this.position = proposedMove;
      } else if ( environment.isBoundary( proposedMove ) ) {
         this.verticalVelocity *= -1;
         this.position = position.vertical( verticalVelocity );
      }
   }//End Method

}//End Class
