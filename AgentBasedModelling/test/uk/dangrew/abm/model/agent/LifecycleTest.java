package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class LifecycleTest {

   @Mock private Random randomizer;
   @Mock private Environment environment;
   private ControllableAgent agent;
   private Lifecycle systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      agent = new AgentImpl( new EnvironmentPosition( 0, 0 ), new Heading( 0, 0 ) );
      systemUnderTest = new Lifecycle( randomizer );
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( agent );
   }//End Method

   @Test public void shouldAgeLinearly() {
      for ( int i = 0; i < 40; i++ ) {
         systemUnderTest.age( environment );
         assertThat( agent.age().get(), is( i + 1 ) );
      }
   }//End Method
   
   @Test public void shouldHaveBasePlusRandomAge() {
      when( randomizer.nextInt( Lifecycle.MAX_AGE_BOOST ) ).thenReturn( 10 );
      systemUnderTest.birth();
      assertThat( agent.age().get(), is( 0 ) );
      assertThat( agent.lifeExpectancy().get(), is( Lifecycle.BASE_AGE + 10 ) );
   }//End Method
   
   @Test public void shouldDieWhenAgeLimitReached(){
      when( randomizer.nextInt( Lifecycle.MAX_AGE_BOOST ) ).thenReturn( 0 );
      systemUnderTest.birth();
      agent.setAge( agent.lifeExpectancy().get() );
      assertThat( systemUnderTest.getAgeBracket(), is( AgeBracket.Complete ) );
   }//End Method
   
   @Test public void shouldRemoveFromEnvironmentAfterDeath(){
      systemUnderTest.birth();
      agent.setAge( agent.lifeExpectancy().get() );
      
      for ( int i = 0; i < Lifecycle.REMOVE_FROM_ENVIRONMENT_AFTER; i++ ) {
         systemUnderTest.age( environment );
         verify( environment, never() ).cleanAgentUp( agent );
      }
      systemUnderTest.age( environment );
      verify( environment ).cleanAgentUp( agent );
   }//End Method
   
   @Test public void shouldNotContinueToAgeAfterCleanUp(){
      shouldRemoveFromEnvironmentAfterDeath();
      int ageAtCleanUp = agent.age().get();
      systemUnderTest.age( environment );
      assertThat( agent.age().get(), is( ageAtCleanUp ) );
   }//End Method
   
   @Test public void shouldBeWithinAgeBrackets(){
      agent.setAge( 1 );
      assertThat( systemUnderTest.getAgeBracket(), is( AgeBracket.Infant ) );
      agent.setAge( Lifecycle.YOUTH_COME_OF_AGE );
      assertThat( systemUnderTest.getAgeBracket(), is( AgeBracket.Youth ) );
      agent.setAge( Lifecycle.ADULT_COME_OF_AGE );
      assertThat( systemUnderTest.getAgeBracket(), is( AgeBracket.Adult ) );
      agent.setAge( Lifecycle.ELDER_COME_OF_AGE );
      assertThat( systemUnderTest.getAgeBracket(), is( AgeBracket.Elder ) );
   }//End Method

   @Test public void shouldDecideMaleAndMaleParentHood(){
      when( randomizer.nextBoolean() ).thenReturn( true );
      
      ControllableAgent agent = mock( ControllableAgent.class );
      systemUnderTest = new Lifecycle( randomizer );
      systemUnderTest.associate( agent );
      
      systemUnderTest.birth();
      verify( agent ).setGender( Mockito.eq( Gender.Male ), Mockito.isA( MaleParentHood.class ) );
   }//End Method
   
   @Test public void shouldDecideFemaleAndFemaleParentHood(){
      when( randomizer.nextBoolean() ).thenReturn( false );
      
      ControllableAgent agent = mock( ControllableAgent.class );
      systemUnderTest = new Lifecycle( randomizer );
      systemUnderTest.associate( agent );
      
      systemUnderTest.birth();
      verify( agent ).setGender( Mockito.eq( Gender.Female ), Mockito.isA( FemaleParentHood.class ) );
   }//End Method
   
   @Test public void shouldRandomizeMatingCycle(){
      when( randomizer.nextInt( Lifecycle.MAXIMUM_MATING_CYCLE ) ).thenReturn( 13 );
      systemUnderTest.birth();
      assertThat( agent.matingCycle(), is( 13 ) );
   }//End Method
   
   @Test public void shouldDieIfStoppedInPlaceForTooLong(){
      systemUnderTest.age( environment );
      for( int i = 0; i < Lifecycle.STATIONARY_LIMIT + 1; i++ ) {
         systemUnderTest.age( environment );
      }
      assertThat( agent.age().get(), is( agent.lifeExpectancy().get() ) );
   }//End Method
   
   @Test public void shouldNotDieIfMovingAround(){
      systemUnderTest.age( environment );
      for( int i = 0; i < Lifecycle.STATIONARY_LIMIT; i++ ) {
         systemUnderTest.age( environment );
      }
      agent.setPosition( new EnvironmentPosition( 100, 100 ) );
      systemUnderTest.age( environment );
      assertThat( agent.age().get(), is( greaterThan( Lifecycle.STATIONARY_LIMIT ) ) );
   }//End Method
   
   @Test public void shouldBeCleanedUpAfterStationary(){
      systemUnderTest.age( environment );
      for( int i = 0; i < Lifecycle.STATIONARY_LIMIT + 1; i++ ) {
         systemUnderTest.age( environment );
      }
      for( int i = 0; i < Lifecycle.REMOVE_FROM_ENVIRONMENT_AFTER + 1; i++ ) {
         systemUnderTest.age( environment );
      }
      assertThat( agent.age().get(), is( agent.lifeExpectancy().get() + Lifecycle.REMOVE_FROM_ENVIRONMENT_AFTER ) );
      verify( environment ).cleanAgentUp( agent );
   }//End Method
   
}//End Class
