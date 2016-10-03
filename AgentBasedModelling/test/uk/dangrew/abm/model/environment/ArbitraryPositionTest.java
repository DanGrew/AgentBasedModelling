package uk.dangrew.abm.model.environment;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ArbitraryPositionTest {

   protected static final int V = 4;
   protected static final int H = 6;
   
   private ArbitraryPosition systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new EnvironmentPosition( V, H );
   }//End Method
   
   protected ArbitraryPosition constructSut(){
      return new EnvironmentPosition( V, H );
   }//End Method

   @Test public void shouldProvideGridReference() {
      assertThat( systemUnderTest.vertical(), is( V ) );
      assertThat( systemUnderTest.horizontal(), is( H ) );
   }//End Method
   
   @Test public void shouldBeEqual(){
      assertThat( new EnvironmentPosition( 1, 2 ), is( new EnvironmentPosition( 1, 2 ) ) );
      assertThat( systemUnderTest, is( new EnvironmentPosition( V, H ) ) );
      assertThat( systemUnderTest, is( systemUnderTest ) );
      
      assertThat( new EnvironmentPosition( 1, 2 ).hashCode(), is( new EnvironmentPosition( 1, 2 ).hashCode() ) );
      assertThat( systemUnderTest.hashCode(), is( new EnvironmentPosition( V, H ).hashCode() ) );
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
