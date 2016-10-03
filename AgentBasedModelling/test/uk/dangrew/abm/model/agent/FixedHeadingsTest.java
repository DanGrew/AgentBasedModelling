package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.dangrew.abm.model.environment.ArbitraryPosition;

@RunWith( JUnitParamsRunner.class )
public class FixedHeadingsTest {

   private FixedHeadings systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new FixedHeadings();
   }//End Method

   @Test public void shouldHaveFixedHeadingForAllPositionsTotallingTen() {
      for ( int v = 0; v <= 10; v++ ) {
         for ( int h = 0; h <= 10; h++ ) {
            System.out.println( v + ", " + h );
            if ( v + h == 10 ) {
               assertThat( systemUnderTest.getFixedHeadingFor( new Heading( v, h ) ), is( notNullValue() ) );
            } else {
               assertThat( systemUnderTest.getFixedHeadingFor( new Heading( v, h ) ), is( nullValue() ) );
            }
         }         
      }
   }//End Method
   
   public Object[] parametersForShouldCalculateHeadings(){
      return new Object[]{
         new Object[]{ 100, 0, FixedHeading.SOUTH },
         new Object[]{ 10, 0, FixedHeading.SOUTH },
         new Object[]{ 1, 0, FixedHeading.SOUTH },
         new Object[]{ 27.347, 0, FixedHeading.SOUTH },
         
         new Object[]{ -100, 0, FixedHeading.NORTH },
         new Object[]{ -10, 0, FixedHeading.NORTH },
         new Object[]{ -1, 0, FixedHeading.NORTH },
         new Object[]{ -27.347, 0, FixedHeading.NORTH },
         
         new Object[]{ 0, 100, FixedHeading.EAST },
         new Object[]{ 0, 10, FixedHeading.EAST },
         new Object[]{ 0, 1, FixedHeading.EAST },
         new Object[]{ 0, 27.347, FixedHeading.EAST },
         
         new Object[]{ 0, -100, FixedHeading.WEST },
         new Object[]{ 0, -10, FixedHeading.WEST },
         new Object[]{ 0, -1, FixedHeading.WEST },
         new Object[]{ 0, -27.347, FixedHeading.WEST },
         
         new Object[]{ -100, 100, FixedHeading.NE_55 },
         new Object[]{ -10, 10, FixedHeading.NE_55 },
         new Object[]{ -1, 1, FixedHeading.NE_55 },
         new Object[]{ -27.347, 27.347, FixedHeading.NE_55 },
         
         new Object[]{ 100, 100, FixedHeading.SE_55 },
         new Object[]{ 10, 10, FixedHeading.SE_55 },
         new Object[]{ 1, 1, FixedHeading.SE_55 },
         new Object[]{ 27.347, 27.347, FixedHeading.SE_55 },
         
         new Object[]{ 100, -100, FixedHeading.SW_55 },
         new Object[]{ 10, -10, FixedHeading.SW_55 },
         new Object[]{ 1, -1, FixedHeading.SW_55 },
         new Object[]{ 27.347, -27.347, FixedHeading.SW_55 },
         
         new Object[]{ -100, -100, FixedHeading.NW_55 },
         new Object[]{ -10, -10, FixedHeading.NW_55 },
         new Object[]{ -1, -1, FixedHeading.NW_55 },
         new Object[]{ -27.347, -27.347, FixedHeading.NW_55 },
         
         new Object[]{ -6, -2, FixedHeading.NW_82 },
         new Object[]{  6, -2, FixedHeading.SW_82 },
         new Object[]{ -6,  2, FixedHeading.NE_82 },
         new Object[]{  6,  2, FixedHeading.SE_82 },
         
         new Object[]{ -8, -1, FixedHeading.NW_91 },
         new Object[]{  8, -1, FixedHeading.SW_91 },
         new Object[]{ -8,  1, FixedHeading.NE_91 },
         new Object[]{  8,  1, FixedHeading.SE_91 },
         
         new Object[]{ -3, -4, FixedHeading.NW_46 },
         new Object[]{  3, -4, FixedHeading.SW_46 },
         new Object[]{ -3,  4, FixedHeading.NE_46 },
         new Object[]{  3,  4, FixedHeading.SE_46 },
      };
   }//End Method
   
   @Parameters
   @Test public void shouldCalculateHeadings( double v, double h, FixedHeading expected ){
      assertThat( systemUnderTest.calculateHeading( v, h ), is( expected ) );
   }//End Method
   
   @Test public void shouldNotProvideHeadingForNoVelocity(){
      assertThat( systemUnderTest.calculateHeading( 0, 0 ), is( nullValue() ) );
   }//End Method

}//End Class
