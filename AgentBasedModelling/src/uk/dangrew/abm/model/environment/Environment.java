package uk.dangrew.abm.model.environment;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import uk.dangrew.abm.model.agent.Agent;

/**
 * The {@link Environment} represents a basic grid system that {@link Agent}s can move in.
 */
public class Environment {

   private final int width;
   private final int height;
   private final ObservableMap< EnvironmentPosition, EnvironmentElement > environment;
   private final UnmodifiableObservableMap< EnvironmentPosition, EnvironmentElement > unmodifiableEnvironment;
   private final ObservableMap< EnvironmentPosition, Agent > agents;
   private final UnmodifiableObservableMap< EnvironmentPosition, Agent > unmodifiableAgents;
   
   /**
    * Constructs a new {@link Environment}.
    * @param width the width of the grid.
    * @param height the height of the grid.
    */
   public Environment( int width, int height ) {
      if ( width <= 0 || height <= 0 ) {
         throw new IllegalArgumentException( "Must provide positive dimensions." );
      }
      
      this.width = width;
      this.height = height;
      this.environment = FXCollections.observableHashMap();
      this.unmodifiableEnvironment = new UnmodifiableObservableMap<>( environment );
      for ( int vertical = 0; vertical < height; vertical++ ) {
         for ( int horizontal = 0; horizontal < width; horizontal++ ) {
            place( new EnvironmentPosition( vertical, horizontal ), EnvironmentElement.Space );
         }
      }
      
      this.agents = FXCollections.observableHashMap();
      this.unmodifiableAgents = new UnmodifiableObservableMap<>( agents );
   }//End Constructor
   
   /**
    * Access to the width of the {@link Environment}.
    * @return the width.
    */
   public int width() {
      return width;
   }//End Method

   /**
    * Access to the height of the {@link Environment}.
    * @return the height.
    */
   public int height() {
      return height;
   }//End Method

   /**
    * Method to apply a horizontal boundary from the given {@link EnvironmentPosition} with the given number
    * of steps east, where negative would move west.
    * @param boundaryStartLocation the {@link EnvironmentPosition} to start the boundary at.
    * @param stepsEast the number of steps east, or west if negative.
    */
   public void applyHorizontalBoundary( EnvironmentPosition boundaryStartLocation, int stepsEast ) {
      final int startY = boundaryStartLocation.horizontal();
      if ( stepsEast > 0 ) {
         for ( int i = startY; i < startY + Math.abs( stepsEast ); i++ ) {
            place( new EnvironmentPosition( boundaryStartLocation.vertical(), i ), EnvironmentElement.Boundary );
         }
      } else {
         for ( int i = startY; i > startY - Math.abs( stepsEast ); i-- ) {
            place( new EnvironmentPosition( boundaryStartLocation.vertical(), i ), EnvironmentElement.Boundary );
         }
      }
   }//End Method
   
   /**
    * Method to apply a vertical boundary from the given {@link EnvironmentPosition} with the given number
    * of steps south, where negative would move north.
    * @param boundaryStartLocation the {@link EnvironmentPosition} to start the boundary at.
    * @param stepsSouth the number of steps south, or north if negative.
    */
   public void applyVerticalBoundary( EnvironmentPosition boundaryStartLocation, int stepsSouth ) {
      final int startX = boundaryStartLocation.vertical();
      if ( stepsSouth > 0 ) {
         for ( int i = startX; i < startX + Math.abs( stepsSouth ); i++ ) {
            place( new EnvironmentPosition( i, boundaryStartLocation.horizontal() ), EnvironmentElement.Boundary );
         }
      } else {
         for ( int i = startX; i > startX - Math.abs( stepsSouth ); i-- ) {
            place( new EnvironmentPosition( i, boundaryStartLocation.horizontal() ), EnvironmentElement.Boundary );
         }
      }
   }//End Method
   
   /**
    * Method to place an {@link EnvironmentElement} at the given {@link EnvironmentPosition}. 
    * @param position the {@link EnvironmentPosition} to position at.
    * @param element the {@link EnvironmentElement} at that position.
    */
   private void place( EnvironmentPosition position, EnvironmentElement element ) {
      if ( !withinBounds( position ) ) {
         throw new ArrayIndexOutOfBoundsException();
      }
      environment.put( position, element );
   }//End Method
   
   /**
    * Method to access the {@link EnvironmentElement} at the given {@link EnvironmentPosition}.
    * @param position the {@link EnvironmentPosition}in question.
    * @return the {@link EnvironmentElement} at that position.
    */
   private EnvironmentElement element( EnvironmentPosition position ) {
      if ( !withinBounds( position ) ) {
         return EnvironmentElement.Boundary;
      }
      
      return environment.get( position );
   }//End Method
   
   /**
    * Method to determine whether the {@link EnvironmentPosition} is within the bounds of 
    * the {@link Environment}.
    * @param position the {@link EnvironmentPosition} to validate.
    * @return true if within the bounds.
    */
   private boolean withinBounds( EnvironmentPosition position ) {
      int vertical = position.vertical();
      int horizontal = position.horizontal();
      
      if ( 
               vertical < 0       || horizontal < 0 ||
               vertical >= height || horizontal >= width 
      ) {
         return false;
      }
      
      return true;
   }//End Method
   
   /**
    * Method to determine whether the given {@link EnvironmentPosition} is a {@link EnvironmentElement#Boundary}.
    * @param position the {@link EnvironmentPosition} in question.
    * @return true if a {@link EnvironmentElement#Boundary}.
    */
   public boolean isBoundary( EnvironmentPosition position ) {
      return element( position ) == EnvironmentElement.Boundary;
   }//End Method
   
   /**
    * Method to determine whether the given {@link EnvironmentPosition} is a {@link EnvironmentElement#Space}.
    * @param position the {@link EnvironmentPosition} in question.
    * @return true if a {@link EnvironmentElement#Space}.
    */
   public boolean isSpace( EnvironmentPosition position ) {
      return element( position ) == EnvironmentElement.Space;
   }//End Method
   
   /**
    * Method to determine whether the given {@link EnvironmentPosition} is available.
    * @param environmentPosition the {@link EnvironmentPosition} in question.
    * @return true if empty and can be occupied.
    */
   public boolean isAvailable( EnvironmentPosition environmentPosition ) {
      if ( isBoundary( environmentPosition ) ) {
         return false;
      }
      
      if ( agents.containsKey( environmentPosition ) ) {
         return false;
      }
      
      return true;
   }//End Method

   /**
    * Unmodifiable access to the grid.
    * @return the map.
    */
   public ObservableMap< EnvironmentPosition, EnvironmentElement > grid() {
      return unmodifiableEnvironment;
   }//End Method
   
   /**
    * Unmodifiable access to the {@link Agent}s.
    * @return the map.
    */
   public ObservableMap< EnvironmentPosition, Agent > agents() {
      return unmodifiableAgents;
   }//End Method
   
   /**
    * Method to instruct the {@link Environment} to track the {@link Agent} and its position.
    * @param agent the {@link Agent} in question.
    */
   public void monitorAgent( Agent agent ) {
      if ( agents.containsValue( agent ) ) {
         return;
      }
      agent.position().addListener( ( s, o, u ) -> {
         agents.remove( o );
         agents.put( u, agent );
      } );
      agents.put( agent.position().get(), agent );
   }//End Method

}//End Class
