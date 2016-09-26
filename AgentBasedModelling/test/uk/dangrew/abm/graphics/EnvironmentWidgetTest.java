package uk.dangrew.abm.graphics;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.dangrew.abm.model.Agent;
import uk.dangrew.abm.model.AgentImpl;
import uk.dangrew.abm.model.Environment;
import uk.dangrew.abm.model.EnvironmentElement;
import uk.dangrew.abm.model.EnvironmentPosition;
import uk.dangrew.abm.model.Heading;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class EnvironmentWidgetTest {

   private Environment environment;
   private EnvironmentWidget systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      environment = new Environment( 20, 20 );
      environment.applyHorizontalBoundary( new EnvironmentPosition( 0, 0 ), 10 );
      systemUnderTest = new EnvironmentWidget( environment );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 100000 );
   }//End Method
   
   @Test public void shouldProvideRectangleForEachPosition(){
      environment.grid().forEach( ( position, element ) -> {
         final Rectangle representation = systemUnderTest.representationFor( position );
         assertThat( representation, is( notNullValue() ) );
         assertThat( representation.getFill(), is( systemUnderTest.identifyColourFor( element ) ) );
      } );
   }//End Method
   
   @Test public void shouldColourRectangleBasedOnElementType(){
      assertThat( systemUnderTest.identifyColourFor( EnvironmentElement.Boundary ), is( Color.BLACK ) );
      assertThat( systemUnderTest.identifyColourFor( EnvironmentElement.Space ), is( Color.LIGHTGRAY ) );
   }//End Method
   
   @Test public void shouldRemoveColourFromOldPositionAndApplyColourAtNewPosition(){
      EnvironmentPosition initialPosition = new EnvironmentPosition( 5, 6 ); 
      Agent agent = new AgentImpl( initialPosition, new Heading( 1, 1 ) );
      environment.monitorAgent( agent );
      
      assertThat( systemUnderTest.representationFor( initialPosition ).getFill(), is( Color.YELLOW ) );
      agent.move( environment );
      assertThat( systemUnderTest.representationFor( initialPosition ).getFill(), is( Color.LIGHTGRAY ) );
      assertThat( systemUnderTest.representationFor( new EnvironmentPosition( 6, 7 ) ).getFill(), is( Color.YELLOW ) );
   }//End Method
}//End Class
