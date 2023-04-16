package org.example.compulsory.bonus;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.stream.IntStream;

public class Exploration {
  private ExplorationGraph explorationGraph;

  private List<Robot> listOfSlaves;

  public Exploration(int numberOfSlaves){
      this.explorationGraph = ExplorationGraph.getInstance();;
      this.listOfSlaves = IntStream.range(0, numberOfSlaves)
              .mapToObj(i-> new Robot("Robot" + i, explorationGraph))
              .toList();
      this.start();
  }

  public void start(){
      for(Robot robot : listOfSlaves){
          Thread thread = new Thread(robot);
          thread.start();
          System.out.println("threadul pt robotul " + robot.getName() + " a inceput");
      }
  }
}
