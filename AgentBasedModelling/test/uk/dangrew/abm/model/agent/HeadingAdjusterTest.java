package uk.dangrew.abm.model.agent;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
      when( randomizer.nextInt( FixedHeading.values().length ) ).thenReturn( 4 );
      
      systemUnderTest.changeHeading();
      verify( agent ).setHeading( FixedHeading.values()[ 4 ].heading() );
   }//End Method
   
}//End Class
