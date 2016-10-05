package uk.dangrew.abm.model.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * {@link NeighbourHood} implementation.
 */
class NeighbourHoodImpl implements NeighbourHood {
   
   private ControllableAgent subjectAgent;
   private final Map< NeighbourHoodRange, ObservableList< Agent > > neighbours;
   private final Map< NeighbourHoodRange, ObservableList< Agent > > unmodifiableNeighbours;
   
   /**
    * Constructs a new {@link NeighbourHoodImpl}.
    */
   NeighbourHoodImpl() {
      this.neighbours = new HashMap<>();
      this.unmodifiableNeighbours = new HashMap<>();
      for ( NeighbourHoodRange range : NeighbourHoodRange.values() ) {
         ObservableList< Agent > list = FXCollections.observableArrayList();
         this.neighbours.put( range, list );
         this.unmodifiableNeighbours.put( range, FXCollections.unmodifiableObservableList( list ) );
      }
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
   @Override public ObservableList< Agent > neighbours( NeighbourHoodRange range ) {
      return unmodifiableNeighbours.get( range );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void identifyNeighbourHood( Environment environment ) {
      Map< NeighbourHoodRange, List< Agent > > temporaryStore = new HashMap<>();
      for ( NeighbourHoodRange range : NeighbourHoodRange.values() ) {
         temporaryStore.put( range, new ArrayList<>() );
      }
      
      for ( Agent agent : environment.agents().values() ) {
         if ( agent == subjectAgent ) {
            continue;
         }
         
         double distanceToAgent = calculateDistanceToAgent( agent );
         
         for ( NeighbourHoodRange range : NeighbourHoodRange.values() ) {
            if ( range.isWithinRange( distanceToAgent ) ) {
               temporaryStore.get( range ).add( agent );
            }
         }
      }
      
      temporaryStore.forEach( ( range, detectedNeighbours ) -> {
         neighbours.get( range ).clear();
         neighbours.get( range ).addAll( detectedNeighbours );
      } );
   }//End Method
   
   /**
    * Method to calculate the distance between the subject {@link Agent} and the given.
    * @param agent the {@link Agent} to calculate the distance to.
    * @return the calculated distance.
    */
   private double calculateDistanceToAgent( Agent agent ) {
      EnvironmentPosition subjectPosition = subjectAgent.position().get();
      EnvironmentPosition agentPosition = agent.position().get();
      
      double hDifference = agentPosition.horizontal() - subjectPosition.horizontal();
      double vDifference = agentPosition.vertical() - subjectPosition.vertical();
      
      return Math.sqrt( hDifference * hDifference + vDifference * vDifference );
   }//End Method
   
}//End Class
