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
      this.environment = new EnvironmentElement[ height ][ width ];
      for ( int vertical = 0; vertical < height; vertical++ ) {
         for ( int horizontal = 0; horizontal < width; horizontal++ ) {
            environment[ vertical ][ horizontal ] = EnvironmentElement.Space;
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
      final int startY = boundaryStartLocation.horizontal();
      if ( stepsEast > 0 ) {
         for ( int i = startY; i < startY + Math.abs( stepsEast ); i++ ) {
            place( new EnvironmentPosition( boundaryStartLocation.vertical(), i ), EnvironmentElement.Boundary );
         }
      } else {
         for ( int i = startY; i > startY - Math.abs( stepsEast ); i-- ) {
            place( new EnvironmentPosition( boundaryStartLocation.vertical(), i ), EnvironmentElement.Boundary );
         }
      }
   }//End Method
   
   /**
    * Method to apply a vertical boundary from the given {@link EnvironmentPosition} with the given number
    * of steps south, where negative would move north.
    * @param boundaryStartLocation the {@link EnvironmentPosition} to start the boundary at.
    * @param stepsSouth the number of steps south, or north if negative.
    */
   public void applyVerticalBoundary( EnvironmentPosition boundaryStartLocation, int stepsSouth ) {
      final int startX = boundaryStartLocation.vertical();
      if ( stepsSouth > 0 ) {
         for ( int i = startX; i < startX + Math.abs( stepsSouth ); i++ ) {
            place( new EnvironmentPosition( i, boundaryStartLocation.horizontal() ), EnvironmentElement.Boundary );
         }
      } else {
         for ( int i = startX; i > startX - Math.abs( stepsSouth ); i-- ) {
            place( new EnvironmentPosition( i, boundaryStartLocation.horizontal() ), EnvironmentElement.Boundary );
         }
      }
   }//End Method
   
   /**
    * Method to place an {@link EnvironmentElement} at the given {@link EnvironmentPosition}. 
    * @param position the {@link EnvironmentPosition} to position at.
    * @param element the {@link EnvironmentElement} at that position.
    */
   private void place( EnvironmentPosition position, EnvironmentElement element ) {
      environment[ position.vertical() ][ position.horizontal() ] = element;
   }//End Method
   
   /**
    * Method to access the {@link EnvironmentElement} at the given {@link EnvironmentPosition}.
    * @param position the {@link EnvironmentPosition}in question.
    * @return the {@link EnvironmentElement} at that position.
    */
   private EnvironmentElement element( EnvironmentPosition position ) {
      int vertical = position.vertical();
      int horizontal = position.horizontal();
      
      if ( 
               vertical < 0       || horizontal < 0 ||
               vertical >= height || horizontal >= width 
      ) {
         return EnvironmentElement.Boundary;
      }
      
      return environment[ vertical ][ horizontal ];
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
