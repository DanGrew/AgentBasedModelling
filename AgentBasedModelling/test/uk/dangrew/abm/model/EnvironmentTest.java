package uk.dangrew.abm.model;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.sun.javafx.collections.UnmodifiableObservableMap;

public class EnvironmentTest {

   private Environment systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new Environment( 10, 20 );
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
      systemUnderTest.applyHorizontalBoundary( new EnvironmentPosition( 0, 0 ), 4 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 1 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 2 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 3 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 4 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 1, 0 ) ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideNegativeHorizontalBoundary() {
      systemUnderTest.applyHorizontalBoundary( new EnvironmentPosition( 0, 5 ), -2 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 2 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 3 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 4 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 5 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 6 ) ), is( false ) );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreHorizontalBoundaryBeyondEdges() {
      systemUnderTest.applyHorizontalBoundary( new EnvironmentPosition( 0, 19 ), 3 );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreNegativeHorizontalBoundaryBeyondEdges() {
      systemUnderTest.applyHorizontalBoundary( new EnvironmentPosition( 1, 0 ), -3 );
   }//End Method
   
   @Test public void shouldProvideVerticalBoundary() {
      systemUnderTest.applyVerticalBoundary( new EnvironmentPosition( 0, 0 ), 4 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 0, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 1, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 2, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 3, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 4, 0 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 4, 1 ) ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideNegativeVerticalBoundary() {
      systemUnderTest.applyVerticalBoundary( new EnvironmentPosition( 5, 0 ), -2 );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 2, 0 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 3, 0 ) ), is( false ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 4, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 5, 0 ) ), is( true ) );
      assertThat( systemUnderTest.isBoundary( new EnvironmentPosition( 6, 0 ) ), is( false ) );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreVerticalBoundaryBeyondEdges() {
      systemUnderTest.applyVerticalBoundary( new EnvironmentPosition( 9, 19 ), 3 );
   }//End Method
   
   @Test( expected = ArrayIndexOutOfBoundsException.class ) public void shouldNotIgnoreNegativeVerticalBoundaryBeyondEdges() {
      systemUnderTest.applyVerticalBoundary( new EnvironmentPosition( 1, 0 ), -3 );
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
      systemUnderTest.applyVerticalBoundary( new EnvironmentPosition( 0, 0 ), 2 );
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
   
}//End Class
