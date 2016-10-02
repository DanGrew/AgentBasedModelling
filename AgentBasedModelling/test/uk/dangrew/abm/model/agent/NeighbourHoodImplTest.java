package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
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

   @Test public void shouldTryToMatchHeadingOfSingleAgentInImmediate() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( -10, -10 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.respondToNeighbours(), is( true ) );
      assertThat( subjectAgent.heading().get(), is( agent1.heading().get() ) );
   }//End Method
   
   @Test public void shouldTryToMatchHeadingOfThoseInImmediate() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( -9, 0 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent2.setHeading( new Heading( -9, -3 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      agent3.setHeading( new Heading( -6, -3 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.respondToNeighbours(), is( true ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( -8, -2 ) ) );
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
   
   @Test public void shouldNotRespondToNeighboursIfNotCloseEnough(){
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.respondToNeighbours(), is( false ) );
   }//End Method
   
   @Test public void shouldHeadTowardsThoseInOutsideNeighbourhood() {
//      fail( "Not yet implemented" );
   }//End Method
   
   @Test public void shouldIdentifyNoChangeInHeadingAsAResult() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( 10, 10 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.respondToNeighbours(), is( false ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( 10, 10 ) ) );
   }//End Method
   
   @Test public void shouldTakeTheLeadIfVelocityIsZero() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( 0, 0 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent2.setHeading( new Heading( 0, 0 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      agent3.setHeading( new Heading( 0, 0 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.respondToNeighbours(), is( false ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( 10, 10 ) ) );
   }//End Method

}//End Class
