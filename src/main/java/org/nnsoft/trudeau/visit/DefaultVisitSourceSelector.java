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

import static org.nnsoft.trudeau.utils.Assertions.checkNotNull;
import static org.nnsoft.trudeau.utils.Assertions.checkState;

import org.nnsoft.trudeau.api.Graph;

/**
 * {@link VisitSourceSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
final class DefaultVisitSourceSelector<V, E, G extends Graph<V, E>>
    implements VisitSourceSelector<V, E, G>
{

    private final G graph;

    public DefaultVisitSourceSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public <S extends V> VisitAlgorithmsSelector<V, E, G> from( S source )
    {
        source = checkNotNull( source, "Impossible to visit input graph %s with null source", graph );
        checkState( graph.containsVertex( source ), "Vertex %s does not exist in the Graph", source );
        return new DefaultVisitAlgorithmsSelector<V, E, G>( graph, source );
    }

}
