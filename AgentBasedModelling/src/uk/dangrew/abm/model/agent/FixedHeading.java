package uk.dangrew.abm.model.agent;

/**
 * {@link FixedHeading} provides a {@link Enum} of available {@link Heading}s for the
 * {@link Agent} to take.
 */
enum FixedHeading {

   NORTH( -10,   0 ),
   NE_91(  -9,   1 ),
   NE_82(  -8,   2 ),
   NE_73(  -7,   3 ),
   NE_64(  -6,   4 ),
   NE_55(  -5,   5 ),
   NE_46(  -4,   6 ),
   NE_37(  -3,   7 ),
   NE_28(  -2,   8 ),
   NE_19(  -1,   9 ),
   EAST (   0,  10 ),
   SE_19(   1,   9 ),
   SE_28(   2,   8 ),
   SE_37(   3,   7 ),
   SE_46(   4,   6 ),
   SE_55(   5,   5 ),
   SE_64(   6,   4 ),
   SE_73(   7,   3 ),
   SE_82(   8,   2 ),
   SE_91(   9,   1 ),
   SOUTH(  10,   0 ),
   SW_91(   9,  -1 ),
   SW_82(   8,  -2 ),
   SW_73(   7,  -3 ),
   SW_64(   6,  -4 ),
   SW_55(   5,  -5 ),
   SW_46(   4,  -6 ),
   SW_37(   3,  -7 ),
   SW_28(   2,  -8 ),
   SW_19(   1,  -9 ),
   WEST (   0, -10 ),
   NW_19(  -1,  -9 ),
   NW_28(  -2,  -8 ),
   NW_37(  -3,  -7 ),
   NW_46(  -4,  -6 ),
   NW_55(  -5,  -5 ),
   NW_64(  -6,  -4 ),
   NW_73(  -7,  -3 ),
   NW_82(  -8,  -2 ),
   NW_91(  -9,  -1 );
   
   private final int vertical;
   private final int horionztal;
   private final Heading heading;
   private final Heading opposite;
   
   /**
    * Constructs a new {@link FixedHeading}.
    * @param vertical the vertical heading.
    * @param horizontal the horizontal heading.
    */
   private FixedHeading( int vertical, int horizontal ) {
      this.vertical = vertical;
      this.horionztal = horizontal;
      this.heading = new Heading( vertical, horizontal );
      this.opposite = new Heading( -vertical, -horizontal );
   }//End Constructor
   
   /**
    * Access to the vertical position.
    * @return the vertical position.
    */
   public int vertical(){
      return vertical;
   }//End Method
   
   /**
    * Access to the horizontal position.
    * @return the horizontal position.
    */
   public int horizontal(){
      return horionztal;
   }//End Method
   
   /**
    * Access to the {@link Heading}.
    * @return the {@link Heading}.
    */
   public Heading heading(){
      return heading;
   }//End Method
   
   /**
    * Access to the {@link Heading} in the opposite direction.
    * @return the {@link Heading} in the opposite direction.
    */
   public Heading opposite(){
      return opposite;
   }//End Method
}//End Enum
