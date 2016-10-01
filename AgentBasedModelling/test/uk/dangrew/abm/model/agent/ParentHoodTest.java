package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.environment.Environment;

public class ParentHoodTest {
   
   @Mock private ControllableAgent agent;
   private ParentHood systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ParentHood() {
         @Override public void mingle( Environment environment ) {}
         @Override public boolean mate( Agent agent ) { return false; }
         @Override public void associate( Agent agent, NeighbourHood neighbourHood ) {}
      };
   }//End Method

   @Test public void shouldBeOfMatingAge() {
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Adult );
      assertThat( systemUnderTest.isMatingAge( agent ), is( true ) );
      
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Elder );
      assertThat( systemUnderTest.isMatingAge( agent ), is( true ) );
   }//End Method
   
   @Test public void shouldNotBeOfMatingAge() {
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Infant );
      assertThat( systemUnderTest.isMatingAge( agent ), is( false ) );
      
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Complete );
      assertThat( systemUnderTest.isMatingAge( agent ), is( false ) );
      
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Youth );
      assertThat( systemUnderTest.isMatingAge( agent ), is( false ) );
   }//End Method

}//End Class
