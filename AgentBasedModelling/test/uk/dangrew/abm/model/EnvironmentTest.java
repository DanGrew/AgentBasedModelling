package uk.dangrew.abm.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

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
      for ( int i = 0; i < 10; i++ ) {
         for ( int j = 0; j < 20; j++ ) {
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
   
}//End Class
