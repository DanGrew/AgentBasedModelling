package uk.dangrew.abm.model.agent;

import java.util.List;
import java.util.Random;

import uk.dangrew.abm.model.environment.Environment;

/**
 * {@link MaleParentHood} provides a {@link ParentHood} for males to mate with females.
 */
class MaleParentHood implements ParentHood {

   static final int MATING_COOL_OFF_PERIOD = 10;
   
   private final Random random;
   private Agent male;
   private NeighbourHood neighbourHood;
   private int urgeToMate;
   
   /**
    * Constructs a new {@link MaleParentHood}.
    */
   MaleParentHood() {
      this( new Random() );
   }//End Constructor
   
   /**
    * Constructs a new {@link MaleParentHood}.
    * @param random the {@link Random}.
    */
   MaleParentHood( Random random ) {
      this.random = random;
      this.urgeToMate = 0;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( Agent agent, NeighbourHood neighbourHood ) {
      if ( this.male != null || this.neighbourHood != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.male = agent;
      this.neighbourHood = neighbourHood;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean mate( Agent agent ) {
      return false;
   }//End Method
   
   /**
    * Method to determine whether the {@link Agent} associated is ready to mate.
    * @return true if the {@link Agent} will mate with the next appropriate {@link Agent}.
    */
   boolean isReadyForMate(){
      return urgeToMate > male.matingCycle();
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void mingle( Environment environment ) {
      if ( !isMatingAge( male ) ) {
         return;
      }
      
      if ( !isReadyForMate() ) {
         urgeToMate++;
         return;
      }
      
      List< Agent > females = neighbourHood.neighbours( NeighbourHoodRange.Separation )
               .filtered( agent -> agent.gender() == Gender.Female )
               .filtered( agent -> isMatingAge( agent ) );
      if ( females.isEmpty() ) {
         return;
      }
      int nextMate = random.nextInt( females.size() );
      Agent female = females.get( nextMate );
      female.mate( male );
      
      urgeToMate = -MATING_COOL_OFF_PERIOD;
   }//End Method

}//End Class
