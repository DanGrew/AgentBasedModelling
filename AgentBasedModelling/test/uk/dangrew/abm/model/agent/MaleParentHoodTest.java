package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class MaleParentHoodTest {

   @Captor ArgumentCaptor< Agent > agentCaptor;
   @Mock private Agent femaleNeighbour1;
   @Mock private Agent femaleNeighbour2;
   @Mock private Agent maleNeighbour;
   
   @Mock private Random random;
   private ControllableAgent agent;
   @Mock private Environment environment;
   @Mock private NeighbourHood neighbourHood;
   private ObservableList< Agent > neighbours;
   private MaleParentHood systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      neighbours = FXCollections.observableArrayList();
      when( neighbourHood.neighbours() ).thenReturn( neighbours );
      when( femaleNeighbour1.gender() ).thenReturn( Gender.Female );
      when( femaleNeighbour1.getAgeBracket() ).thenReturn( AgeBracket.Adult );
      when( femaleNeighbour2.gender() ).thenReturn( Gender.Female );
      when( femaleNeighbour2.getAgeBracket() ).thenReturn( AgeBracket.Elder );
      when( maleNeighbour.gender() ).thenReturn( Gender.Male );
      
      agent = new AgentImpl( new EnvironmentPosition( 0, 0 ), new Heading( 10, 10 ) );
      agent.setAge( agent.lifeExpectancy().get() - 1 );
      systemUnderTest = new MaleParentHood( random );
      systemUnderTest.associate( agent, neighbourHood );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations() {
      systemUnderTest.associate( agent, neighbourHood );
   }//End Method
   
   @Test public void shouldMateWithRandomFemaleInNeighbour(){
      when( random.nextInt( 2 ) ).thenReturn( 1 );
      neighbours.add( femaleNeighbour1 );
      neighbours.add( femaleNeighbour2 );
      primeForMating();
      systemUnderTest.mingle( environment );
      verify( femaleNeighbour2 ).mate( agent );
   }//End Method
   
   @Test public void shouldMateWithFemalesInTurnWithCoolOff(){
      when( random.nextInt( 2 ) ).thenReturn( 0, 1 );
      neighbours.add( femaleNeighbour1 );
      neighbours.add( femaleNeighbour2 );
      primeForMating();
      systemUnderTest.mingle( environment );
      verify( femaleNeighbour1 ).mate( agent );
      for ( int i = 0; i < MaleParentHood.MATING_COOL_OFF_PERIOD; i++ ) {
         systemUnderTest.mingle( environment );
         assertThat( systemUnderTest.isReadyForMate(), is( false ) );
      }
      primeForMating();
      systemUnderTest.mingle( environment );
      verify( femaleNeighbour2 ).mate( agent );
   }//End Method
   
   @Test public void shouldNotAcceptMates(){
      assertThat( systemUnderTest.mate( femaleNeighbour1 ), is( false ) );
      assertThat( systemUnderTest.mate( mock( Agent.class ) ), is( false ) );
   }//End Method
   
   @Test public void shouldOnlyMateAfterCertainAge(){
      agent.setAge( 0 );
      neighbours.add( femaleNeighbour1 );
      for ( int i = 0; i < agent.matingCycle() + 1; i++ ) {
         systemUnderTest.mingle( environment );
      }
      systemUnderTest.mingle( environment );
      verify( femaleNeighbour1, never() ).mate( agent );
   }//End Method
   
   @Test public void shouldNotMateWithMales(){
      neighbours.add( maleNeighbour );
      for ( int i = 0; i < agent.matingCycle() + 1; i++ ) {
         systemUnderTest.mingle( environment );
      }
      systemUnderTest.mingle( environment );
      verify( maleNeighbour, never() ).mate( agent );
   }//End Method
   
   @Test public void shouldMateWithAnotherIfAttemptFails(){
      //not important yet
   }//End Method
   
   @Test public void shouldNotMateWithAgentWhoIsntOldEnough(){
      when( femaleNeighbour1.getAgeBracket() ).thenReturn( AgeBracket.Infant );
      
      neighbours.add( femaleNeighbour1 );
      primeForMating();
      assertThat( systemUnderTest.isReadyForMate(), is( true ) );
      verify( femaleNeighbour1, never() ).mate( femaleNeighbour1 );
   }//End Method
   
   @Test public void shouldOnlyMateWhenMatingDriveIsFilled(){
      neighbours.add( femaleNeighbour1 );
      
      for ( int i = 0; i < agent.matingCycle(); i++ ) {
         assertThat( systemUnderTest.isReadyForMate(), is( false ) );
         systemUnderTest.mingle( environment );
         verify( femaleNeighbour1, never() ).mate( agent );
      }
      systemUnderTest.mingle( environment );
      assertThat( systemUnderTest.isReadyForMate(), is( true ) );
      systemUnderTest.mingle( environment );
      verify( femaleNeighbour1 ).mate( agent );
   }//End Method

   private void primeForMating(){
      for ( int i = 0; i < agent.matingCycle() + 1; i++ ) {
         systemUnderTest.mingle( environment );
      }
      assertThat( systemUnderTest.isReadyForMate(), is( true ) );
   }//End Method
   
}//End Class
