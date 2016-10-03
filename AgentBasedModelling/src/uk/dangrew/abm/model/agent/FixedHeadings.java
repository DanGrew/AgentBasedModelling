package uk.dangrew.abm.model.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link FixedHeadings} provides a fixed range of {@link Heading}s for {@link Agent}s to 
 * follow when swarming.
 */
class FixedHeadings {

   static final double HEADING_RANGE = 10.0;
   private final Map< Heading, FixedHeading > headingsForTargets;
   
   /**
    * Constructs a new {@link FixedHeadings}.
    */
   FixedHeadings() {
      this.headingsForTargets = new HashMap<>();
      initialiseHeadings();
   }//End Constructor
   
   /**
    * Method to initialise the fixed headings for access.
    */
   private void initialiseHeadings(){
      for ( FixedHeading heading : FixedHeading.values() ) {
         headingsForTargets.put( heading.heading(), heading );
      }
   }//End Method

   /**
    * Method to get the {@link FixedHeading} for the given {@link Heading}, precisely equal to one
    * of the {@link FixedHeading}s.
    * @param heading the {@link Heading} to look for.
    * @return the associated {@link FixedHeading}.
    */
   public FixedHeading getFixedHeadingFor( Heading heading ) {
      return headingsForTargets.get( heading );
   }//End Method
   
   /**
    * Method to calculate the appropriate {@link FixedHeading} for the given heading values.
    * @param verticalHeading the vertical direction.
    * @param horizontalHeading the horizontal direction.
    * @return the {@link FixedHeading} associated, interpolating along the heading to find the
    * most relevant {@link FixedHeading}.
    */
   FixedHeading calculateHeading( double verticalHeading, double horizontalHeading ){
      /*
       * Some efficiency and simplicity.
       */
      if ( verticalHeading == 0 && horizontalHeading == 0 ) {
         return null;
      } else if ( verticalHeading == 0 ) {
         return horizontalHeading > 0 ? FixedHeading.EAST : FixedHeading.WEST;
      } else if ( horizontalHeading == 0 ) {
         return verticalHeading > 0 ? FixedHeading.SOUTH : FixedHeading.NORTH;
      }
      
      /* 
       * Equation of headings line: y = 10 - x
       * Equation of heading vector: y = (h/v)*x
       * 
       * ... 10 - x = (h/v) * x
       * ... 10 = (h/v) * x + x
       * ... 10 = (h/v) * x + (v/v) * x
       * ... 10 = ( (h + v) / v ) * x
       * ... x = 10 / ( (h + v) / v )
       */
      double xOnCurve = HEADING_RANGE / ( ( Math.abs( horizontalHeading ) + Math.abs( verticalHeading ) )/ Math.abs( verticalHeading ) );
      int convertedV = ( int )Math.round( xOnCurve );
      if ( verticalHeading < 0 ) {
         convertedV = Math.negateExact( convertedV );
      }
      int convertedH = ( int )HEADING_RANGE - Math.abs( convertedV );
      if ( horizontalHeading < 0 ) {
         convertedH = Math.negateExact( convertedH );
      }
      return headingsForTargets.get( new Heading( convertedV, convertedH ) );
   }//End Method
   
}//End Class
