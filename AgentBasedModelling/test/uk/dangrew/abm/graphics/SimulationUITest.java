package uk.dangrew.abm.graphics;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.abm.model.Agent;
import uk.dangrew.abm.model.AgentImpl;
import uk.dangrew.abm.model.Environment;
import uk.dangrew.abm.model.EnvironmentPosition;
import uk.dangrew.abm.model.Heading;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class SimulationUITest {

   private Environment environment;
   private Agent agent;
   private SimulationUI systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      environment = new Environment( 100, 100 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 0, 0 ), 100 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 99, 0 ), 100 );
      
      environment.applyVerticalBoundary( new EnvironmentPosition( 30, 20 ), 40 );
      environment.applyVerticalBoundary( new EnvironmentPosition( 30, 50 ), 40 );
      environment.applyVerticalBoundary( new EnvironmentPosition( 30, 80 ), 40 );
      
      agent = new AgentImpl( new EnvironmentPosition( 5, 5 ), new Heading( 8, 2 ) );
      environment.monitorAgent( agent );
      System.out.println( agent.position() );
      
      systemUnderTest = new SimulationUI( environment );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      while( true ) {
         Thread.sleep( 500 );
         
         agent.move( environment );
         System.out.println( agent.position() );
      }
   }//End Method
   
}//End Class
