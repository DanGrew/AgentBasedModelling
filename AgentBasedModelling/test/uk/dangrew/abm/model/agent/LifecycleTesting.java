package uk.dangrew.abm.model.agent;

/**
 * {@link LifecycleTesting} provides a convenient way of putting the {@link Agent}
 * in the correct state for testing.
 */
public class LifecycleTesting {
   
   /**
    * Method to set the {@link AgeBracket} by adjusting the {@link Agent}s age.
    * @param agent the {@link ControllableAgent} to set the age on.
    * @param ageBracket the {@link AgeBracket} to be in.
    */
   static void setAgeBracket( ControllableAgent agent, AgeBracket ageBracket ) {
      switch( ageBracket ) {
         case Adult:
            agent.setAge( Lifecycle.ADULT_COME_OF_AGE );
            break;
         case Complete:
            throw new UnsupportedOperationException();
         case Elder:
            agent.setAge( Lifecycle.ELDER_COME_OF_AGE );
            break;
         case Infant:
            agent.setAge( 0 );
            break;
         case Youth:
            agent.setAge( Lifecycle.YOUTH_COME_OF_AGE );
            break;
         default:
            break;
      }
   }//End Method

}//End Class
