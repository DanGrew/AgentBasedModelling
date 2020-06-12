package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InfantPositioningTest {

   private ControllableAgent infant;
   private ControllableAgent adult1;
   private ControllableAgent adult2;
   private ControllableAgent adult3;
   private ControllableAgent elder;
   
   private ControllableAgent subject;
   @Mock private NeighbourHood neighbourHood;
   private ObservableList< Agent > neighbours;
   
   private InfantPositioning systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      neighbours = FXCollections.observableArrayList();
      when( neighbourHood.neighbours( NeighbourHoodRange.Separation ) ).thenReturn( neighbours );
      
      infant = new AgentImpl( environmentPosition( 0, 0 ), FixedHeading.EAST.heading() );
      LifecycleTesting.setAgeBracket( infant, AgeBracket.Infant );
      adult1 = new AgentImpl( environmentPosition( 0, 0 ), FixedHeading.EAST.heading() );
      LifecycleTesting.setAgeBracket( adult1, AgeBracket.Adult );
      adult2 = new AgentImpl( environmentPosition( 0, 0 ), FixedHeading.EAST.heading() );
      LifecycleTesting.setAgeBracket( adult2, AgeBracket.Adult );
      adult3 = new AgentImpl( environmentPosition( 0, 0 ), FixedHeading.EAST.heading() );
      LifecycleTesting.setAgeBracket( adult3, AgeBracket.Adult );
      elder = new AgentImpl( environmentPosition( 0, 0 ), FixedHeading.EAST.heading() );
      LifecycleTesting.setAgeBracket( elder, AgeBracket.Elder );
      
      subject = new AgentImpl( environmentPosition( 0, 0 ), FixedHeading.SOUTH.heading() );
      systemUnderTest = new InfantPositioning();
   }//End Method

   @Test public void shouldNotProvideHeadingWhenNoOthersPresent() {
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotProvideHeadingWhenNoAdultsPresent() {
      neighbours.addAll( infant, elder );
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldFollowSingleAdult() {
      neighbours.add( adult1 );
      adult1.setPosition( environmentPosition( 6, 4 ) );
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( new Heading( 6, 4 ) ) );
   }//End Method
   
   @Test public void shouldFollowSingleAdultRelativeToPositionVertically() {
      subject.setPosition( environmentPosition( 2, 4 ) );
      
      neighbours.add( adult1 );
      adult1.setPosition( environmentPosition( 6, 4 ) );
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( FixedHeading.SOUTH.heading() ) );
   }//End Method
   
   @Test public void shouldFollowSingleAdultRelativeToPositionHorizontally() {
      subject.setPosition( environmentPosition( 6, 2 ) );
      
      neighbours.add( adult1 );
      adult1.setPosition( environmentPosition( 6, 4 ) );
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( FixedHeading.EAST.heading() ) );
   }//End Method
   
   @Test public void shouldFollowSingleAdultNegatively() {
      neighbours.add( adult1 );
      adult1.setPosition( environmentPosition( -6, -4 ) );
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( new Heading( -6, -4 ) ) );
   }//End Method
   
   @Test public void shouldHeadTowardsAveragePositionOfAdults() {
      neighbours.add( adult1 );
      adult1.setPosition( environmentPosition( 6, 4 ) );
      neighbours.add( adult2 );
      adult2.setPosition( environmentPosition( 2, 8 ) );
      neighbours.add( adult3 );
      adult3.setPosition( environmentPosition( 1, 9 ) );
      
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( new Heading( 3, 7 ) ) );
   }//End Method
   
   @Test public void shouldHeadTowardsAveragePositionOfAdultsNegatively() {
      neighbours.add( adult1 );
      adult1.setPosition( environmentPosition( -3, -7 ) );
      neighbours.add( adult2 );
      adult2.setPosition( environmentPosition( -2, 8 ) );
      neighbours.add( adult3 );
      adult3.setPosition( environmentPosition( 1, -9 ) );
      
      assertThat( systemUnderTest.findHeadingToAdultCenteredPosition( subject, neighbourHood ), is( new Heading( -3, -7 ) ) );
   }//End Method

}//End Class
