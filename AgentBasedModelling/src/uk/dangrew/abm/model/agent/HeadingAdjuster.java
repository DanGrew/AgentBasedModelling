package uk.dangrew.abm.model.agent;

import java.util.Random;

/**
 * The {@link HeadingAdjuster} is responsible for changing the {@link Heading} of an {@link Agent}.
 */
class HeadingAdjuster {

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
      int nextInt = random.nextInt( FixedHeading.values().length );
      FixedHeading heading = FixedHeading.values()[ nextInt ];
      
      agent.setHeading( heading.heading() );
   }//End Method
   
}//End Class
