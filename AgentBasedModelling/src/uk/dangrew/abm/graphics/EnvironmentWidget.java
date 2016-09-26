package uk.dangrew.abm.graphics;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.MapChangeListener.Change;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.dangrew.abm.model.Agent;
import uk.dangrew.abm.model.Environment;
import uk.dangrew.abm.model.EnvironmentElement;
import uk.dangrew.abm.model.EnvironmentPosition;

/**
 * The {@link EnvironmentWidget} provides a graphical representation of an {@link Environment}.
 */
public class EnvironmentWidget extends GridPane {

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
         Rectangle rectangle = new Rectangle( 5, 5 );
         rectangle.setFill( identifyColourFor( element ) );
         representation.put( position, rectangle );
         
         BorderPane border = new BorderPane( rectangle );
         border.setPadding( new Insets( 1 ) );
         add( border, position.horizontal(), position.vertical() );
      } );
      
      environment.agents().addListener( ( Change< ? extends EnvironmentPosition, ? extends Agent > change ) -> {
         if ( change.wasRemoved() ) {
            representation.get( change.getKey() ).setFill( identifyColourFor( EnvironmentElement.Space ) );
         } else {
            representation.get( change.getKey() ).setFill( Color.RED );
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
            return Color.LIGHTGRAY;
         default:
            return Color.RED;
      }
   }//End Method
   
   Rectangle representationFor( EnvironmentPosition position ) {
      return representation.get( position );
   }//End Method

}//End Class
