package bonus;

import compulsory.Node;
import compulsory.Person;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import utilities.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class TestMaximally2ConnectedAlgorithm {
    Solution solution;

    @Test
    @DisplayName("This graph is biconnected")
    void testBiconnectivity()
    {
        Map<Node, List<Node>> graph = Utils.createExampleBiconnectedGraph();

        List<Node> listOfNodes = new LinkedList<>();

        for (Map.Entry<Node,List<Node>> entry : graph.entrySet()) {
            listOfNodes.add(entry.getKey());

        assertTrue( solution.isMaximally2Connected(listOfNodes));
    }
}
