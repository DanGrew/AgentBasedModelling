package uk.dangrew.abm.model.environment;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import uk.dangrew.abm.model.agent.Agent;
import uk.dangrew.abm.model.agent.AgentImpl;
import uk.dangrew.abm.model.agent.Heading;

public class EnvironmentTest {

   @Spy private EnvironmentPositioningImpl positioning;
   private Environment systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Environment( positioning, 10, 20 );
   }//End Method
   
   @Test public void shouldProvideDimensions(){
      assertThat( systemUnderTest.width(), is( 10 ) );
      assertThat( systemUnderTest.height(), is( 20 ) );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAllowEmptyDimensionRow(){
      new Environment( 0, 10 );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAllowEmptyDimensionColumn(){
      new Environment( 10, 0 );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAllowNegativeDimensionRow(){
      new Environment( -1, 10 );
   }//End Method
   
   @Test( expected = IllegalArgumentException.class ) public void shouldNotAllowNegativeDimensionColumn(){
      new Environment( 10, -1 );
   }//End Method

   @Test public void shouldInitiallyBeEmpty(){
      for ( int i = 0; i < 20; i++ ) {
         for ( int j = 0; j < 10; j++ ) {
            assertThat( systemUnderTest.isSpace( new EnvironmentPosition( i, j ) ), is( true ) );
         }
      }
   }//End Method
   
   @Test public void shouldProvideHorizontalBoundary() {
      systemUnderTest.applyHorizontalBoundary( 0, 0, 4 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 1 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 2 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 3 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 4 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 1, 0 ) ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideNegativeHorizontalBoundary() {
      systemUnderTest.applyHorizontalBoundary( 0, 5, -2 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 2 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 3 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 4 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 5 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 6 ) ), is( false ) );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreHorizontalBoundaryBeyondEdges() {
      systemUnderTest.applyHorizontalBoundary( 0, 19, 3 );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreNegativeHorizontalBoundaryBeyondEdges() {
      systemUnderTest.applyHorizontalBoundary( 1, 0, -3 );
   }//End Method
   
   @Test public void shouldProvideVerticalBoundary() {
      systemUnderTest.applyVerticalBoundary( 0, 0, 4 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 1, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 2, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 3, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 4, 0 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 4, 1 ) ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideNegativeVerticalBoundary() {
      systemUnderTest.applyVerticalBoundary( 5, 0, -2 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 2, 0 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 3, 0 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 4, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 5, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 6, 0 ) ), is( false ) );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreVerticalBoundaryBeyondEdges() {
      systemUnderTest.applyVerticalBoundary( 9, 19, 3 );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreNegativeVerticalBoundaryBeyondEdges() {
      systemUnderTest.applyVerticalBoundary( 1, 0, -3 );
   }//End Method
   
   @Test public void shouldIdentifyAllOutsideEnvironmentAsBoundary(){
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( -1, -1 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( -1, 5 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 5, -1 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 11 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 21, 0 ) ), is( true ) );
   }//End Method
   
   @Test public void shouldProvideEnvironmentPositions(){
      assertThat( systemUnderTest.grid(), is( notNullValue() ) );
      systemUnderTest.applyVerticalBoundary( 0, 0, 2 );
      assertThat( systemUnderTest.grid().get( new EnvironmentPosition( 0, 0 ) ), is( EnvironmentElement.Boundary ) );
   }//End Method
   
   @Test public void shouldMonitorAgent(){
      Agent agent = new AgentImpl( new EnvironmentPosition( 5, 5 ), new Heading( 1, 1 ) );
      systemUnderTest.monitorAgent( agent );
      assertThat( systemUnderTest.agents(), hasEntry( new EnvironmentPosition( 5, 5 ), agent ) );
   }//End Method
   
   @Test public void shouldUpdateAgentLocationWhenAgentMoves(){
      Agent agent = new AgentImpl( new EnvironmentPosition( 5, 5 ), new Heading( 1, 1 ) );
      systemUnderTest.monitorAgent( agent );
      assertThat( systemUnderTest.agents(), hasEntry( new EnvironmentPosition( 5, 5 ), agent ) );
      
      agent.move( systemUnderTest );
      assertThat( systemUnderTest.agents(), hasEntry( agent.position().get(), agent ) );
   }//End Method
   
   @Test public void shouldNotReregisterForPositionChangesIfMonitoredAgain(){
      //efficiency
   }//End Method
   
   @Test public void shouldNotAllowModificationsToGrid(){
      assertThat( systemUnderTest.grid(), is( instanceOf( UnmodifiableObservableMap.class ) ) );
   }//End Method
   
   @Test public void shouldNotAllowModificationsToAgents(){
      assertThat( systemUnderTest.agents(), is( instanceOf( UnmodifiableObservableMap.class ) ) );
   }//End Method
   
   @Test public void shouldNotShowPositionToBeAvailableWhenAgentPresent(){
      assertThat( systemUnderTest.isAvailable( new EnvironmentPosition( 4, 3 ) ), is( true ) );
      Agent agent = new AgentImpl( new EnvironmentPosition( 4, 3 ), new Heading( 10, 10 ) );
      systemUnderTest.monitorAgent( agent );
      assertThat( systemUnderTest.isAvailable( new EnvironmentPosition( 4, 3 ) ), is( false ) );
      agent.move( systemUnderTest );
      assertThat( systemUnderTest.isAvailable( new EnvironmentPosition( 4, 3 ) ), is( true ) );
      assertThat( systemUnderTest.isAvailable( new EnvironmentPosition( 5, 4 ) ), is( false ) );
   }//End Method
   
   @Test public void shouldNotShowPositionToBeAvailableWhenBoundaryPresent(){
      systemUnderTest.applyVerticalBoundary( 0, 0, 5 );
      assertThat( systemUnderTest.isAvailable( new EnvironmentPosition( -1, -1 ) ), is( false ) );
      assertThat( systemUnderTest.isAvailable( new EnvironmentPosition( 4, 0 ) ), is( false ) );
   }//End Method
   
   @Test public void shouldIgnoreCleanUpWhenAgentNotPresent(){
      Agent agent = new AgentImpl( new EnvironmentPosition( 4, 3 ), new Heading( 10, 10 ) );
      systemUnderTest.cleanAgentUp( agent );
   }//End Method
   
   @Test public void shouldRemoveAgentAndNoLongerListenForChangesWhenCleanedUp(){
      Agent agent = new AgentImpl( new EnvironmentPosition( 4, 3 ), new Heading( 10, 10 ) );
      systemUnderTest.monitorAgent( agent );
      systemUnderTest.cleanAgentUp( agent );
      assertThat( systemUnderTest.agents().get( agent.position().get() ), is( nullValue() ) );
      assertThat( systemUnderTest.grid().get( agent.position().get() ), is( EnvironmentElement.Space ) );
      agent.move( systemUnderTest );
      assertThat( systemUnderTest.agents().get( agent.position().get() ), is( nullValue() ) );
      assertThat( systemUnderTest.grid().get( agent.position().get() ), is( EnvironmentElement.Space ) );
   }//End Method
   
   @Test public void shouldProvideLocation(){
      EnvironmentPosition result = mock( EnvironmentPosition.class );
      when( positioning.locate( 434, 978 ) ).thenReturn( result );
      
      assertThat( systemUnderTest.locate( 434, 978 ), is( result ) );
      verify( positioning ).locate( 434, 978 );
   }//End Method
   
   @Test public void shouldProvideHorizontalOffsetting(){
      EnvironmentPosition original = mock( EnvironmentPosition.class );
      
      assertThat( 
               systemUnderTest.horizontalPositionOffset( original, 3 ), 
               is( positioning.horizontalPositionOffset( original, 3 ) ) 
      );
   }//End Method
   
   @Test public void shouldProvideVerticalOffsetting(){
      EnvironmentPosition original = mock( EnvironmentPosition.class );
      
      assertThat( 
               systemUnderTest.verticalPositionOffset( original, 3 ), 
               is( positioning.verticalPositionOffset( original, 3 ) ) 
      );
   }//End Method
   
   @Test public void shouldProvideTranslation(){
      EnvironmentPosition original = mock( EnvironmentPosition.class );
      
      assertThat( 
               systemUnderTest.translate( original, new Heading( 3, 4 ) ), 
               is( positioning.translate( original, new Heading( 3, 4 ) ) ) 
      );
   }//End Method
}//End Class
