package uk.dangrew.abm.model.agent;

import java.util.Random;

/**
 * The {@link HeadingAdjuster} is responsible for changing the {@link Heading} of an {@link Agent}.
 */
class SwarmingNatureImpl implements SwarmingNature {

   private final Random random;
   private ControllableAgent agent;
   private NeighbourHood neighbourHood;
   
   /**
    * Constructs a new {@link HeadingAdjuster}.
    */
   public SwarmingNatureImpl() {
      this( new Random() );
   }//End Constructor
   
   /**
    * Constructs a new {@link SwarmingNatureImpl}.
    * @param random the {@link Random} associated.
    */
   SwarmingNatureImpl( Random random ){
      this.random = random;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( ControllableAgent agent, NeighbourHood neighbourHood ) {
      if ( this.agent != null || this.neighbourHood != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.agent = agent;
      this.neighbourHood = neighbourHood;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void randomizeHeading(){
      int nextInt = random.nextInt( FixedHeading.values().length );
      FixedHeading heading = FixedHeading.values()[ nextInt ];
      
      agent.setHeading( heading.heading() );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean respondToNeighbours() {
      if ( neighbourHood.neighbours().isEmpty() ) {
         return false;
      }
      
      double averageH = 0.0;
      double averageV = 0.0;
      for( Agent agent : neighbourHood.neighbours() ) {
         averageH += agent.heading().get().horizontalVelocity();
         averageV += agent.heading().get().verticalVelocity();
      }
      averageH /= neighbourHood.neighbours().size();
      averageV /= neighbourHood.neighbours().size();
      
      int headingH = ( int )Math.round( averageH );
      int headingV = ( int )Math.round( averageV );
      
      if ( headingH == 0 && headingV == 0 ) {
         //Take the initiative and move!
         return false;
      }
      
      Heading proposedHeading = new Heading( headingV, headingH );
      if ( agent.heading().get().equals( proposedHeading ) ) {
         //Unchanged
         return false;
      }
      agent.setHeading( proposedHeading );
      return true;
   }//End Method
   
}//End Class
