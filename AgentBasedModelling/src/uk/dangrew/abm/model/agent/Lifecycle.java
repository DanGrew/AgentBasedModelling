package uk.dangrew.abm.model.agent;

import java.util.Random;

import uk.dangrew.abm.model.environment.Environment;
import uk.dangrew.abm.model.environment.EnvironmentPosition;

/**
 * The {@link Lifecycle} provides a mechanism for tracking and controlling the age of an {@link Agent}.
 */
class Lifecycle {

   static final int STATIONARY_LIMIT = 50;
   static final int MAXIMUM_MATING_CYCLE = 30;
   
   static final int BASE_AGE = 1000;
   static final int MAX_AGE_BOOST = BASE_AGE / 4;
   static final int YOUTH_COME_OF_AGE = BASE_AGE / 5;
   static final int ADULT_COME_OF_AGE = YOUTH_COME_OF_AGE * 2;
   static final int ELDER_COME_OF_AGE = BASE_AGE - ( BASE_AGE / 10 );
   static final int REMOVE_FROM_ENVIRONMENT_AFTER = 1;//BASE_AGE / 10;
   
   private final Random random;
   private ControllableAgent agent;
   
   private EnvironmentPosition previousPosition;
   private int inPlaceFor;
   
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
      this.inPlaceFor = 0;
      this.previousPosition = null;
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
      
      boolean male = random.nextBoolean();
      if ( male ) {
         agent.setGender( Gender.Male, new MaleParentHood() );
      } else {
         agent.setGender( Gender.Female, new FemaleParentHood() );
      }
      
      int matingCycle = random.nextInt( MAXIMUM_MATING_CYCLE );
      agent.setMatingCycle( matingCycle );
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
      
      if ( previousPosition == null ) {
         previousPosition = agent.position().get();
         return;
      }
      
      if ( previousPosition.equals( agent.position().get() ) ) {
         inPlaceFor++;
      } else {
         inPlaceFor = 0;
      }
      
      if ( inPlaceFor > STATIONARY_LIMIT ) {
         agent.setAge( Math.max( agent.lifeExpectancy().get(), agent.age().get() ) );
      }
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
