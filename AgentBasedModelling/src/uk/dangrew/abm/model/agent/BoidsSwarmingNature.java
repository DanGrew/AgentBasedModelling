package uk.dangrew.abm.model.agent;

import java.util.Random;

import javafx.collections.ObservableList;

public class BoidsSwarmingNature implements SwarmingNature {

   private final Random random;
   private final FixedHeadings fixedHeadings;
   private ControllableAgent agent;
   private NeighbourHood neighbourHood;
   
   BoidsSwarmingNature() {
      this.fixedHeadings = new FixedHeadings();
      this.random = new Random();
   }//End Constructor
   
   @Override public void associate( ControllableAgent agent, NeighbourHood neighbourHood ) {
      if ( this.agent != null || this.neighbourHood != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.agent = agent;
      this.neighbourHood = neighbourHood;
   }

   @Override public void randomizeHeading() {
      int nextInt = random.nextInt( FixedHeading.values().length );
      FixedHeading heading = FixedHeading.values()[ nextInt ];
      
      agent.setHeading( heading.heading() );
   }//End Method

   @Override public boolean respondToNeighbours() {
      if ( neighbourHood.neighbours( NeighbourHoodRange.Cohesion ).isEmpty() && 
               neighbourHood.neighbours( NeighbourHoodRange.Separation ).isEmpty() ) {
         return false;
      }
      
      double[] separation = calculateSeparationHeading();
      double[] cohesion = calculateCohesionHeading();
      
      separation = new double[]{ separation[ 0 ] * 1, separation[ 1 ] * 1 };
      
      final FixedHeading boidsHeading = calculateAverageHeading( cohesion, separation );
      if ( boidsHeading == null ) {
         return false;
      }
      agent.setHeading( boidsHeading.heading() );
      System.out.println( agent.toString() + ": " + boidsHeading.heading() );
      return true;
   }//End Method
   
   private double[] calculateSeparationHeading(){
      ObservableList< Agent > separationNeighbours = neighbourHood.neighbours( NeighbourHoodRange.Separation );
      double[] separation = calculateAveragePosition( separationNeighbours ); 
      separation = calculateHeadingToPosition( separation );
      return new double[]{ -separation[ 0 ], -separation[ 1 ] };
   }//End Method
   
   private double[] calculateCohesionHeading(){
      ObservableList< Agent > cohesionNeighbours = neighbourHood.neighbours( NeighbourHoodRange.Cohesion );
      double[] cohesion = calculateAveragePosition( cohesionNeighbours ); 
      cohesion = calculateHeadingToPosition( cohesion );
      return cohesion;
   }//End Method
   
   private double[] calculateHeadingToPosition( double[] position ) {
      double headingV = position[ 0 ] - agent.position().get().vertical();
      double headingH = position[ 1 ] - agent.position().get().horizontal();
      return new double[]{ headingV, headingH };
   }//End Method
   
   private double[] calculateAveragePosition( ObservableList< Agent > neighbours ) {
      if ( neighbours.isEmpty() ) {
         return new double[]{ 0, 0 };
      }
      
      double averageH = 0;
      double averageV = 0;
      
      for ( Agent neighbour : neighbours ) {
         averageH = neighbour.position().get().horizontal();
         averageV = neighbour.position().get().vertical();
      }
      
      averageH /= neighbours.size();
      averageV /= neighbours.size();
      
      return new double[]{ averageV, averageH };
   }//End Method
   
   private FixedHeading calculateAverageHeading( double[] first, double[] second ) {
      double averageH = 0;
      double averageV = 0;
      
      averageV = first[ 0 ] + second[ 0 ];
      averageH = first[ 1 ] + second[ 1 ];
      
      averageH /= 2;
      averageV /= 2;
      
      return fixedHeadings.calculateHeading( averageV, averageH );
   }//End Method

}//End Class
