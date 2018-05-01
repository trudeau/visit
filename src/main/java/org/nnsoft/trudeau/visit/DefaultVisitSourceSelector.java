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

import static java.util.Objects.requireNonNull;

import com.google.common.graph.Graph;

/**
 * {@link VisitSourceSelector} implementation.
 *
 * @param <N> the Graph nodes type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
final class DefaultVisitSourceSelector<N, G extends Graph<N>>
    implements VisitSourceSelector<N, G>
{

    private final G graph;

    public DefaultVisitSourceSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public <S extends N> VisitAlgorithmsSelector<N, G> from( S source )
    {
        source = requireNonNull( source, "Impossible to visit input graph with null source" );
        if ( !graph.nodes().contains( source ) )
        {
            throw new IllegalArgumentException( "Input node does not exist in the Graph" );
        }
        return new DefaultVisitAlgorithmsSelector<N, G>( graph, source );
    }

}
