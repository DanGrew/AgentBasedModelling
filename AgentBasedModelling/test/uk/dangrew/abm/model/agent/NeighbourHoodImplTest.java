package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javafx.collections.ListChangeListener;
import uk.dangrew.abm.model.environment.Environment;

public class NeighbourHoodImplTest {

   private static final int SUBJECT_V = 5;
   private static final int SUBJECT_H = 5;
   
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
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Separation ), containsInAnyOrder( agent1, agent2, agent3 ) );
   }//End Method
   
   @Test public void shouldClearAgentsOutEachIdentifyProcess(){
      agent1.setPosition( environmentPosition( 6, 6 ) );
      agent2.setPosition( environmentPosition( 7, 3 ) );
      agent3.setPosition( environmentPosition( 3, 7 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Separation ), containsInAnyOrder( agent1, agent2, agent3 ) );
   }//End Method
   
   @Test public void shouldProvideNeighboursBasedOnDistance(){
      agent1.setPosition( environmentPosition( 6, 5 ) );
      agent2.setPosition( environmentPosition( ( int )( SUBJECT_V + NeighbourHoodRange.Separation.distanceFromAgent() ), 5 ) );
      agent3.setPosition( environmentPosition( ( int )( SUBJECT_V + NeighbourHoodRange.Cohesion.distanceFromAgent() ), 5 ) );
      agent4.setPosition( environmentPosition( 1000, 5 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Separation ), containsInAnyOrder( agent1, agent2 ) );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Cohesion ), containsInAnyOrder( agent1, agent2, agent3 ) );
   }//End Method
   
   @Test public void shouldFindMultipleNeighboursInLowerRange(){
      agent1.setPosition( environmentPosition( 6, 5 ) );
      agent2.setPosition( environmentPosition( 5, 6 ) );
      agent3.setPosition( environmentPosition( 4, 5 ) );
      agent4.setPosition( environmentPosition( 5, 4 ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Separation ), containsInAnyOrder( agent1, agent2, agent3, agent4 ) );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Cohesion ), containsInAnyOrder( agent1, agent2, agent3, agent4 ) );
   }//End Method
   
   @Test public void shouldFindMultipleNeighboursInHigherRange(){
      agent1.setPosition( environmentPosition( ( int )( SUBJECT_V + NeighbourHoodRange.Cohesion.distanceFromAgent() ), 5 ) );
      agent2.setPosition( environmentPosition( ( int )( SUBJECT_V - NeighbourHoodRange.Cohesion.distanceFromAgent() ), 5 ) );
      agent3.setPosition( environmentPosition( 5, ( int )( SUBJECT_H + NeighbourHoodRange.Cohesion.distanceFromAgent() ) ) );
      agent4.setPosition( environmentPosition( 5, ( int )( SUBJECT_H - NeighbourHoodRange.Cohesion.distanceFromAgent() ) ) );
      
      systemUnderTest.identifyNeighbourHood( environment );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Separation ), is( empty() ) );
      assertThat( systemUnderTest.neighbours( NeighbourHoodRange.Cohesion ), containsInAnyOrder( agent1, agent2, agent3, agent4 ) );
   }//End Method
   
   @Test public void shouldOnlyUpdateNeighboursInBulk(){
      agent1.setPosition( environmentPosition( 6, 5 ) );
      agent2.setPosition( environmentPosition( 5, 6 ) );
      agent3.setPosition( environmentPosition( 4, 5 ) );
      agent4.setPosition( environmentPosition( 5, 4 ) );

      @SuppressWarnings("unchecked") //mocking with generics
      ListChangeListener< Agent > listener = mock( ListChangeListener.class );
      
      systemUnderTest.neighbours( NeighbourHoodRange.Separation ).addListener( listener );
      systemUnderTest.identifyNeighbourHood( environment );
      
      verify( listener, times( 1 ) ).onChanged( Mockito.any() );
   }//End Method
   
}//End Class
