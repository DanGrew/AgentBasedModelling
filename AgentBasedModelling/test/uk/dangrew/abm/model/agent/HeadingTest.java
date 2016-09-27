package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.abm.model.agent.Heading;

public class HeadingTest {

   private static final int V_VEL = 3;
   private static final int H_VEL = 7;
   
   private Heading systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Heading( V_VEL, H_VEL );
   }//End Method

   @Test public void shouldProvideHeadingValues() {
      assertThat( systemUnderTest.verticalVelocity(), is( V_VEL ) );
      assertThat( systemUnderTest.horizontalVelocity(), is( H_VEL ) );
   }//End Method
   
   @Test public void shouldBeEqual(){
      assertThat( systemUnderTest, is( systemUnderTest ) );
      assertThat( systemUnderTest, is( new Heading( V_VEL, H_VEL ) ) );
   }//End Method
   
   @Test public void shouldNotBeEqual(){
      assertThat( systemUnderTest, is( not( new Heading( H_VEL, H_VEL ) ) ) );
      assertThat( systemUnderTest, is( not( new Heading( V_VEL, V_VEL ) ) ) );
      assertThat( systemUnderTest, is( not( new Heading( H_VEL, V_VEL ) ) ) );
      assertThat( systemUnderTest, is( not( "anything" ) ) );
      assertThat( systemUnderTest.equals( null ), is( false ) );
   }//End Method
   
   @Test public void shouldHaveEqualHashcode(){
      assertThat( systemUnderTest.hashCode(), is( systemUnderTest.hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( new Heading( V_VEL, H_VEL ).hashCode() ) );
   }//End Method
   
   @Test public void shouldNotHaveEqualHashcode(){
      assertThat( systemUnderTest.hashCode(), is( not( new Heading( H_VEL, H_VEL ).hashCode() ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( new Heading( V_VEL, V_VEL ).hashCode() ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( new Heading( H_VEL, V_VEL ).hashCode() ) ) );
      assertThat( systemUnderTest.hashCode(), is( not( "anything".hashCode() ) ) );
   }//End Method
   
}//End Class
