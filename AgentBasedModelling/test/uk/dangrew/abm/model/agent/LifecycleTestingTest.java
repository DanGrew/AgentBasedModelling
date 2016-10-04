package uk.dangrew.abm.model.agent;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.abm.model.environment.EnvironmentPositioningForTests.environmentPosition;

import org.junit.Test;

public class LifecycleTestingTest {

   @Test public void shouldPutAgentInAgeBracket() {
      ControllableAgent agent = new AgentImpl( environmentPosition( 0, 0 ), new Heading( 1, 1 ) );
      assertThat( agent.age().get(), is( 0 ) );
      assertThat( agent.getAgeBracket(), is( AgeBracket.Infant ) );
      
      LifecycleTesting.setAgeBracket( agent, AgeBracket.Adult );
      assertThat( agent.age().get(), is( Lifecycle.ADULT_COME_OF_AGE ) );
      assertThat( agent.getAgeBracket(), is( AgeBracket.Adult ) );
      
      LifecycleTesting.setAgeBracket( agent, AgeBracket.Elder );
      assertThat( agent.age().get(), is( Lifecycle.ELDER_COME_OF_AGE ) );
      assertThat( agent.getAgeBracket(), is( AgeBracket.Elder ) );
      
      LifecycleTesting.setAgeBracket( agent, AgeBracket.Youth );
      assertThat( agent.age().get(), is( Lifecycle.YOUTH_COME_OF_AGE ) );
      assertThat( agent.getAgeBracket(), is( AgeBracket.Youth ) );
      
      LifecycleTesting.setAgeBracket( agent, AgeBracket.Infant );
      assertThat( agent.age().get(), is( 0 ) );
      assertThat( agent.getAgeBracket(), is( AgeBracket.Infant ) );
   }//End Method

}//End Class
