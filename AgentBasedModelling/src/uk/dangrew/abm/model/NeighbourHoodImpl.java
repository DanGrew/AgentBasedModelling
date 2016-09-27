package uk.dangrew.abm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link NeighbourHood} implementation.
 */
class NeighbourHoodImpl implements NeighbourHood {
   
   private static final int IMMEDIATE_NEIGHBOURHOOD_DISTANCE = 2;
   private static final int IMMEDIATE_NEIGHBOURHOOD_EDGE_LENGTH = 10;

   private ControllableAgent subjectAgent;
   
   NeighbourHoodImpl() {}//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( ControllableAgent agent ) {
      if ( this.subjectAgent != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.subjectAgent = agent;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public boolean respondToNeighbours( Environment environment ) {
      EnvironmentPosition topLeftOfNeighbourhood = subjectAgent.position().get().translate( 
               new Heading( -IMMEDIATE_NEIGHBOURHOOD_DISTANCE, -IMMEDIATE_NEIGHBOURHOOD_DISTANCE ) 
      );

      final int vProgress = topLeftOfNeighbourhood.vertical();
      final List< Agent > agentsInImmediate = new ArrayList<>();
      
      for ( int v = vProgress; v < vProgress + IMMEDIATE_NEIGHBOURHOOD_EDGE_LENGTH; v++ ) {
         final int hProgress = topLeftOfNeighbourhood.horizontal();
         for ( int h = hProgress; h < hProgress + IMMEDIATE_NEIGHBOURHOOD_EDGE_LENGTH; h++ ) {
            Agent neighbour = environment.agents().get( new EnvironmentPosition( v, h ) );
            if ( neighbour == subjectAgent ) {
               continue;
            }
            if ( neighbour != null ) {
               agentsInImmediate.add( neighbour );
            }
         }         
      }
      
      if ( agentsInImmediate.isEmpty() ) {
         return false;
      }
      
      double averageH = 0.0;
      double averageV = 0.0;
      for( Agent agent : agentsInImmediate ) {
         averageH += agent.heading().get().horizontalVelocity();
         averageV += agent.heading().get().verticalVelocity();
      }
      averageH /= agentsInImmediate.size();
      averageV /= agentsInImmediate.size();
      
      int headingH = ( int )Math.round( averageH );
      int headingV = ( int )Math.round( averageV );
      
      if ( headingH == 0 && headingV == 0 ) {
         //Take the initiative and move!
         return false;
      }
      
      Heading proposedHeading = new Heading( headingV, headingH );
      if ( subjectAgent.heading().get().equals( proposedHeading ) ) {
         //Unchanged
         return false;
      }
      subjectAgent.setHeading( proposedHeading );
      return true;
   }//End Method

}//End Class
