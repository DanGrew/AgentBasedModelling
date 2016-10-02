package uk.dangrew.abm.model.environment;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.abm.model.agent.Heading;

public class EnvironmentPositioningImplTest {

   private EnvironmentPosition position;
   private EnvironmentPositioningImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      position = new EnvironmentPosition( 10, 11 );
      systemUnderTest = new EnvironmentPositioningImpl();
   }//End Method
   
   @Test public void shouldLocatePositionValues(){
      assertThat( systemUnderTest.locate( 45, 101 ), is( new EnvironmentPosition( 45, 101 ) ) );
   }//End Method

   @Test public void shouldProvideHorizontalOffsetPosition(){
      assertThat( systemUnderTest.horizontalPositionOffset( position, 5 ), is( new EnvironmentPosition( position.vertical(), position.horizontal() + 5 ) ) );
      assertThat( systemUnderTest.horizontalPositionOffset( position, -5 ), is( new EnvironmentPosition( position.vertical(), position.horizontal() - 5 ) ) );
   }//End Method
   
   @Test public void shouldProvideVerticalOffsetPosition(){
      assertThat( systemUnderTest.verticalPositionOffset( position, 5 ), is( new EnvironmentPosition( position.vertical() + 5, position.horizontal() ) ) );
      assertThat( systemUnderTest.verticalPositionOffset( position, -5 ), is( new EnvironmentPosition( position.vertical() - 5, position.horizontal() ) ) );
   }//End Method
   
   @Test public void shouldProvideOffsetPosition(){
      assertThat( systemUnderTest.translate( position, new Heading( 10, 4 ) ), is( new EnvironmentPosition( position.vertical() + 10, position.horizontal() + 4 ) ) );
      assertThat( systemUnderTest.translate( position, new Heading( -6, -3 ) ), is( new EnvironmentPosition( position.vertical() - 6, position.horizontal() - 3 ) ) );
   }//End Method

}//End Class
