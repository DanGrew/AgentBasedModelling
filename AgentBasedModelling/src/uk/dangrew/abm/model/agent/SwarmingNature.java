package uk.dangrew.abm.model.agent;

interface SwarmingNature {

   /**
    * Method to associated the given {@link ControllableAgent} with the {@link HeadingAdjuster}.
    * @param agent the {@link ControllableAgent} to associate.
    * @param neighbourHood the {@link NeighbourHood} of the {@link Agent}.
    */
   public void associate( ControllableAgent agent, NeighbourHood neighbourHood );//End Method

   /**
    * Method to randomize the heading of the {@link Agent}.
    */
   public void randomizeHeading();//End Method

   /**
    * Method to respond to the {@link Agent}s in the {@link NeighbourHood} according to the rules
    * defined in this {@link NeighbourHood}.
    * @return true if affected by neighbouring {@link Agent}s.
    */
   public boolean respondToNeighbours();

}//End Interface