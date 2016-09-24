package uk.dangrew.abm.model;

/**
 * The {@link Environment} represents a basic grid system that {@link Agent}s can move in.
 */
public class Environment implements NeighbourHood {

   private final int width;
   private final int height;
   private EnvironmentElement[][] environment;
   
   /**
    * Constructs a new {@link Environment}.
    * @param width the width of the grid.
    * @param height the height of the grid.
    */
   public Environment( int width, int height ) {
      if ( width <= 0 || height <= 0 ) {
         throw new IllegalArgumentException( "Must provide positive dimensions." );
      }
      
      this.width = width;
      this.height = height;
      this.environment = new EnvironmentElement[ width ][ height ];
      for ( int i = 0; i < width; i++ ) {
         for ( int j = 0; j < height; j++ ) {
            environment[ i ][ j ] = EnvironmentElement.Space;
         }
      }
   }//End Constructor

   /**
    * Method to apply a horizontal boundary from the given {@link EnvironmentPosition} with the given number
    * of steps east, where negative would move west.
    * @param boundaryStartLocation the {@link EnvironmentPosition} to start the boundary at.
    * @param stepsEast the number of steps east, or west if negative.
    */
   public void applyHorizontalBoundary( EnvironmentPosition boundaryStartLocation, int stepsEast ) {
      if ( stepsEast > 0 ) {
         for ( int i = boundaryStartLocation.y(); i < boundaryStartLocation.y() + Math.abs( stepsEast ); i++ ) {
            place( new EnvironmentPosition( boundaryStartLocation.x(), i ), EnvironmentElement.Boundary );
         }
      } else {
         for ( int i = boundaryStartLocation.y(); i > boundaryStartLocation.y() - Math.abs( stepsEast ); i-- ) {
            place( new EnvironmentPosition( boundaryStartLocation.x(), i ), EnvironmentElement.Boundary );
         }
      }
   }//End Method
   
   /**
    * Method to place an {@link EnvironmentElement} at the given {@link EnvironmentPosition}. 
    * @param position the {@link EnvironmentPosition} to position at.
    * @param element the {@link EnvironmentElement} at that position.
    */
   private void place( EnvironmentPosition position, EnvironmentElement element ) {
      environment[ position.x() ][ position.y() ] = element;
   }//End Method
   
   /**
    * Method to access the {@link EnvironmentElement} at the given {@link EnvironmentPosition}.
    * @param position the {@link EnvironmentPosition}in question.
    * @return the {@link EnvironmentElement} at that position.
    */
   private EnvironmentElement element( EnvironmentPosition position ) {
      return environment[ position.x() ][ position.y() ];
   }//End Method

   /**
    * Method to determine whether the given {@link EnvironmentPosition} is a {@link EnvironmentElement#Boundary}.
    * @param position the {@link EnvironmentPosition} in question.
    * @return true if a {@link EnvironmentElement#Boundary}.
    */
   public boolean isBoundary( EnvironmentPosition position ) {
      return element( position ) == EnvironmentElement.Boundary;
   }//End Method
   
   /**
    * Method to determine whether the given {@link EnvironmentPosition} is a {@link EnvironmentElement#Space}.
    * @param position the {@link EnvironmentPosition} in question.
    * @return true if a {@link EnvironmentElement#Space}.
    */
   public boolean isSpace( EnvironmentPosition position ) {
      return element( position ) == EnvironmentElement.Space;
   }//End Method

   /**
    * Access to the width of the {@link Environment}.
    * @return the width.
    */
   public int width() {
      return width;
   }//End Method

   /**
    * Access to the height of the {@link Environment}.
    * @return the height.
    */
   public int height() {
      return height;
   }//End Method

}//End Class
