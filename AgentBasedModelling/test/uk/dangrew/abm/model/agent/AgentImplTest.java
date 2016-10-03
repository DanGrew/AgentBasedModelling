package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.environment.Environment;

public class AgentImplTest {

   private static final int H_VELOCITY = 4;
   private static final int V_VELOCITY = 5;
   
   @Mock private MovementInterpolator interpolator;
   @Mock private SwarmingNatureImpl swarmingNature;
   @Mock private NeighbourHood neighbourHood;
   @Mock private Lifecycle lifecyle;
   @Mock private ParentHood parentHood;
   @Mock private Environment environment;
   private AgentImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new AgentImpl( 
               interpolator, swarmingNature, neighbourHood, lifecyle, 
               environmentPosition( 5, 7 ), new Heading( V_VELOCITY, H_VELOCITY ) 
      );
      systemUnderTest.setGender( Gender.Male, parentHood );
   }//End Method
   
   @Test public void shouldBeAssociatedWithInterpolator(){
      verify( interpolator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldBeAssociatedWithSwarmingNature(){
      verify( swarmingNature ).associate( systemUnderTest, neighbourHood );
   }//End Method
   
   @Test public void shouldBeAssociatedWithNeighbourHood(){
      verify( neighbourHood ).associate( systemUnderTest );
   }//End Method

   @Test public void shouldBeInInitialPosition() {
      assertThat( systemUnderTest.position().get(), is( environmentPosition( 5, 7 ) ) );
   }//End Method
   
   @Test public void shouldHaveInitialHeading() {
      assertThat( systemUnderTest.heading().get(), is( new Heading( V_VELOCITY, H_VELOCITY ) ) );
   }//End Method
   
   @Test public void shouldSetPosition(){
      systemUnderTest.setPosition( environmentPosition( 200, 100 ) );
      assertThat( systemUnderTest.position().get(), is( environmentPosition( 200, 100 ) ) );
   }//End Method
   
   @Test public void shouldSetHeading(){
      systemUnderTest.setHeading( new Heading( 200, 100 ) );
      assertThat( systemUnderTest.heading().get(), is( new Heading( 200, 100 ) ) );
   }//End Method

   @Test public void shouldNotChangeHeadingIfNotMovedIntoBoundary(){
      when( interpolator.move( environment ) ).thenReturn( false );
      systemUnderTest.move( environment );
      verify( interpolator ).move( environment );
      verify( swarmingNature, never() ).randomizeHeading();
   }//End Method
   
   @Test public void shouldChangeHeadingIfMovedIntoBoundary(){
      when( interpolator.move( environment ) ).thenReturn( true );
      systemUnderTest.move( environment );
      verify( interpolator ).move( environment );
      verify( swarmingNature ).randomizeHeading();
   }//End Method
   
   @Test public void shouldUnconditionallyIdentifyNeighbourHoodAndRespondToIt(){
      systemUnderTest.move( environment );
      verify( neighbourHood ).identifyNeighbourHood( environment );
      verify( swarmingNature ).respondToNeighbours();
   }//End Method
   
   @Test public void shouldIdentifyNeighbourHoodBeforeAnyOtherOperations(){
      when( interpolator.move( environment ) ).thenReturn( true );
      
      systemUnderTest.move( environment );
      
      InOrder order = inOrder( neighbourHood, parentHood, interpolator, swarmingNature );
      order.verify( neighbourHood ).identifyNeighbourHood( environment );
      order.verify( swarmingNature ).respondToNeighbours();
      order.verify( parentHood ).mingle( environment );
      order.verify( interpolator ).move( environment );
      order.verify( swarmingNature ).randomizeHeading();
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
   
   @Test public void shouldSetReproductiveDrive(){
      systemUnderTest.setMatingCycle( 8 );
      assertThat( systemUnderTest.matingCycle(), is( 8 ) );
   }//End Method
   
   @Test public void shouldBeAssociatedWithLifecyle(){
      verify( lifecyle ).associate( systemUnderTest );
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
      verify( neighbourHood, never() ).identifyNeighbourHood( environment );
      verify( swarmingNature, never() ).respondToNeighbours();
      verify( interpolator, never() ).move( environment );
      verify( parentHood, never() ).mingle( environment );
   }//End Method
   
   @Test public void shouldBeAssociatedWithParentHood(){
      assertThat( systemUnderTest.gender(), is( Gender.Male ) );
      verify( parentHood ).associate( systemUnderTest, neighbourHood );
   }//End Method
   
   @Test public void shouldProvideMatingDrive(){
      systemUnderTest.setMatingCycle( 45 );
      assertThat( systemUnderTest.matingCycle(), is( 45 ) );
   }//End Method
   
   @Test public void shouldMateUsingParentHoodLogic(){
      Agent agent = mock( Agent.class );
      systemUnderTest.mate( agent );
      verify( parentHood ).mate( agent );
   }//End Method
   
   @Test public void shouldMingleWhenMoved(){
      systemUnderTest.move( environment );
      verify( parentHood ).mingle( environment );
   }//End Method
   
}//End Class
