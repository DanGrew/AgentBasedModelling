package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.abm.model.environment.Environment;

public class SwarmingNatureImplTest {

   private ControllableAgent agent1;
   private ControllableAgent agent2;
   private ControllableAgent agent3;
   private ControllableAgent agent4;
   
   private ControllableAgent subjectAgent;
   @Mock private NeighbourHood neighbourHood;
   private ObservableList< Agent > neighbours;
   private Environment environment;
   
   @Mock private Random randomizer;
   private SwarmingNature systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      environment = new Environment( 10, 10 );
      neighbours = FXCollections.observableArrayList();
      when( neighbourHood.neighbours() ).thenReturn( neighbours );
      
      agent1 = new AgentImpl( environmentPosition( 0, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent1 );
      agent2 = new AgentImpl( environmentPosition( 1, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent2 );
      agent3 = new AgentImpl( environmentPosition( 2, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent3 );
      agent4 = new AgentImpl( environmentPosition( 3, 0 ), new Heading( 1, 1 ) );
      environment.monitorAgent( agent4 );
      subjectAgent = new AgentImpl( environmentPosition( 5, 5 ), new Heading( 10, 10 ) );
      
      systemUnderTest = new SwarmingNatureImpl( randomizer );
      systemUnderTest.associate( subjectAgent, neighbourHood );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociationMultipleTimes(){
      systemUnderTest.associate( subjectAgent, neighbourHood );
   }//End Method

   @Test public void shouldRandomizeNewVelocity(){
      when( randomizer.nextInt( FixedHeading.values().length ) ).thenReturn( 4 );
      
      systemUnderTest.randomizeHeading();
      assertThat( subjectAgent.heading().get(), is( FixedHeading.values()[ 4 ].heading() ) );
   }//End Method
   
   @Test public void shouldTryToMatchHeadingOfSingleAgentInImmediate() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( FixedHeading.NE_19.heading() );
      
      neighbours.addAll( agent1 );
      assertThat( systemUnderTest.respondToNeighbours(), is( true ) );
      assertThat( subjectAgent.heading().get(), is( FixedHeading.NE_19.heading() ) );
   }//End Method
   
   @Test public void shouldTryToMatchHeadingOfThoseInImmediate() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( -9, 0 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent2.setHeading( new Heading( -9, -3 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      agent3.setHeading( new Heading( -6, -3 ) );
      
      neighbours.addAll( agent1, agent2, agent3 );
      assertThat( systemUnderTest.respondToNeighbours(), is( true ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( -8, -2 ) ) );
   }//End Method
   
   @Test public void shouldNotRespondToNeighboursIfNotCloseEnough(){
      assertThat( systemUnderTest.respondToNeighbours(), is( false ) );
   }//End Method
   
   @Test public void shouldHeadTowardsThoseInOutsideNeighbourhood() {
//      fail( "Not yet implemented" );
   }//End Method
   
   @Test public void shouldIdentifyNoChangeInHeadingAsAResult() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( FixedHeading.SE_55.heading() );
      subjectAgent.setHeading( FixedHeading.SE_55.heading() );
      
      neighbours.addAll( agent1 );
      assertThat( systemUnderTest.respondToNeighbours(), is( false ) );
      assertThat( subjectAgent.heading().get(), is( FixedHeading.SE_55.heading() ) );
   }//End Method
   
   @Test public void shouldTakeTheLeadIfVelocityIsZero() {
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent1.setHeading( new Heading( 0, 0 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent2.setHeading( new Heading( 0, 0 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      agent3.setHeading( new Heading( 0, 0 ) );
      
      neighbours.addAll( agent1, agent2, agent3 );
      assertThat( systemUnderTest.respondToNeighbours(), is( false ) );
      assertThat( subjectAgent.heading().get(), is( new Heading( 10, 10 ) ) );
   }//End Method

}//End Class
