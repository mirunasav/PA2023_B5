package bonus;

import compulsory.Node;
import compulsory.Person;
import homework.Network;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import utilities.Utils;

import java.security.KeyPair;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static utilities.Utils.isMaximally2Connected;

public class TestMaximally2ConnectedAlgorithm {
    @Test
    @DisplayName("This graph is biconnected")
    public void testBiconnectivity() {
       Network testNetwork = Utils.createExampleBiconnectedGraph();

        List<Node> listOfNodes = new LinkedList<>();

            assertTrue(isMaximally2Connected(testNetwork,testNetwork.getListOfEntities()));
        }
    }
