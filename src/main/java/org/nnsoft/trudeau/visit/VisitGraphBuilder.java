package org.nnsoft.trudeau.visit;

/*
 *   Copyright 2013 - 2018 The Trudeau Project
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

import com.google.common.graph.MutableGraph;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;

/**
 * Internal Visitor helper that produces the search tree.
 *
 * @param <N> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class VisitGraphBuilder<N, G extends Graph<N>>
    extends BaseGraphVisitHandler<N, G, Graph<N>>
{

    private MutableGraph<N> visitGraph;

    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverGraph( G graph )
    {
        if ( graph.isDirected() )
        {
            visitGraph = GraphBuilder.directed().build();
        }
        else
        {
            visitGraph = GraphBuilder.undirected().build();
        }

        for ( N node : graph.nodes() )
        {
            visitGraph.addNode( node );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitState discoverEdge( N head, N tail )
    {
        visitGraph.putEdge( head, tail );
        return VisitState.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<N> onCompleted()
    {
        return visitGraph;
    }

}
