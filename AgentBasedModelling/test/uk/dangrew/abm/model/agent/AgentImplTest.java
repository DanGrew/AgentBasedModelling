package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.agent.AgentImpl;
import uk.dangrew.abm.model.agent.Heading;
import uk.dangrew.abm.model.agent.HeadingAdjuster;
import uk.dangrew.abm.model.agent.MovementInterpolator;
import uk.dangrew.abm.model.agent.NeighbourHood;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class AgentImplTest {

   private static final int H_VELOCITY = 4;
   private static final int V_VELOCITY = 5;
   
   @Mock private MovementInterpolator interpolator;
   @Mock private HeadingAdjuster headingAdjuster;
   @Mock private NeighbourHood neighbourHood;
   @Mock private Environment environment;
   private AgentImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new AgentImpl( 
               interpolator, headingAdjuster, neighbourHood, new EnvironmentPosition( 5, 7 ), new Heading( V_VELOCITY, H_VELOCITY ) 
      );
   }//End Method
   
   @Test public void shouldBeAssociatedWithInterpolator(){
      verify( interpolator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldBeAssociatedWithHeadingAdjuster(){
      verify( headingAdjuster ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldBeAssociatedWithNeighbourHood(){
      verify( neighbourHood ).associate( systemUnderTest );
   }//End Method

   @Test public void shouldBeInInitialPosition() {
      assertThat( systemUnderTest.position().get(), is( new EnvironmentPosition( 5, 7 ) ) );
   }//End Method
   
   @Test public void shouldHaveInitialHeading() {
      assertThat( systemUnderTest.heading().get(), is( new Heading( V_VELOCITY, H_VELOCITY ) ) );
   }//End Method
   
   @Test public void shouldSetPosition(){
      systemUnderTest.setPosition( new EnvironmentPosition( 200, 100 ) );
      assertThat( systemUnderTest.position().get(), is( new EnvironmentPosition( 200, 100 ) ) );
   }//End Method
   
   @Test public void shouldSetHeading(){
      systemUnderTest.setHeading( new Heading( 200, 100 ) );
      assertThat( systemUnderTest.heading().get(), is( new Heading( 200, 100 ) ) );
   }//End Method

   @Test public void shouldNotChangeHeadingIfNotMovedIntoBoundary(){
      when( interpolator.move( environment ) ).thenReturn( false );
      systemUnderTest.move( environment );
      verify( interpolator ).move( environment );
      verify( headingAdjuster, never() ).changeHeading();
   }//End Method
   
   @Test public void shouldChangeHeadingIfMovedIntoBoundary(){
      when( interpolator.move( environment ) ).thenReturn( true );
      systemUnderTest.move( environment );
      verify( interpolator ).move( environment );
      verify( headingAdjuster ).changeHeading();
   }//End Method
   
   @Test public void shouldUnconditionallyRespondToNeighbourHood(){
      systemUnderTest.move( environment );
      verify( neighbourHood ).respondToNeighbours( environment );
   }//End Method
   
}//End Class
