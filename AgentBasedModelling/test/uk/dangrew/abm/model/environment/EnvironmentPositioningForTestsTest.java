package uk.dangrew.abm.model.environment;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.translate;

import org.junit.Test;

public class EnvironmentPositioningForTestsTest {

   @Test public void shouldConstructEnvironmentPosition(){
      EnvironmentPosition position = environmentPosition( 2, 3 );
      assertThat( position.vertical(), is( 2 ) );
      assertThat( position.horizontal(), is( 3 ) );
   }//End Method
   
   @Test public void shouldTranslatePosition() {
      assertThat( translate( environmentPosition( 2, 3 ), 10, 4 ), is( environmentPosition( 12, 7 ) ) );
      assertThat( translate( environmentPosition( 2, 3 ), -6, -3 ), is( environmentPosition( -4, 0 ) ) );
   }//End Method

}//End Class
