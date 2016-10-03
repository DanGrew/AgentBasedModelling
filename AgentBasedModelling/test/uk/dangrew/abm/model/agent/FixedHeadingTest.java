package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class FixedHeadingTest {

   @Test public void headingsShouldAddUpToTen() {
      for ( FixedHeading heading : FixedHeading.values() ) {
         assertThat( Math.abs( heading.vertical() ) + Math.abs( heading.horizontal() ), is( 10 ) );
      }
   }//End Method
   
   @Test public void headingsProvideRepresentativeHeading() {
      for ( FixedHeading heading : FixedHeading.values() ) {
         assertThat( heading.heading().horizontalVelocity(), is( heading.horizontal() ) );
         assertThat( heading.heading().verticalVelocity(), is( heading.vertical() ) );
      }
   }//End Method

}//End Class
