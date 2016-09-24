package uk.dangrew.abm.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AgentImplTest {

   private static final int H_VELOCITY = 4;
   private static final int V_VELOCITY = 5;
   
   @Mock private Random randomizer;
   @Mock private Environment environment;
   private Agent systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new AgentImpl( randomizer, new EnvironmentPosition( 5, 7 ), V_VELOCITY, H_VELOCITY );
   }//End Method

   @Test public void shouldBeInInitialPosition() {
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( 5, 7 ) ) );
   }//End Method

   @Test public void shouldMoveInEnvironment(){
      when( environment.isSpace( Mockito.any() ) ).thenReturn( true );
      systemUnderTest.move( environment );
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( 10, 11 ) ) );
   }//End Method
   
   @Test public void shouldRandomizeNewVelocityAndApplyMovementRegardless(){
      when( environment.isSpace( Mockito.any() ) ).thenReturn( false );
      when( environment.isBoundary( Mockito.any() ) ).thenReturn( true );
      
      when( randomizer.nextInt( AgentImpl.VELOCITY_DISTRIBUTION ) ).thenReturn( 3 );
      
      systemUnderTest.move( environment );
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( -2, 4 ) ) );
      
      systemUnderTest.move( environment );
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( 5, 7 ) ) );
   }//End Method
}//End Class
