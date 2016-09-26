package uk.dangrew.abm.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FluidMovementInterpolatorTest {

   private static final double PROPORTION = 0.1;
   
   @Mock private Environment environment;
   private ControllableAgent agent;
   private FluidMovementInterpolator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      when( environment.isAvailable( Mockito.any() ) ).thenReturn( true );
      
      agent = new AgentImpl( new EnvironmentPosition( 0, 0 ), new Heading( 10, 10 ) );
      systemUnderTest = new FluidMovementInterpolator( PROPORTION );
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociationMultipleTimes(){
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test public void shouldStepProportion() {
      for ( int i = 0; i < 10; i++ ) {
         assertThat( systemUnderTest.move( environment ), is( false ) );
         EnvironmentPosition proposed = new EnvironmentPosition( i + 1, i + 1 );
         verify( environment ).isAvailable( proposed );
         assertThat( agent.position().get(), is( proposed ) );
      }
   }//End Method
   
   @Test public void shouldStepProportionNegatively() {
      agent.setHeading( new Heading( -10, -10 ) );
      for ( int i = 0; i < 10; i++ ) {
         assertThat( systemUnderTest.move( environment ), is( false ) );
         EnvironmentPosition proposed = new EnvironmentPosition( -i - 1, -i - 1 );
         verify( environment ).isAvailable( proposed );
         assertThat( agent.position().get(), is( proposed ) );
      }
   }//End Method
   
   @Test public void shouldProvideJustEnoughToEscapeCurrentSqure(){
      agent.setHeading( new Heading( 5, 5 ) );
      systemUnderTest.move( environment );
      assertThat( agent.position().get(), is( new EnvironmentPosition( 1, 1 ) ) );
      systemUnderTest.move( environment );
      assertThat( agent.position().get(), is( new EnvironmentPosition( 1, 1 ) ) );
      systemUnderTest.move( environment );
      assertThat( agent.position().get(), is( new EnvironmentPosition( 2, 2 ) ) );
   }//End Method
   
   @Test public void shouldStepMultipleSquares() {
      agent.setHeading( new Heading( 20, 20 ) );
      for ( int i = 0; i < 10; i++ ) {
         systemUnderTest.move( environment );
         assertThat( agent.position().get(), is( new EnvironmentPosition( ( i + 1 ) * 2, ( i + 1 ) * 2 ) ) );
      }
   }//End Method
   
   @Test public void shouldDetectPositiveHorizontalCollisions(){
      when( environment.isAvailable( Mockito.any() ) ).thenReturn( false );
      agent.setHeading( new Heading( 0, 5 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method
   
   @Test public void shouldDetectNegativeHorizontalCollisions(){
      when( environment.isAvailable( Mockito.any() ) ).thenReturn( false );
      agent.setHeading( new Heading( 0, -5 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method
   
   @Test public void shouldDetectPositiveVerticalCollisions(){
      when( environment.isAvailable( Mockito.any() ) ).thenReturn( false );
      agent.setHeading( new Heading( 5, 0 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method
   
   @Test public void shouldDetectNegativeVerticalCollisions(){
      when( environment.isAvailable( Mockito.any() ) ).thenReturn( false );
      agent.setHeading( new Heading( -5, 0 ) );
      assertThat( systemUnderTest.move( environment ), is( true ) );
   }//End Method

}//End Class
