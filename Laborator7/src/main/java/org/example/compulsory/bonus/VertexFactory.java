package org.example.compulsory.bonus;

import java.util.function.Supplier;

public class VertexFactory implements Supplier<Vertex> {
    private static int nextVertexID = 1;
    @Override
    public Vertex get() {
        return new Vertex(nextVertexID++);
    }
}
