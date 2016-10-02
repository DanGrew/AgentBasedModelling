package uk.dangrew.abm.graphics;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.MapChangeListener.Change;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.dangrew.abm.model.agent.Agent;
import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentElement;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * The {@link EnvironmentWidget} provides a graphical representation of an {@link Environment}.
 */
public class EnvironmentWidget extends GridPane {

   static final Color MALE_INFANT_COLOUR = Color.AQUA;
   static final Color MALE_YOUTH_COLOUR = Color.CORNFLOWERBLUE;
   static final Color MALE_ADULT_COLOUR = Color.BLUE;
   static final Color MALE_ELDER_COLOUR = Color.BLUE.darker();
   
   static final Color FEMALE_INFANT_COLOUR = Color.PINK;
   static final Color FEMALE_YOUTH_COLOUR = Color.PALEVIOLETRED;
   static final Color FEMALE_ADULT_COLOUR = Color.RED;
   static final Color FEMALE_ELDER_COLOUR = Color.RED.darker();
   
   static final Color COMPLETE_COLOUR = Color.GRAY;
   
   private final Environment environment;
   private final Map< EnvironmentPosition, Rectangle > representation;
   
   /**
    * Constructs a new {@link EnvironmentWidget}.
    * @param environment the {@link Environment} to represent.
    */
   public EnvironmentWidget( Environment environment ) {
      this.environment = environment;
      this.representation = new HashMap<>();
      
      environment.grid().forEach( ( position, element ) -> {
         Rectangle rectangle = new Rectangle( 8, 8 );
         rectangle.setFill( identifyColourFor( element ) );
         representation.put( position, rectangle );
         
         BorderPane border = new BorderPane( rectangle );
//         border.setPadding( new Insets( 1 ) );
         add( border, position.horizontal(), position.vertical() );
      } );
      
      environment.agents().addListener( ( Change< ? extends EnvironmentPosition, ? extends Agent > change ) -> {
         if ( change.wasRemoved() ) {
            representation.get( change.getKey() ).setFill( identifyColourFor( EnvironmentElement.Space ) );
         } else {
            representation.get( change.getKey() ).setFill( identifyColourFor( change.getValueAdded() ) );
         }
      } );
   }//End Constructor
   
   /**
    * Method to identify the {@link Color} associated with the given {@link EnvironmentElement}.
    * @param element the {@link EnvironmentElement} in question.
    * @return the {@link Color}.
    */
   Color identifyColourFor( EnvironmentElement element ) {
      switch( element ) {
         case Boundary:
            return Color.BLACK;
         case Space:
            return Color.WHITE;
         default:
            return Color.RED;
      }
   }//End Method
   
   /**
    * Method to identify the {@link Color} associated with the given {@link Agent}, according to its
    * {@link AgeBracket}.
    * @param agent the {@link Agent} in question.
    * @return the {@link Color} associated.
    */
   Color identifyColourFor( Agent agent ) {
      switch( agent.gender() ) {
         case Male:
            switch( agent.getAgeBracket() ) {
               case Adult:
                  return MALE_ADULT_COLOUR;
               case Complete:
                  return COMPLETE_COLOUR;
               case Elder:
                  return MALE_ELDER_COLOUR;
               case Infant:
                  return MALE_INFANT_COLOUR;
               case Youth:
                  return MALE_YOUTH_COLOUR;
               default:
                  return Color.GREEN;
            }
         case Female:
            switch( agent.getAgeBracket() ) {
               case Adult:
                  return FEMALE_ADULT_COLOUR;
               case Complete:
                  return COMPLETE_COLOUR;
               case Elder:
                  return FEMALE_ELDER_COLOUR;
               case Infant:
                  return FEMALE_INFANT_COLOUR;
               case Youth:
                  return FEMALE_YOUTH_COLOUR;
               default:
                  return Color.GREEN;
            }
         default:
            return Color.GREEN;
      }
   }//End Method
   
   Rectangle representationFor( EnvironmentPosition position ) {
      return representation.get( position );
   }//End Method

}//End Class
