package uk.dangrew.abm.algorithm;

import static org.mockito.Mockito.verify;

import java.util.function.BiPredicate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GridInterpolationTest {

   @Mock private BiPredicate< Integer, Integer > predicate;
   private GridInterpolation systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GridInterpolation( predicate );
   }//End Method
   
   @Test public void shouldInterpolateSouthEast() {
      systemUnderTest.interpolate( 5, 5, 9, 7 );
      verify( predicate ).test( 5, 5 );
      verify( predicate ).test( 6, 5 );
      verify( predicate ).test( 7, 6 );
      verify( predicate ).test( 8, 6 );
      verify( predicate ).test( 9, 7 );
   }//End Method
   
   @Test public void shouldInterpolateSouthWest() {
      systemUnderTest.interpolate( 5, 5, 9, 3 );
      verify( predicate ).test( 6, 5 );
      verify( predicate ).test( 7, 4 );
      verify( predicate ).test( 8, 4 );
      verify( predicate ).test( 9, 3 );
   }//End Method
   
   @Test public void shouldInterpolateNorthEast() {
      systemUnderTest.interpolate( 5, 5, 1, 7 );
      verify( predicate ).test( 4, 5 );
      verify( predicate ).test( 3, 6 );
      verify( predicate ).test( 2, 6 );
      verify( predicate ).test( 1, 7 );
   }//End Method
   
   @Test public void shouldInterpolateNorthWest() {
      systemUnderTest.interpolate( 5, 5, 1, 3 );
      verify( predicate ).test( 4, 5 );
      verify( predicate ).test( 3, 4 );
      verify( predicate ).test( 2, 4 );
      verify( predicate ).test( 1, 3 );
   }//End Method
   
   @Test public void shouldInterpolateSouth() {
      systemUnderTest.interpolate( 5, 5, 9, 5 );
      verify( predicate ).test( 6, 5 );
      verify( predicate ).test( 7, 5 );
      verify( predicate ).test( 8, 5 );
      verify( predicate ).test( 9, 5 );
   }//End Method
   
   @Test public void shouldInterpolateEast() {
      systemUnderTest.interpolate( 5, 5, 5, 7 );
      verify( predicate ).test( 5, 6 );
      verify( predicate ).test( 5, 7 );
   }//End Method
   
   @Test public void shouldInterpolateWest() {
      systemUnderTest.interpolate( 5, 5, 5, 3 );
      verify( predicate ).test( 5, 4 );
      verify( predicate ).test( 5, 3 );
   }//End Method
   
   @Test public void shouldInterpolateNorth() {
      systemUnderTest.interpolate( 5, 5, 1, 5 );
      verify( predicate ).test( 4, 5 );
      verify( predicate ).test( 3, 5 );
      verify( predicate ).test( 2, 5 );
      verify( predicate ).test( 1, 5 );
   }//End Method
   
   @Test public void shouldInterpolateVerticallyWithLargerHorizontalStep() {
      systemUnderTest.interpolate( 5, 5, 7, 9 );
      verify( predicate ).test( 5, 6 );
      verify( predicate ).test( 6, 7 );
      verify( predicate ).test( 6, 8 );
      verify( predicate ).test( 7, 9 );
   }//End Method

}//End Class
