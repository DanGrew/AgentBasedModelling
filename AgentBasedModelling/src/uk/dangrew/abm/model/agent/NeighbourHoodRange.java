package uk.dangrew.abm.model.agent;

public enum NeighbourHoodRange {

   Separation( 3 ),
   Cohesion( 7 );
   
   private final double distanceFromAgent;
   
   private NeighbourHoodRange( double distanceFromAgent ) {
      this.distanceFromAgent = distanceFromAgent;
   }//End Constructor
   
   public double distanceFromAgent(){
      return distanceFromAgent;
   }//End Method
   
   public boolean isWithinRange( double distanceFromAgent ) {
      return distanceFromAgent <= this.distanceFromAgent;
   }//End Method
   
}//End Enum
