package uk.dangrew.abm.graphics;

import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import java.util.ArrayList;
import java.util.Collection;
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
      
      environment = new Environment( 100, 100 );
      environment.applyHorizontalBoundary( 0, 0, 100 );
      environment.applyHorizontalBoundary( 99, 0, 100 );
      environment.applyVerticalBoundary( 0, 0, 100 );
      environment.applyVerticalBoundary( 0, 99, 100 );
      
      environment.applyVerticalBoundary( 30, 20, 40 );
      environment.applyVerticalBoundary( 30, 50, 40 );
      environment.applyVerticalBoundary( 30, 80, 40 );
      
      
      systemUnderTest = new SimulationUI( environment );
      
      Random random = new Random();
      for ( int i = 0; i < 100; i++ ) {
         Agent agent = new AgentImpl( environmentPosition( random.nextInt( 100 ), random.nextInt( 100 ) ), new Heading( 8, 2 ) );
         environment.monitorAgent( agent );
         System.out.println( agent.position() );
      }
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 2000 );
      while( true ) {
         Thread.sleep( 50 );
         
         Collection< Agent > agents = new ArrayList<>( environment.agents().values() );
         agents.forEach( a -> a.move( environment ) );
      }
   }//End Method
   
}//End Class
