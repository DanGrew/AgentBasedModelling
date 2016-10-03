package uk.dangrew.abm.model.environment;

public class EnvironmentPositionTest extends ArbitraryPositionTest{

   @Override protected ArbitraryPosition constructSut() {
      return new EnvironmentPosition( V, H );
   }//End Method
   
}//End Class
