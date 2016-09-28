package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class AgentImplTest {

   private static final int H_VELOCITY = 4;
   private static final int V_VELOCITY = 5;
   
   @Mock private MovementInterpolator interpolator;
   @Mock private HeadingAdjuster headingAdjuster;
   @Mock private NeighbourHood neighbourHood;
   @Mock private Lifecycle lifecyle;
   @Mock private Environment environment;
   private AgentImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new AgentImpl( 
               interpolator, headingAdjuster, neighbourHood, lifecyle, 
               new EnvironmentPosition( 5, 7 ), new Heading( V_VELOCITY, H_VELOCITY ) 
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
   
   @Test public void shouldProvideAge(){
      assertThat( systemUnderTest.age().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldSetAge(){
      assertThat( systemUnderTest.age().get(), is( nullValue() ) );
      systemUnderTest.setAge( 29 );
      assertThat( systemUnderTest.age().get(), is( 29 ) );
   }//End Method
   
   @Test public void shouldProvideLifeExpectancy(){
      assertThat( systemUnderTest.lifeExpectancy().get(), is( not( 0 ) ) );
   }//End Method
   
   @Test public void shouldSetLifeExpectancy(){
      systemUnderTest.setLifeExpectancy( 29 );
      assertThat( systemUnderTest.lifeExpectancy().get(), is( 29 ) );
   }//End Method
   
   @Test public void shouldBeBornUsingLifecyle(){
      verify( lifecyle ).birth();
   }//End Method
   
   @Test public void shouldAgeWithEachMove(){
      systemUnderTest.move( environment );
      verify( lifecyle ).age( environment );
      
      systemUnderTest.move( environment );
      verify( lifecyle, times( 2 ) ).age( environment );
   }//End Method
   
   @Test public void shouldProvideAgeBracket(){
      when( lifecyle.getAgeBracket() ).thenReturn( AgeBracket.Youth );
      assertThat( systemUnderTest.getAgeBracket(), is( AgeBracket.Youth ) );
      verify( lifecyle ).getAgeBracket();
   }//End Method
   
   @Test public void shouldNotMoveIfComplete(){
      when( lifecyle.getAgeBracket() ).thenReturn( AgeBracket.Complete );
      systemUnderTest.move( environment );
      verify( neighbourHood, never() ).respondToNeighbours( environment );
      verify( interpolator, never() ).move( environment );
   }//End Method
}//End Class
