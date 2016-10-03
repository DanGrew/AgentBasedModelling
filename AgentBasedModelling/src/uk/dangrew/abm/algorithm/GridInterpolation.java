package uk.dangrew.abm.algorithm;

import java.util.function.BiPredicate;

/**
 * {@link GridInterpolation} provides an algorithm for interpolating positions through a grid
 * providing feedback for each cell encountered.
 */
public class GridInterpolation {
   
   private final BiPredicate< Integer, Integer > predicate;
   
   /**
    * Constructs a new {@link GridInterpolation}.
    * @param predicate the {@link BiPredicate} to call when a cell is encountered.
    */
   public GridInterpolation( BiPredicate< Integer, Integer > predicate ) {
      this.predicate = predicate;
   }//End Constructor
   
   /**
    * Method to interpolate from the initial position to the target position, calling the {@link BiPredicate}
    * for each cell encountered.
    * @param initialVertical the initial vertical position.
    * @param initialHorizontal the target horizontal position.
    * @param targetVertical the target vertical position.
    * @param targetHorizontal the target horizontal position.
    * @return true if stopped by the {@link BiPredicate}.
    */
   public boolean interpolate( int initialVertical, int initialHorizontal, int targetVertical, int targetHorizontal ){
      if ( initialVertical == targetVertical ) {
         return flatHorizontalInterpolation( initialVertical, initialHorizontal, targetHorizontal );
      }
      
      if ( initialHorizontal == targetHorizontal ) {
         return flatVerticalInterpolation( initialVertical, initialHorizontal, targetVertical );
      }
      
      int changeInV = targetVertical - initialVertical;
      int changeInH = targetHorizontal - initialHorizontal;
      if ( Math.abs( changeInV ) > Math.abs( changeInH ) ) {
         return interpolateVertically( 
                  initialVertical, 
                  initialHorizontal, 
                  targetVertical, 
                  targetHorizontal 
         );
      } else {
         return interpolateHorizontally( 
                  initialVertical, 
                  initialHorizontal, 
                  targetVertical, 
                  targetHorizontal 
         );
      }
   }//End Method
   
   /**
    * Method to interpolate horizontally, where there is no gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialPosition the initial position to move from.
    * @param targetHorizontal the target horizontal position.
    * @return true if this position collides with something.
    */
   private boolean flatHorizontalInterpolation( int initialV, int initialH, int targetHorizontal ) {
      double horizontalDelta = targetHorizontal - initialH;
      
      if ( horizontalDelta >= 0 ) {
         for ( int horizontalProgress = initialH; horizontalProgress <= targetHorizontal; horizontalProgress++ ) { 
            if ( predicate.test( initialV, horizontalProgress ) ) {
               return true;
            }
         }
      } else {
         for ( int horizontalProgress = initialH; horizontalProgress >= targetHorizontal; horizontalProgress-- ) { 
            if ( predicate.test( initialV, horizontalProgress ) ) {
               return true;
            }
         }
      }
      return false;
   }//End Method
   
   /**
    * Method to interpolate vertically, where there is no gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialPosition the initial position to move from.
    * @param targetVertical the target vertical position.
    * @return true if this position collides with something.
    */
   private boolean flatVerticalInterpolation( int initialV, int initialH, int targetVertical ) {
      double verticalDelta = targetVertical - initialV;
      
      if ( verticalDelta >= 0 ) {
         for ( int verticalProgress = initialV; verticalProgress <= targetVertical; verticalProgress++ ) { 
            if ( predicate.test( verticalProgress, initialH ) ) {
               return true;
            }
         }
      } else {
         for ( int verticalProgress = initialV; verticalProgress >= targetVertical; verticalProgress-- ) { 
            if ( predicate.test( verticalProgress, initialH ) ) {
               return true;
            }
         }
      }
      return false;
   }//End Method
   
   /**
    * Method to interpolate vertically, where the vertical gradient is greater than or equal to the 
    * horizontal gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialVertical the initial vertical position.
    * @param initialHorizontal the initial horizontal position.
    * @param targetHorizontal the target horizontal position.
    * @param targetVertical the target vertical position.
    * @return true if this position collides with something.
    */
   private boolean interpolateVertically( int initialVertical, int initialHorizontal, int targetVertical, int targetHorizontal ){
      double verticalDelta = targetVertical - initialVertical;
      double horizontalDelta = targetHorizontal - initialHorizontal;
      double verticalStepSize = verticalDelta >= 0 ? 1 : -1;
      int horizontalStepSize = horizontalDelta >= 0 ? 1 : -1;
      double error = -1.0;
      double deltaError = Math.abs( horizontalDelta / verticalDelta ); 
      int horizontalProgress = initialHorizontal;
      
      if ( verticalStepSize >= 0 ) {
         for ( int verticalProgress = initialVertical; verticalProgress <= targetVertical; verticalProgress++ ) {
            if ( predicate.test( verticalProgress, horizontalProgress ) ) {
               return true;
            }
            
            error = error + deltaError;
            if ( error >= 0.0 ) {
               horizontalProgress = horizontalProgress + horizontalStepSize;
               error = error - 1;
            }
         }
      } else {
         for ( int verticalProgress = initialVertical; verticalProgress >= targetVertical; verticalProgress-- ) {
            if ( predicate.test( verticalProgress, horizontalProgress ) ) {
               return true;
            }
            error = error + deltaError;
            if ( error >= 0.0 ) {
               horizontalProgress = horizontalProgress + horizontalStepSize;
               error = error - 1;
            }
         }
      }
      return false;
   }//End Method
   
   /**
    * Method to interpolate horizontally, where the horizontal gradient is greater than or equal to the 
    * vertical gradient.
    * @param environment the {@link Environment} to move in.
    * @param initialVertical the initial vertical position.
    * @param initialHorizontal the initial horizontal position.
    * @param targetHorizontal the target horizontal position.
    * @param targetVertical the target vertical position.
    * @return true if this position collides with something.
    */
   private boolean interpolateHorizontally( int initialVertical, int initialHorizontal, int targetVertical, int targetHorizontal ){
      double verticalDelta = targetVertical - initialVertical;
      double horizontalDelta = targetHorizontal - initialHorizontal;
      int verticalStepSize = verticalDelta >= 0 ? 1 : -1;
      int horizontalStepSize = horizontalDelta >= 0 ? 1 : -1;
      double error = -1.0;
      double deltaError = Math.abs( verticalDelta / horizontalDelta ); 
      int verticalProgress = initialVertical;
      
      if ( horizontalStepSize >= 0 ) {
         for ( int horizontalProgress = initialHorizontal; horizontalProgress <= targetHorizontal; horizontalProgress++ ) {
            if ( predicate.test( verticalProgress, horizontalProgress ) ) {
               return true;
            }
            
            error = error + deltaError;
            if ( error >= 0.0 ) {
               verticalProgress = verticalProgress + verticalStepSize;
               error = error - 1;
            }
         }
      } else {
         for ( int horizontalProgress = initialHorizontal; horizontalProgress >= targetHorizontal; horizontalProgress-- ) {
            if ( predicate.test( verticalProgress, horizontalProgress ) ) {
               return true;
            }
            error = error + deltaError;
            if ( error >= 0.0 ) {
               verticalProgress = verticalProgress + verticalStepSize;
               error = error - 1;
            }
         }
      }
      return false;
   }//End Method

}//End Class
