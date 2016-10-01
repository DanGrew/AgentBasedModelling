package uk.dangrew.abm.model.agent;

import uk.dangrew.abm.model.environment.Environment;

/**
 * {@link ParentHood} provides an interface for the parenthood responsibilities
 * and behaviour of an {@link Agent}. 
 */
interface ParentHood {

   /**
    * Method to associated the {@link Agent} parent and the {@link NeighbourHood} around the 
    * {@link Agent}.
    * @param agent the parent.
    * @param neighbourHood the {@link NeighbourHood} associated.
    */
   public void associate( Agent agent, NeighbourHood neighbourHood );

   /**
    * Method to mate with the given {@link Agent}. This does not always produce offspring.
    * @param agent the {@link Agent} to mate with.
    * @return true if successfully mated.
    */
   public boolean mate( Agent agent );
   
   /**
    * Method to mingle with other {@link Agent}s in the environment. This could result in mating
    * or progressing through pregnancy.
    * @param environment the {@link Environment} to mingle in.
    */
   public void mingle( Environment environment );
   
   /**
    * Method to determine whether the {@link Agent} given is of mating age.
    * @param agent the {@link Agent} in question.
    * @return true if can mate, false otherwise.
    */
   public default boolean isMatingAge( Agent agent ) {
      switch( agent.getAgeBracket() ) {
         case Infant:
         case Youth:
         case Complete:
         default:
            return false;
         case Elder:
         case Adult:
            return true;
      }
   }//End Method

}//End Interface