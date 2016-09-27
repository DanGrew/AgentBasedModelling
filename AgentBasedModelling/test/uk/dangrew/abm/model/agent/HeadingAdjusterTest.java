package uk.dangrew.abm.model.agent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.agent.ControllableAgent;
import uk.dangrew.abm.model.agent.Heading;
import uk.dangrew.abm.model.agent.HeadingAdjuster;

public class HeadingAdjusterTest {

   @Mock private ControllableAgent agent;
   @Mock private Random randomizer;
   private HeadingAdjuster systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new HeadingAdjuster( randomizer );
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociationMultipleTimes(){
      systemUnderTest.associate( agent );
   }//End Method

   @Test public void shouldRandomizeNewVelocityWithoutInversion(){
      when( randomizer.nextInt( HeadingAdjuster.VELOCITY_DISTRIBUTION ) ).thenReturn( 4 );
      when( randomizer.nextBoolean() ).thenReturn( true, true );
      
      systemUnderTest.changeHeading();
      verify( agent ).setHeading( new Heading( 6, 4 ) );
   }//End Method
   
   @Test public void shouldRandomizeNewVelocityWithInversion(){
      when( randomizer.nextInt( HeadingAdjuster.VELOCITY_DISTRIBUTION ) ).thenReturn( 1 );
      when( randomizer.nextBoolean() ).thenReturn( false, false );
      
      systemUnderTest.changeHeading();
      verify( agent ).setHeading( new Heading( -9, -1 ) );
   }//End Method

}//End Class
