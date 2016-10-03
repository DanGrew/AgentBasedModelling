package uk.dangrew.abm.model.agent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * {@link NeighbourHood} implementation.
 */
class NeighbourHoodImpl implements NeighbourHood {
   
   private static final int IMMEDIATE_NEIGHBOURHOOD_DISTANCE = 3;
   private static final int IMMEDIATE_NEIGHBOURHOOD_EDGE_LENGTH = 7;

   private ControllableAgent subjectAgent;
   private final ObservableList< Agent > neighbours;
   private final ObservableList< Agent > unmodifiableNeighbours;
   
   /**
    * Constructs a new {@link NeighbourHoodImpl}.
    */
   NeighbourHoodImpl() {
      this.neighbours = FXCollections.observableArrayList();
      this.unmodifiableNeighbours = FXCollections.unmodifiableObservableList( this.neighbours );
   }//End Constructor
   
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
   @Override public ObservableList< Agent > neighbours() {
      return unmodifiableNeighbours;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void identifyNeighbourHood( Environment environment ) {
      neighbours.clear();
      
      EnvironmentPosition topLeftOfNeighbourhood = environment.translate( 
               subjectAgent.position().get(),
               new Heading( -IMMEDIATE_NEIGHBOURHOOD_DISTANCE, -IMMEDIATE_NEIGHBOURHOOD_DISTANCE ) 
      );
      
      final int vProgress = topLeftOfNeighbourhood.vertical();
      
      for ( int v = vProgress; v < vProgress + IMMEDIATE_NEIGHBOURHOOD_EDGE_LENGTH; v++ ) {
         final int hProgress = topLeftOfNeighbourhood.horizontal();
         for ( int h = hProgress; h < hProgress + IMMEDIATE_NEIGHBOURHOOD_EDGE_LENGTH; h++ ) {
            Agent neighbour = environment.agents().get( environment.locate( v, h ) );
            if ( neighbour == subjectAgent ) {
               continue;
            }
            if ( neighbour != null ) {
               neighbours.add( neighbour );
            }
         }         
      }
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean respondToNeighbours() {
      if ( neighbours.isEmpty() ) {
         return false;
      }
      
      double averageH = 0.0;
      double averageV = 0.0;
      for( Agent agent : neighbours ) {
         averageH += agent.heading().get().horizontalVelocity();
         averageV += agent.heading().get().verticalVelocity();
      }
      averageH /= neighbours.size();
      averageV /= neighbours.size();
      
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
