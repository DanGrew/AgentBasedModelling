package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.abm.model.environment.Environment;

public class NeighbourHoodImplTest {

   private ControllableAgent agent1;
   private ControllableAgent agent2;
   private ControllableAgent agent3;
   private ControllableAgent agent4;
   
   private ControllableAgent subjectAgent;
   private Environment environment;
   private NeighbourHoodImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      environment = new Environment( 10, 10 );
      
      agent1 = new AgentImpl( environmentPosition( 0, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent1 );
      agent2 = new AgentImpl( environmentPosition( 1, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent2 );
      agent3 = new AgentImpl( environmentPosition( 2, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent3 );
      agent4 = new AgentImpl( environmentPosition( 3, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent4 );
      
      subjectAgent = new AgentImpl( environmentPosition( 5, 5 ), new Heading( 10, 10 ) );
      environment.monitorAgent( subjectAgent );
      systemUnderTest = new NeighbourHoodImpl();
      systemUnderTest.associate( subjectAgent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( subjectAgent );
   }//End Method

   @Test public void shouldIdentifyAllAgentsInNeighbourHood() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.neighbours(), containsInAnyOrder( agent1, agent2, agent3 ) );
   }//End Method
   
   @Test public void shouldClearAgentsOutEachIdentifyProcess(){
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.neighbours(), containsInAnyOrder( agent1, agent2, agent3 ) );
   }//End Method

}//End Class
