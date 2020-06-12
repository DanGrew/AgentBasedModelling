package uk.dangrew.abm.model.agent;

import java.util.List;

/**
 * {@link InfantPositioning} provides a rough attempt at steering {@link AgeBracket#Infant}s
 * towards the average position of the {@link AgeBracket#Adult}s.
 */
class InfantPositioning {

   private final FixedHeadings fixedHeadings;
   
   /**
    * Constructs a new {@link InfantPositioning}.
    */
   public InfantPositioning() {
      this.fixedHeadings = new FixedHeadings();
   }//End Constructor
   
   /**
    * Method to find the {@link Heading} that will lead the {@link Agent} to the average
    * position of the {@link AgeBracket#Adult}s in the {@link NeighbourHood}.
    * @param subject the subject that wants to move.
    * @param neighbourHood the {@link NeighbourHood} of the subject.
    * @return the {@link Heading} found to achieve that.
    */
   public Heading findHeadingToAdultCenteredPosition( Agent subject, NeighbourHood neighbourHood ) {
      List< Agent > adults = neighbourHood.neighbours( NeighbourHoodRange.Cohesion ).filtered( agent -> {
         switch ( agent.getAgeBracket() ) {
            case Adult:
               return true;
            case Complete:
            case Elder:
            case Infant:
            case Youth:
            default:
               return false;
         }
      } );
      
      if ( adults.isEmpty() ) {
         return null;
      }
      
      double averageH = 0;
      double averageV = 0;
      
      for( Agent agent : adults ) {
         averageH += agent.position().get().horizontal();
         averageV += agent.position().get().vertical();
      }
      
      averageH /= adults.size();
      averageV /= adults.size();
      
      averageH -= subject.position().get().horizontal();
      averageV -= subject.position().get().vertical();
      
      FixedHeading fixedHeading = fixedHeadings.calculateHeading( averageV, averageH );
      if ( fixedHeading != null ) {
         return fixedHeading.heading();
      }
      
      return null;
   }//End Method

}//End Class
