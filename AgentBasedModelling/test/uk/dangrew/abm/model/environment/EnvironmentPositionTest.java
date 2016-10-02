package uk.dangrew.abm.model.environment;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.abm.model.agent.Heading;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

public class EnvironmentPositionTest {

   private static final int X = 4;
   private static final int Y = 6;
   
   private EnvironmentPosition systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new EnvironmentPosition( X, Y );
   }//End Method

   @Test public void shouldProvideGridReference() {
      assertThat( systemUnderTest.vertical(), is( X ) );
      assertThat( systemUnderTest.horizontal(), is( Y ) );
   }//End Method
   
   @Test public void shouldBeEqual(){
      assertThat( new EnvironmentPosition( 1, 2 ), is( new EnvironmentPosition( 1, 2 ) ) );
      assertThat( systemUnderTest, is( new EnvironmentPosition( X, Y ) ) );
      assertThat( systemUnderTest, is( systemUnderTest ) );
      
      assertThat( new EnvironmentPosition( 1, 2 ).hashCode(), is( new EnvironmentPosition( 1, 2 ).hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( new EnvironmentPosition( X, Y ).hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( systemUnderTest.hashCode() ) );
   }//End Method
   
   @Test public void shouldNotBeEqual(){
      assertThat( new EnvironmentPosition( 1, 2 ), is( not( new EnvironmentPosition( 1, 3 ) ) ) );
      assertThat( new EnvironmentPosition( 1, 2 ), is( not( new EnvironmentPosition( 2, 2 ) ) ) );
      assertThat( new EnvironmentPosition( 1, 2 ).equals( null ), is( false ) );
      assertThat( new EnvironmentPosition( 1, 2 ), is( not( "help" ) ) );
      
      assertThat( new EnvironmentPosition( 1, 2 ).hashCode(), is( not( new EnvironmentPosition( 1, 3 ).hashCode() ) ) );
      assertThat( new EnvironmentPosition( 1, 2 ).hashCode(), is( not( new EnvironmentPosition( 2, 2 ).hashCode() ) ) );
      assertThat( new EnvironmentPosition( 1, 2 ).hashCode(), is( not( "help".hashCode() ) ) );
   }//End Method
   
}//End Class
