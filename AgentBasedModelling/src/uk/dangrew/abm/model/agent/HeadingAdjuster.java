package uk.dangrew.abm.model.agent;

import java.util.Random;

/**
 * The {@link HeadingAdjuster} is responsible for changing the {@link Heading} of an {@link Agent}.
 */
class HeadingAdjuster {

   static final int VELOCITY_DISTRIBUTION = 10;
   
   private final Random random;
   private ControllableAgent agent;
   
   /**
    * Constructs a new {@link HeadingAdjuster}.
    */
   public HeadingAdjuster() {
      this( new Random() );
   }//End Constructor
   
   /**
    * Constructs a new {@link HeadingAdjuster}.
    * @param random the {@link Random} associated.
    */
   HeadingAdjuster( Random random ){
      this.random = random;
   }//End Constructor
   
   /**
    * Method to associated the given {@link ControllableAgent} with the {@link HeadingAdjuster}.
    * @param agent the {@link ControllableAgent} to associate.
    */
   void associate( ControllableAgent agent ) {
      if ( this.agent != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.agent = agent;
   }//End Method
   
   /**
    * Method to change the heading of the {@link Agent}.
    */
   void changeHeading(){
      int proportion = random.nextInt( VELOCITY_DISTRIBUTION );
      boolean positiveHorizontal = random.nextBoolean();
      boolean positiveVertical = random.nextBoolean();
      
      int horizontalProportion = proportion;
      int verticalProportion = VELOCITY_DISTRIBUTION - horizontalProportion;
      
      if ( !positiveHorizontal ) {
         horizontalProportion = Math.negateExact( horizontalProportion );
      }
      if ( !positiveVertical ) {
         verticalProportion = Math.negateExact( verticalProportion );
      }
      
      agent.setHeading( new Heading( verticalProportion, horizontalProportion ) );
   }//End Method
   
}//End Class
