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

import com.google.common.graph.Graph;

/**
 * A {@link GraphVisitHandler} controls the execution of breadth-first and depth-first search
 * algorithms in {@link Visit}.
 */
public interface GraphVisitHandler<N, G extends Graph<N>, O>
{

    /**
     * Called at the beginning of breadth-first and depth-first search.
     */
    void discoverGraph( G graph );

    /**
     * Performs operations on the input vertex and determines the behavior of the visit algorithm
     * based on the return value:
     * <ul>
     *   <li>{@link VisitState.CONTINUE} continues the visit normally;</li> 
     *   <li>{@link VisitState.SKIP} continues the visit skipping the input vertex;</li>
     *   <li>{@link VisitState.ABORT} terminates the visit.</li>
     * </ul>
     * @return the state of the visit after operations on the vertex
     */
    VisitState discoverNode( N node );

    /**
     * Performs operations on the input edge and determines the behavior of the visit algorithm
     * based on the return value:
     * <ul>
     *   <li>{@link VisitState.CONTINUE} continues the visit normally;</li> 
     *   <li>{@link VisitState.SKIP} continues the visit skipping the input edge;</li>
     *   <li>{@link VisitState.ABORT} terminates the visit.</li>
     * </ul>
     * @return the state of the visit after operations on the edge
     */
    VisitState discoverEdge( N head, N tail );

    /**
     * Checks if the search algorithm should be terminated. Called after the search algorithm has finished
     * visiting the input vertex.
     * @return {@link VisitState.ABORT} if the search algorithm should be terminated after visiting the input vertex, {@link VisitState.CONTINUE} otherwise
     */
    VisitState finishVertex( N node );

    /**
     * Called upon termination of the search algorithm.
     */
    void finishGraph( G graph );

    /**
     * Invoked once the visit is finished.
     *
     * @return Value that will be returned by the visit
     */
    O onCompleted();

}
