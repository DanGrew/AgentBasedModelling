package uk.dangrew.abm.model.agent;

import java.util.Random;

import uk.dangrew.abm.model.environment.Environment;

/**
 * The {@link Lifecycle} provides a mechanism for tracking and controlling the age of an {@link Agent}.
 */
class Lifecycle {

   static final int BASE_AGE = 1000;
   static final int MAX_AGE_BOOST = BASE_AGE / 4;
   static final int YOUTH_COME_OF_AGE = BASE_AGE / 5;
   static final int ADULT_COME_OF_AGE = YOUTH_COME_OF_AGE * 2;
   static final int ELDER_COME_OF_AGE = BASE_AGE - ( BASE_AGE / 10 );
   static final int REMOVE_FROM_ENVIRONMENT_AFTER = BASE_AGE / 10;
   
   private final Random random;
   private ControllableAgent agent;
   
   /**
    * Constructs a new {@link Lifecycle}.
    */
   Lifecycle() {
      this( new Random() );
   }//End Constructor
   
   /**
    * Constructs a new {@link Lifecycle}.
    * @param randomizer the {@link Random}.
    */
   Lifecycle( Random randomizer ) {
      this.random = randomizer;
   }//End Constructor

   /**
    * Method to associated the {@link ControllableAgent} with this {@link Lifecycle}.
    * @param agent the {@link ControllableAgent} to associate.
    */
   public void associate( ControllableAgent agent ) {
      if ( this.agent != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.agent = agent;
   }//End Method
   
   /**
    * Method to birth the {@link Agent} into the world, giving it a life expectancy and age.
    */
   public void birth() {
      agent.setLifeExpectancy( BASE_AGE + random.nextInt( MAX_AGE_BOOST ) );
      agent.setAge( 0 );
   }//End Method

   /**
    * Method to age the {@link Agent} in the given {@link Environment}.
    * @param environment the {@link Environment} to age in.
    */
   public void age( Environment environment ) {
      int age = agent.age().get();
      if ( age >= agent.lifeExpectancy().get() + REMOVE_FROM_ENVIRONMENT_AFTER ) {
         environment.cleanAgentUp( agent );
         return;
      }
      
      agent.setAge( age + 1 );
   }//End Method

   /**
    * Method to gge the {@link AgeBracket} for the {@link Agent}s current age.
    * @return the current {@link AgeBracket}.
    */
   public AgeBracket getAgeBracket() {
      int age = agent.age().get();
      if ( age < YOUTH_COME_OF_AGE ) {
         return AgeBracket.Infant;
      } else if ( age < ADULT_COME_OF_AGE ) {
         return AgeBracket.Youth;
      } else if ( age < ELDER_COME_OF_AGE ) {
         return AgeBracket.Adult;
      } else if ( age < agent.lifeExpectancy().get() ) {
         return AgeBracket.Elder;
      } else {
         return AgeBracket.Complete;
      }
   }//End Method

}//End Class
