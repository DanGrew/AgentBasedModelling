package uk.dangrew.abm.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AgentImplTest {

   @Mock private Environment environment;
   private Agent systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new AgentImpl( new EnvironmentPosition( 10, 1 ) );
   }//End Method

   @Test public void shouldBeInInitialPosition() {
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( 10, 1 ) ) );
   }//End Method

   @Test public void shouldMoveInEnvironment(){
      when( environment.isSpace( Mockito.any() ) ).thenReturn( true );
      systemUnderTest.move( environment );
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( 10, 2 ) ) );
   }//End Method
   
   @Test public void shouldMoveInOppositeDirectionWhenBoundaryFound(){
      when( environment.isSpace( Mockito.any() ) ).thenReturn( false );
      when( environment.isBoundary( Mockito.any() ) ).thenReturn( true );
      systemUnderTest.move( environment );
      assertThat( systemUnderTest.position(), is( new EnvironmentPosition( 10, 0 ) ) );
   }//End Method
}//End Class
