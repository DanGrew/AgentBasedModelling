package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.abm.model.agent.AgentImpl;
import uk.dangrew.abm.model.agent.ControllableAgent;
import uk.dangrew.abm.model.agent.Heading;
import uk.dangrew.abm.model.agent.NeighbourHoodImpl;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

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
      
      agent1 = new AgentImpl( new EnvironmentPosition( 0, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent1 );
      agent2 = new AgentImpl( new EnvironmentPosition( 1, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent2 );
      agent3 = new AgentImpl( new EnvironmentPosition( 2, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent3 );
      agent4 = new AgentImpl( new EnvironmentPosition( 3, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent4 );
      
      subjectAgent = new AgentImpl( new EnvironmentPosition( 5, 5 ), new Heading( 10, 10 ) );
      environment.monitorAgent( subjectAgent );
      systemUnderTest = new NeighbourHoodImpl();
      systemUnderTest.associate( subjectAgent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( subjectAgent );
   }//End Method

   @Test public void shouldTryToMatchHeadingOfSingleAgentInImmediate() {
      agent1.setPosition( new EnvironmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( -10, -10 ) );
      
      assertThat( systemUnderTest.respondToNeighbours( environment ), is( true ) );
      assertThat( subjectAgent.heading().get(), is( agent1.heading().get() ) );
   }//End Method
   
   @Test public void shouldTryToMatchHeadingOfThoseInImmediate() {
      agent1.setPosition( new EnvironmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( -9, 0 ) );
      agent2.setPosition( new EnvironmentPosition( 7, 3 ) );
      agent2.setHeading( new Heading( -9, -3 ) );
      agent3.setPosition( new EnvironmentPosition( 3, 7 ) );
      agent3.setHeading( new Heading( -6, -3 ) );
      
      assertThat( systemUnderTest.respondToNeighbours( environment ), is( true ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( -8, -2 ) ) );
   }//End Method
   
   @Test public void shouldNotRespondToNeighboursIfNotCloseEnough(){
      assertThat( systemUnderTest.respondToNeighbours( environment ), is( false ) );
   }//End Method
   
   @Test public void shouldHeadTowardsThoseInOutsideNeighbourhood() {
//      fail( "Not yet implemented" );
   }//End Method
   
   @Test public void shouldIdentifyNoChangeInHeadingAsAResult() {
      agent1.setPosition( new EnvironmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( 10, 10 ) );
      
      assertThat( systemUnderTest.respondToNeighbours( environment ), is( false ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( 10, 10 ) ) );
   }//End Method
   
   @Test public void shouldTakeTheLeadIfVelocityIsZero() {
      agent1.setPosition( new EnvironmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( 0, 0 ) );
      agent2.setPosition( new EnvironmentPosition( 7, 3 ) );
      agent2.setHeading( new Heading( 0, 0 ) );
      agent3.setPosition( new EnvironmentPosition( 3, 7 ) );
      agent3.setHeading( new Heading( 0, 0 ) );
      
      assertThat( systemUnderTest.respondToNeighbours( environment ), is( false ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( 10, 10 ) ) );
   }//End Method

}//End Class
