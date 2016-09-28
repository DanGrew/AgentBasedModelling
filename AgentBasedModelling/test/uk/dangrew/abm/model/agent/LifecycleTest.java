package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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

}//End Class
