package uk.dangrew.abm.graphics;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import uk.dangrew.abm.model.environment.Environment;

/**
 * The {@link SimulationUI} is the top level object for the UI for simulating.
 */
public class SimulationUI extends StackPane {
   
   /**
    * Constructs a new {@link SimulationUI}.
    * @param environment the {@link Environment} associated.
    */
   public SimulationUI( Environment environment ) {
      getChildren().addAll( new EnvironmentWidget( environment ) );
      setBackground( new Background( new BackgroundFill( Color.LIGHTGREY, null, null ) ) );
   }//End Constructor

}//End Class
