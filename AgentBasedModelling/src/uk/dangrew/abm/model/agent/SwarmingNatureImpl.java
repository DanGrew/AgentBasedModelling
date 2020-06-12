package uk.dangrew.abm.model.agent;

import java.util.Random;

/**
 * The {@link HeadingAdjuster} is responsible for changing the {@link Heading} of an {@link Agent}.
 */
class SwarmingNatureImpl implements SwarmingNature {

   private final Random random;
   private final FixedHeadings headings;
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
      this.headings = new FixedHeadings();
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
//      if ( agent.getAgeBracket() == AgeBracket.Infant ) {
//         Heading proposed = new InfantPositioning().findHeadingToAdultCenteredPosition( agent, neighbourHood );
//         if ( proposed != null ) {
//            agent.setHeading( proposed );
//            return true;
//         }
//      }
      
      if ( neighbourHood.neighbours( NeighbourHoodRange.Separation ).isEmpty() ) {
         return false;
      }
      
      double averageH = 0.0;
      double averageV = 0.0;
      for( Agent agent : neighbourHood.neighbours( NeighbourHoodRange.Separation ) ) {
         averageH += agent.heading().get().horizontalVelocity();
         averageV += agent.heading().get().verticalVelocity();
      }
      averageH /= neighbourHood.neighbours( NeighbourHoodRange.Separation ).size();
      averageV /= neighbourHood.neighbours( NeighbourHoodRange.Separation ).size();
      
      int headingH = ( int )Math.round( averageH );
      int headingV = ( int )Math.round( averageV );
      
      if ( headingH == 0 && headingV == 0 ) {
         //Take the initiative and move!
         return false;
      }
      
      FixedHeading fixedHeading = headings.calculateHeading( headingV, headingH );
      Heading proposedHeading = fixedHeading.heading();
      if ( agent.heading().get().equals( proposedHeading ) ) {
         //Unchanged
         return false;
      }
      agent.setHeading( proposedHeading );
      return true;
   }//End Method
   
}//End Class
