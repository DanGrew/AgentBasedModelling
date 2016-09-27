package uk.dangrew.abm.graphics;

import javafx.scene.layout.StackPane;
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
   }//End Constructor

}//End Class
