package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.translate;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class FemaleParentHoodTest {

   @Captor ArgumentCaptor< Agent > agentCaptor;
   @Mock private Agent maleNeighbour;
   @Mock private Agent femaleNeighbour;
   
   @Mock private Random random;
   private ControllableAgent agent;
   private Environment environment;
   @Mock private NeighbourHood neighbourHood;
   private FemaleParentHood systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      when( maleNeighbour.gender() ).thenReturn( Gender.Male );
      when( maleNeighbour.getAgeBracket() ).thenReturn( AgeBracket.Adult );
      when( femaleNeighbour.gender() ).thenReturn( Gender.Female );
      when( femaleNeighbour.getAgeBracket() ).thenReturn( AgeBracket.Adult );
      
      environment = spy( new Environment( 100, 100 ) );
      agent = new AgentImpl( environmentPosition( 10, 10 ), new Heading( 10, 10 ) );
      agent.setAge( agent.lifeExpectancy().get() - 1 );
      systemUnderTest = new FemaleParentHood( random );
      systemUnderTest.associate( agent, neighbourHood );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations() {
      systemUnderTest.associate( agent, neighbourHood );
   }//End Method
   
   @Test public void shouldHaveOffspringAfterPregnancy(){
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
      for ( int i = 0; i < FemaleParentHood.PREGNANCY_PERIOD; i++ ) {
         systemUnderTest.mingle( environment );
      }
      
      verify( environment, never() ).monitorAgent( Mockito.any() );
      systemUnderTest.mingle( environment );
      verify( environment ).monitorAgent( agentCaptor.capture() );
      
      assertThat( agentCaptor.getValue().position().get(), is( translate( agent.position().get(), -1, -1 ) ) );
      assertThat( agentCaptor.getValue().heading().get(), is( agent.heading().get() ) );
      
      systemUnderTest.mingle( environment );
      verify( environment, times( 1 ) ).monitorAgent( Mockito.any() );
   }//End Method
   
   @Test public void shouldNotMateWithMoreThanOneAtOnce(){
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( false ) );
      Agent anotherMale = mock( Agent.class );
      when( anotherMale.gender() ).thenReturn( Gender.Male );
      assertThat( systemUnderTest.mate( anotherMale ), is( false ) );
   }//End Method
   
   @Test public void shouldOnlyMateGivenChance(){
      when( random.nextInt( FemaleParentHood.MATING_DENOMINATOR ) ).thenReturn( 30 );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
   }//End Method
   
   @Test public void shouldNotMateIfNotGivenChance(){
      when( random.nextInt( FemaleParentHood.MATING_DENOMINATOR ) ).thenReturn( 50 );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( false ) );
   }//End Method
   
   @Test public void shouldOnlyReproduceAfterCertainAge(){
      agent.setAge( 0 );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
      for ( int i = 0; i < FemaleParentHood.PREGNANCY_PERIOD * 2; i++ ) {
         systemUnderTest.mingle( environment );
      }
      
      assertThat( environment.agents().isEmpty(), is( true ) );
   }//End Method
   
   @Test public void shouldIgnoreMatingFemale(){
      assertThat( systemUnderTest.mate( femaleNeighbour ), is( false ) );
   }//End Method
   
   @Test public void shouldNotMateWithAgentWhoIsntOldEnough(){
      when( maleNeighbour.getAgeBracket() ).thenReturn( AgeBracket.Infant );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( false ) );
   }//End Method
   
   @Test public void shouldWaitRecoveryPeriodBeforeMatingAgain(){
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
      for ( int i = 0; i < FemaleParentHood.PREGNANCY_PERIOD; i++ ) {
         systemUnderTest.mingle( environment );
      }
      systemUnderTest.mingle( environment );
      verify( environment ).monitorAgent( agentCaptor.capture() );
      
      assertThat( systemUnderTest.mate( maleNeighbour ), is( false ) );
      for ( int i = 0; i < FemaleParentHood.PREGNANCY_RECOVERY - 1; i++ ) {
         systemUnderTest.mingle( environment );
      }
      assertThat( systemUnderTest.mate( maleNeighbour ), is( false ) );
      systemUnderTest.mingle( environment );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
   }//End Method
   
   @Test public void shouldCheckAntiClockwiseAroundAgentForOffspringPosition(){
      EnvironmentPosition position = environmentPosition( 5, 5 );
      
      EnvironmentPosition first = environmentPosition( 4, 4 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( first ) );
      
      when( environment.isAvailable( first ) ).thenReturn( false );
      EnvironmentPosition second = environmentPosition( 5, 4 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( second ) );
      
      when( environment.isAvailable( second ) ).thenReturn( false );
      EnvironmentPosition third = environmentPosition( 6, 4 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( third ) );
      
      when( environment.isAvailable( third ) ).thenReturn( false );
      EnvironmentPosition fourth = environmentPosition( 6, 5 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( fourth ) );
      
      when( environment.isAvailable( fourth ) ).thenReturn( false );
      EnvironmentPosition fifth = environmentPosition( 6, 6 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( fifth ) );
      
      when( environment.isAvailable( fifth ) ).thenReturn( false );
      EnvironmentPosition sixth = environmentPosition( 5, 6 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( sixth ) );
      
      when( environment.isAvailable( sixth ) ).thenReturn( false );
      EnvironmentPosition seventh = environmentPosition( 4, 6 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( seventh ) );
      
      when( environment.isAvailable( seventh ) ).thenReturn( false );
      EnvironmentPosition eighth = environmentPosition( 4, 5 );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( eighth ) );
      
      when( environment.isAvailable( eighth ) ).thenReturn( false );
      assertThat( systemUnderTest.identifyOffspringInitialPosition( environment, position ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotHaveOffspringAndMissChanceIfNoSpace(){
      agent.setPosition( environmentPosition( 10, 10 ) );
      environment.applyHorizontalBoundary( 9, 9, 3 );
      environment.applyHorizontalBoundary( 11, 9, 3 );
      environment.applyHorizontalBoundary( 10, 9, 1 );
      environment.applyHorizontalBoundary( 9, 9, 3 );
      environment.applyHorizontalBoundary( 10, 11, 1 );
      
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( false ) );
      for ( int i = 0; i < FemaleParentHood.PREGNANCY_PERIOD + FemaleParentHood.PREGNANCY_RECOVERY; i++ ) {
         systemUnderTest.mingle( environment );
      }
      systemUnderTest.mingle( environment );
      verify( environment, never() ).monitorAgent( Mockito.any() );
      assertThat( systemUnderTest.mate( maleNeighbour ), is( true ) );
   }//End Method

}//End Class
