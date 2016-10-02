package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.translate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class FluidMovementInterpolatorTest {

   private static final double PROPORTION = 0.1;
   
   private Environment environment;
   private ControllableAgent agent;
   private FluidMovementInterpolator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      environment = new Environment( 100, 100 );
      agent = new AgentImpl( environmentPosition( 10, 10 ), new Heading( 10, 10 ) );
      systemUnderTest = new FluidMovementInterpolator( PROPORTION );
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociationMultipleTimes(){
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test public void shouldStepProportion() {
      EnvironmentPosition original = agent.position().get();
      for ( int i = 0; i < 10; i++ ) {
         assertThat( systemUnderTest.move( environment ), is( false ) );
         EnvironmentPosition proposed = translate( original, i + 1, i + 1 );
         assertThat( agent.position().get(), is( proposed ) );
      }
   }//End Method
   
   @Test public void shouldStepProportionNegatively() {
      EnvironmentPosition original = agent.position().get();
      agent.setHeading( new Heading( -10, -10 ) );
      for ( int i = 0; i < 10; i++ ) {
         assertThat( systemUnderTest.move( environment ), is( false ) );
         EnvironmentPosition proposed = translate( original, -i - 1, -i - 1 );
         assertThat( agent.position().get(), is( proposed ) );
      }
   }//End Method
   
   @Test public void shouldProvideJustEnoughToEscapeCurrentSquare(){
      agent.setHeading( new Heading( 5, 5 ) );
      systemUnderTest.move( environment );
      assertThat( agent.position().get(), is( environmentPosition( 11, 11 ) ) );
      systemUnderTest.move( environment );
      assertThat( agent.position().get(), is( environmentPosition( 11, 11 ) ) );
      systemUnderTest.move( environment );
      assertThat( agent.position().get(), is( environmentPosition( 12, 12 ) ) );
   }//End Method
   
   @Test public void shouldStepMultipleSquares() {
      EnvironmentPosition original = agent.position().get();
      agent.setHeading( new Heading( 20, 20 ) );
      for ( int i = 0; i < 10; i++ ) {
         systemUnderTest.move( environment );
         assertThat( agent.position().get(), is( translate( original, ( i + 1 ) * 2, ( i + 1 ) * 2 ) ) );
      }
   }//End Method
   
   @Test public void shouldDetectPositiveHorizontalCollisions(){
      environment.applyHorizontalBoundary( 10, 12, 1 );
      agent.setHeading( new Heading( 0, 50 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method
   
   @Test public void shouldDetectNegativeHorizontalCollisions(){
      environment.applyHorizontalBoundary( 10, 8, 1 );
      agent.setHeading( new Heading( 0, -50 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method
   
   @Test public void shouldDetectPositiveVerticalCollisions(){
      environment.applyHorizontalBoundary( 12, 10, 1 );
      agent.setHeading( new Heading( 50, 0 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method
   
   @Test public void shouldDetectNegativeVerticalCollisions(){
      environment.applyHorizontalBoundary( 8, 10, 1 );
      agent.setHeading( new Heading( -50, 0 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method

}//End Class
