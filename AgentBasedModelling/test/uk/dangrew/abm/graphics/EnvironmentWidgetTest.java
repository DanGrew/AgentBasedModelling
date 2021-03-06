package uk.dangrew.abm.graphics;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.dangrew.abm.model.agent.AgeBracket;
import uk.dangrew.abm.model.agent.Agent;
import uk.dangrew.abm.model.agent.AgentImpl;
import uk.dangrew.abm.model.agent.Gender;
import uk.dangrew.abm.model.agent.Heading;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentElement;
import uk.dangrew.abm.model.environment.EnvironmentPosition;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class EnvironmentWidgetTest {

   private Environment environment;
   private EnvironmentWidget systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      environment = new Environment( 20, 20 );
      environment.applyHorizontalBoundary( 0, 0, 10 );
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
      assertThat( systemUnderTest.identifyColourFor( EnvironmentElement.Space ), is( Color.WHITE ) );
   }//End Method
   
   @Test public void shouldColourRectangleBasedOnAgentAgeBracket(){
      Agent agent = mock( Agent.class );
      
      when( agent.gender() ).thenReturn( Gender.Male );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Adult );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.MALE_ADULT_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Complete );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.COMPLETE_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Elder );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.MALE_ELDER_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Infant );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.MALE_INFANT_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Youth );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.MALE_YOUTH_COLOUR ) );

      when( agent.gender() ).thenReturn( Gender.Female );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Adult );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.FEMALE_ADULT_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Complete );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.COMPLETE_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Elder );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.FEMALE_ELDER_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Infant );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.FEMALE_INFANT_COLOUR ) );
      when( agent.getAgeBracket() ).thenReturn( AgeBracket.Youth );
      assertThat( systemUnderTest.identifyColourFor( agent ), is( EnvironmentWidget.FEMALE_YOUTH_COLOUR ) );
   }//End Method
   
   @Test public void shouldRemoveColourFromOldPositionAndApplyColourAtNewPosition(){
      EnvironmentPosition initialPosition = environmentPosition( 5, 6 ); 
      Agent agent = new AgentImpl( initialPosition, new Heading( 10, 10 ) );
      environment.monitorAgent( agent );
      
      assertThat( systemUnderTest.representationFor( initialPosition ).getFill(), is( systemUnderTest.identifyColourFor( agent ) ) );
      agent.move( environment );
      assertThat( systemUnderTest.representationFor( initialPosition ).getFill(), is( Color.WHITE ) );
      assertThat( systemUnderTest.representationFor( environmentPosition( 6, 7 ) ).getFill(), is( systemUnderTest.identifyColourFor( agent ) ) );
   }//End Method
}//End Class
