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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.graph.EndpointPair.ordered;
import static org.nnsoft.trudeau.visit.VisitState.ABORT;
import static org.nnsoft.trudeau.visit.VisitState.CONTINUE;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.google.common.graph.Graph;
import com.google.common.graph.EndpointPair;

/**
 * {@link VisitAlgorithmsSelector} implementation.
 *
 * @param <N> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
final class DefaultVisitAlgorithmsSelector<N, G extends Graph<N>>
    implements VisitAlgorithmsSelector<N, G>
{

    /** The graph. */
    private final G graph;

    /** The start vertex for the search. */
    private final N source;

    /**
     * Create a default {@link VisitAlgorithmsSelector} for the given {@link Graph} and start vertex.
     *
     * @param graph the {@link Graph} to be used.
     * @param source the start vertex.
     */
    public DefaultVisitAlgorithmsSelector( final G graph, final N source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    public Graph<N> applyingBreadthFirstSearch()
    {
        return applyingBreadthFirstSearch( new VisitGraphBuilder<N, G>() );
    }

    /**
     * {@inheritDoc}
     */
    public Graph<N> applyingDepthFirstSearch()
    {
        return applyingDepthFirstSearch( new VisitGraphBuilder<N, G>() );
    }

    /**
     * {@inheritDoc}
     */
    public <O> O applyingBreadthFirstSearch( GraphVisitHandler<N, G, O> handler )
    {
        return applyingSearch( handler, true );
    }

    /**
     * {@inheritDoc}
     */
    public <O> O applyingDepthFirstSearch( GraphVisitHandler<N, G, O> handler )
    {
        return applyingSearch( handler, false );
    }

    /**
     * A generalized graph search algorithm to be used to implement depth-first and breadth-first searches. Depending on
     * the used collection, the algorithm traverses the graph in a different way:
     * <ul>
     * <li>Queue (FIFO): breadth-first</li>
     * <li>Stack (LIFO): depth-first</li>
     * </ul>
     *
     * @param <O> the output handling type
     * @param handler the handler intercepts visits
     * @param enqueue defines the collection behavior used to traverse the graph: true is a Queue, false is a Stack
     * @return the result of {@link GraphVisitHandler#onCompleted()}
     */
    private <O> O applyingSearch( GraphVisitHandler<N, G, O> handler, boolean enqueue )
    {
        handler = checkNotNull( handler, "Graph visitor handler can not be null." );

        handler.discoverGraph( graph );

        final LinkedList<EndpointPair<N>> nodesList = new LinkedList<EndpointPair<N>>();

        nodesList.addLast( ordered( source, source ) );

        final Set<N> visitedNodes = new HashSet<N>();
        visitedNodes.add( source );

        boolean visitingGraph = true;

        while ( visitingGraph && !nodesList.isEmpty() )
        {
            // if dequeue, remove the first element, otherwise the last
            final EndpointPair<N> pair = enqueue ? nodesList.removeFirst() : nodesList.removeLast();
            final N v = pair.source();
            final N prevHead = pair.target();

            boolean skipVertex = false;

            if ( graph.hasEdgeConnecting( prevHead, v ) )
            {
                // if the vertex was already visited, do not discover
                // another edge leading to the same vertex
                if ( visitedNodes.contains( v ) )
                {
                    skipVertex = true;
                }
                else
                {
                    VisitState stateAfterEdgeDiscovery = handler.discoverEdge( prevHead, v );
                    if ( CONTINUE != stateAfterEdgeDiscovery )
                    {
                        skipVertex = true;
                        if ( ABORT == stateAfterEdgeDiscovery )
                        {
                            visitingGraph = false;
                        }
                    }
                }
            }

            // only mark the current vertex as visited, if the
            // edge leading to it should be expanded
            boolean vertexWasDiscovered = false;
            if ( !skipVertex )
            {
                visitedNodes.add( v );
                VisitState stateAfterVertexDiscovery = handler.discoverNode( v );
                vertexWasDiscovered = true;
                if ( CONTINUE != stateAfterVertexDiscovery )
                {
                    skipVertex = true;
                    if ( ABORT == stateAfterVertexDiscovery )
                    {
                        visitingGraph = false;
                    }
                }
            }

            if ( !skipVertex )
            {
                Iterator<N> connected = (graph.isDirected()
                                        ? graph.successors( v )
                                        : graph.adjacentNodes( v ) ).iterator();

                while ( connected.hasNext() )
                {
                    N w = connected.next();
                    if ( !visitedNodes.contains( w ) )
                    {
                        nodesList.addLast( ordered( w, v ) );
                    }
                }
            }

            if ( vertexWasDiscovered && ABORT == handler.finishVertex( v ) )
            {
                visitingGraph = false;
            }
        }

        handler.finishGraph( graph );

        return handler.onCompleted();
    }

}
