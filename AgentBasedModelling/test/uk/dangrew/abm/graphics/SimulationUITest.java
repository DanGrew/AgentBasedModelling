package uk.dangrew.abm.graphics;

import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.abm.model.agent.Agent;
import uk.dangrew.abm.model.agent.AgentImpl;
import uk.dangrew.abm.model.agent.Heading;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class SimulationUITest {

   private Environment environment;
   private SimulationUI systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      environment = new Environment( 50, 50 );
//      environment.applyHorizontalBoundary( 0, 0, 100 );
//      environment.applyHorizontalBoundary( 99, 0, 100 );
//      environment.applyVerticalBoundary( 0, 0, 100 );
//      environment.applyVerticalBoundary( 0, 99, 100 );
      
//      environment.applyVerticalBoundary( 30, 20, 40 );
//      environment.applyHorizontalBoundary( 70, 20, 40 );
//      environment.applyVerticalBoundary( 70, 60, -40 );
//      environment.applyHorizontalBoundary( 30, 60, 40 );
//      environment.applyVerticalBoundary( 30, 100, 40 );
//      environment.applyHorizontalBoundary( 70, 100, 40 );
      
      
      systemUnderTest = new SimulationUI( environment );
      
      Random random = new Random();
      for ( int i = 0; i < 10; i++ ) {
         Agent agent = new AgentImpl( environmentPosition( random.nextInt( 50 ), random.nextInt( 50 ) ), new Heading( random.nextInt( 10 ), random.nextInt( 10 ) ) );
         environment.monitorAgent( agent );
         System.out.println( agent.position() );
      }
   }//End Method

//   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 2000 );
      while( true ) {
         Thread.sleep( 50 );
         
         List< Agent > agents = new ArrayList<>( environment.agents().values() );
         Collections.shuffle( agents );
         agents.forEach( a -> a.move( environment ) );
      }
   }//End Method
   
}//End Class
