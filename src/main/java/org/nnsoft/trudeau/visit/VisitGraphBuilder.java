package org.nnsoft.trudeau.visit;

/*
 *   Copyright 2013 The Trudeau Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import org.nnsoft.trudeau.api.DirectedGraph;
import org.nnsoft.trudeau.api.Graph;
import org.nnsoft.trudeau.inmemory.BaseMutableGraph;
import org.nnsoft.trudeau.inmemory.DirectedMutableGraph;
import org.nnsoft.trudeau.inmemory.UndirectedMutableGraph;

/**
 * Internal Visitor helper that produces the search tree.
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class VisitGraphBuilder<V, E, G extends Graph<V, E>>
    extends BaseGraphVisitHandler<V, E, G, Graph<V, E>>
{

    private BaseMutableGraph<V, E> visitGraph;

    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverGraph( G graph )
    {
        if ( graph instanceof DirectedGraph )
        {
            visitGraph = new DirectedMutableGraph<V, E>();
        }
        else
        {
            visitGraph = new UndirectedMutableGraph<V, E>();
        }

        for ( V vertex : graph.getVertices() )
        {
            visitGraph.addVertex( vertex );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitState discoverEdge( V head, E edge, V tail )
    {
        visitGraph.addEdge( head, edge, tail );
        return VisitState.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V, E> onCompleted()
    {
        return visitGraph;
    }

}
