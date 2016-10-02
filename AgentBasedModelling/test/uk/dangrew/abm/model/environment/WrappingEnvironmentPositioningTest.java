package uk.dangrew.abm.model.environment;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.dangrew.abm.model.agent.Heading;

@RunWith( JUnitParamsRunner.class )
public class WrappingEnvironmentPositioningTest {

   private static final int WIDTH = 10;
   private static final int HEIGHT = 15;
   
   @Mock private Environment environment; 
   private WrappingEnvironmentPositioning systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      when( environment.width() ).thenReturn( WIDTH );
      when( environment.height() ).thenReturn( HEIGHT );
      systemUnderTest = new WrappingEnvironmentPositioning();
      systemUnderTest.associate( environment );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( environment );
   }//End Method

   @Test public void shouldProvideBasicLocation() {
      assertThat( systemUnderTest.locate( 0, 0 ), is( environmentPosition( 0, 0 ) ) );
      assertThat( systemUnderTest.locate( 9, 9 ), is( environmentPosition( 9, 9 ) ) );
      assertThat( systemUnderTest.locate( 4, 6 ), is( environmentPosition( 4, 6 ) ) );
   }//End Method
   
   public Object[] parametersForShouldWrapAroundOnLocate(){
      return new Object[]{
         new Object[]{ 15, 5, 0, 5 },
         new Object[]{ 5, 10, 5, 0 },
         new Object[]{ 20, 5, 5, 5 },
         new Object[]{ 5, 15, 5, 5 },
         new Object[]{ 34, 5, 4, 5 },
         new Object[]{ 5, 24, 5, 4 },
         new Object[]{ 37, 24, 7, 4 },
         
         new Object[]{ -1, 5, 14, 5 },
         new Object[]{ 5, -1, 5, 9 },
         new Object[]{ -15, 5, 0, 5 },
         new Object[]{ 5, -10, 5, 0 },
         new Object[]{ -34, 5, 11, 5 },
         new Object[]{ 5, -24, 5, 6 },
         new Object[]{ -37, -24, 8, 6 },
         
         new Object[]{ -37, 24, 8, 4 },
         new Object[]{ 37, -24, 7, 6 },
      };
   }//End Method
   
   @Parameters
   @Test public void shouldWrapAroundOnLocate( int vInput, int hInput, int expectedV, int expectedH ) {
      assertThat( systemUnderTest.locate( vInput, hInput ), is( environmentPosition( expectedV, expectedH ) ) );
   }//End Method
   
   @Test public void shouldProvideHorizontalOffset() {
      assertThat( systemUnderTest.horizontalPositionOffset( environmentPosition( 6, 5 ), 26 ), is( environmentPosition( 6, 1 ) ) );
      assertThat( systemUnderTest.horizontalPositionOffset( environmentPosition( 6, 5 ), -26 ), is( environmentPosition( 6, 9 ) ) );
   }//End Method
   
   @Test public void shouldProvideVerticalOffset() {
      assertThat( systemUnderTest.verticalPositionOffset( environmentPosition( 5, 6 ), 26 ), is( environmentPosition( 1, 6 ) ) );
      assertThat( systemUnderTest.verticalPositionOffset( environmentPosition( 5, 6 ), -26 ), is( environmentPosition( 9, 6 ) ) );
   }//End Method

   @Test public void shouldProvideTranslation() {
      assertThat( systemUnderTest.translate( environmentPosition( 5, 6 ), new Heading( 26, -24 ) ), is( environmentPosition( 1, 2 ) ) );
      assertThat( systemUnderTest.translate( environmentPosition( 5, 6 ), new Heading( -26, 24 ) ), is( environmentPosition( 9, 0 ) ) );
   }//End Method
   
}//End Class
