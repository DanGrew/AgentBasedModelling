package uk.dangrew.abm.model.agent;

import java.util.Random;

import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * The {@link FemaleParentHood} provides the {@link ParentHood} behaviour of a {@link Gender#Female}
 * {@link Agent}.
 */
class FemaleParentHood implements ParentHood {

   static final int MATING_CHANCE = 30;
   static final int MATING_DENOMINATOR = 100;
   static final int PREGNANCY_PERIOD = 100;
   static final int PREGNANCY_RECOVERY = PREGNANCY_PERIOD / 6;

   private final Random random;
   private Agent female;
   private NeighbourHood neighbourHood;
   
   private Agent maleParent;
   private int pregnancyLength;
   
   /**
    * Constructs a new {@link FemaleParentHood}.
    */
   FemaleParentHood() {
      this( new Random() );
   }//End Constructor
   
   /**
    * Constructs a new {@link FemaleParentHood}.
    * @param random the {@link Random}.
    */
   FemaleParentHood( Random random ) {
      this.random = random;
      this.pregnancyLength = 0;
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void associate( Agent agent, NeighbourHood neighbourHood ) {
      if ( this.female != null || this.neighbourHood != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.female = agent;
      this.neighbourHood = neighbourHood;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public boolean mate( Agent agent ) {
      if ( maleParent != null ) {
         return false;
      }
      
      if ( agent.gender() != Gender.Male ) {
         return false;
      }
      
      if ( !isMatingAge( agent ) ) {
         return false;
      }
      
      if ( pregnancyLength < 0 ) {
         return false;
      }
      
      int chance = random.nextInt( MATING_DENOMINATOR );
      if ( chance > MATING_CHANCE ) {
         return false;
      }
      
      this.maleParent = agent;
      return true;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public void mingle( Environment environment ) {
      if ( !isMatingAge( female ) ) {
         return;
      }
      
      if ( maleParent != null || pregnancyLength < 0 ) {
         pregnancyLength++;
      }
      if ( pregnancyLength > PREGNANCY_PERIOD ) {
         EnvironmentPosition parentPosition = female.position().get();
         EnvironmentPosition offspringPosition = identifyOffspringInitialPosition( environment, parentPosition );
         if ( offspringPosition != null ) {
            Agent offspring = new AgentImpl( offspringPosition, female.heading().get() );
            environment.monitorAgent( offspring );
         }
         
         pregnancyLength = -PREGNANCY_RECOVERY;
         maleParent = null;
      }
   }//End Method
   
   /**
    * Method to identify the position of the offspring in the space around the parent.
    * @param environment the {@link Environment} in question.
    * @param parentPosition the {@link EnvironmentPosition} of the parent.
    * @return the empty {@link EnvironmentPosition} to place the offspring, or null if no space.
    */
   EnvironmentPosition identifyOffspringInitialPosition( Environment environment, EnvironmentPosition parentPosition ){
      EnvironmentPosition proposedOffspringPosition = parentPosition.translate( new Heading( -1, -1 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( 0, -1 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( 1, -1 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( 1, 0 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( 1, 1 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( 0, 1 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( -1, 1 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      proposedOffspringPosition = parentPosition.translate( new Heading( -1, 0 ) );
      if ( environment.isAvailable( proposedOffspringPosition ) ) {
         return proposedOffspringPosition;
      }
      
      return null;
   }//End Method

}//End Class
