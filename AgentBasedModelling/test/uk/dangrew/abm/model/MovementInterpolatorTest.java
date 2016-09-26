package uk.dangrew.abm.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javafx.beans.value.ChangeListener;

public class MovementInterpolatorTest {

   @Mock private ChangeListener< EnvironmentPosition > positionListener;
   @Captor private ArgumentCaptor< EnvironmentPosition > positionCaptor;
   
   private ControllableAgent agent;
   @Mock private Environment environment;
   private MovementInterpolator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
   }//End Method
   
   private void constructSut( int row, int column, int vVelocity, int hVelocity ) {
      agent = new AgentImpl( new EnvironmentPosition( row, column ), new Heading( vVelocity, hVelocity ) );
      systemUnderTest = new MovementInterpolator();
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociationMultipleTimes(){
      constructSut( 1, 1, 1, 1 );
      systemUnderTest.associate( agent );
   }//End Method
   
   @Test public void shouldInterpolateSouthEast() {
      constructSut( 5, 5, 4, 2 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 9 ) );
      assertThat( agent.position().get().horizontal(), is( 7 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 6, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 7, 6 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 8, 6 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 9, 7 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateSouthWest() {
      constructSut( 5, 5, 4, -2 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 9 ) );
      assertThat( agent.position().get().horizontal(), is( 3 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 6, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 7, 4 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 8, 4 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 9, 3 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateNorthEast() {
      constructSut( 5, 5, -4, 2 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 1 ) );
      assertThat( agent.position().get().horizontal(), is( 7 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 4, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 3, 6 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 2, 6 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 1, 7 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateNorthWest() {
      constructSut( 5, 5, -4, -2 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 1 ) );
      assertThat( agent.position().get().horizontal(), is( 3 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 4, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 3, 4 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 2, 4 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 1, 3 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateSouth() {
      constructSut( 5, 5, 4, 0 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 9 ) );
      assertThat( agent.position().get().horizontal(), is( 5 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 6, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 7, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 8, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 9, 5 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateEast() {
      constructSut( 5, 5, 0, 2 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 5 ) );
      assertThat( agent.position().get().horizontal(), is( 7 ) );
      
      verify( positionListener, times( 2 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 5, 6 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 5, 7 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateWest() {
      constructSut( 5, 5, 0, -2 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 5 ) );
      assertThat( agent.position().get().horizontal(), is( 3 ) );
      
      verify( positionListener, times( 2 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 5, 4 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 5, 3 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateNorth() {
      constructSut( 5, 5, -4, 0 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 1 ) );
      assertThat( agent.position().get().horizontal(), is( 5 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 4, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 3, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 2, 5 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 1, 5 ) ) );
   }//End Method
   
   @Test public void shouldInterpolateVerticallyWithLargerHorizontalStep() {
      constructSut( 5, 5, 2, 4 );
      agent.position().addListener( positionListener );
      
      systemUnderTest.moveForVelocity( environment );
      assertThat( agent.position().get().vertical(), is( 7 ) );
      assertThat( agent.position().get().horizontal(), is( 9 ) );
      
      verify( positionListener, times( 4 ) ).changed( Mockito.eq( agent.position() ), Mockito.any(), positionCaptor.capture() );
      assertThat( positionCaptor.getAllValues().get( 0 ), is( new EnvironmentPosition( 5, 6 ) ) );
      assertThat( positionCaptor.getAllValues().get( 1 ), is( new EnvironmentPosition( 6, 7 ) ) );
      assertThat( positionCaptor.getAllValues().get( 2 ), is( new EnvironmentPosition( 6, 8 ) ) );
      assertThat( positionCaptor.getAllValues().get( 3 ), is( new EnvironmentPosition( 7, 9 ) ) );
   }//End Method
   
}//End Class
