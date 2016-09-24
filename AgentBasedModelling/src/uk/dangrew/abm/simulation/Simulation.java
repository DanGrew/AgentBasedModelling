package uk.dangrew.abm.simulation;

import uk.dangrew.abm.model.Agent;
import uk.dangrew.abm.model.AgentImpl;
import uk.dangrew.abm.model.Environment;
import uk.dangrew.abm.model.EnvironmentPosition;

/**
 * Method to run a simulation. Simple initially.
 */
public class Simulation {
   
   public static final void main( String[] args ) throws InterruptedException {
      Environment environment = new Environment( 100, 100 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 0, 0 ), 100 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 99, 0 ), 100 );
      
      Agent agent = new AgentImpl( new EnvironmentPosition( 5, 5 ), 10, 0 );
      System.out.println( agent.position() );
      
      while( true ) {
         Thread.sleep( 500 );
         
         agent.move( environment );
         System.out.println( agent.position() );
      }
   }//End Method

}//End Class
