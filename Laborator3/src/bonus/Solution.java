package bonus;

import compulsory.Node;
import compulsory.Person;
import homework.Network;
import utilities.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static utilities.Utils.*;

public class Solution {
    Network network;

    public Solution(Network network, TypeOfSolutions typeOfsolution) {
        this.network = network;
        switch (typeOfsolution) {
            case FIND_ARTICULATION_POINTS -> {
                printArticulationPoints(findArticulationPoints(this.getNetwork(),this.getNetwork().getListOfEntities()));
            }
            case FIND_2CONNECTED_COMPONENTS -> print2ConnectedComponents(find2ConnectedComponents(this.getNetwork()));
            case FIND_MAXIMALLY2CONNECTED_COMPONENTS ->
                    print2ConnectedComponents(findMaximally2ConnectedComponents(this));
        }
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }


}


/*
Ce inteleg prin maximally 2 connected ?
sa fie 1-connected sau 2connected, dar nu mai mult de atat

Notiunea 2:
"A graph is said to be maximally connected if its connectivity equals its minimum degree"

In cazul nostru : un graf e maximally 2 connnected daca e 2connected si gradul minim al unui nod este 2
 */