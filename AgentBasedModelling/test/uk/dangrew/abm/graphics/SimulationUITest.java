package uk.dangrew.abm.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
   private List< Agent > agents;
   private SimulationUI systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      environment = new Environment( 100, 100 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 0, 0 ), 100 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 99, 0 ), 100 );
      
      environment.applyVerticalBoundary( new EnvironmentPosition( 30, 20 ), 40 );
      environment.applyVerticalBoundary( new EnvironmentPosition( 30, 50 ), 40 );
      environment.applyVerticalBoundary( new EnvironmentPosition( 30, 80 ), 40 );
      
      agents = new ArrayList<>();
      
      systemUnderTest = new SimulationUI( environment );
      
      Random random = new Random();
      for ( int i = 0; i < 100; i++ ) {
         Agent agent = new AgentImpl( new EnvironmentPosition( random.nextInt( 100 ), random.nextInt( 100 ) ), new Heading( 8, 2 ) );
         agents.add( agent );
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
         
         agents.forEach( a -> a.move( environment ) );
      }
   }//End Method
   
}//End Class
