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
import static com.google.common.base.Preconditions.checkState;

import com.google.common.graph.Graph;

/**
 * {@link VisitSourceSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
final class DefaultVisitSourceSelector<V, G extends Graph<V>>
    implements VisitSourceSelector<V, G>
{

    private final G graph;

    public DefaultVisitSourceSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public <S extends V> VisitAlgorithmsSelector<V, G> from( S source )
    {
        source = checkNotNull( source, "Impossible to visit input graph %s with null source", graph );
        checkState( graph.nodes().contains( source ), "Vertex %s does not exist in the Graph", source );
        return new DefaultVisitAlgorithmsSelector<V, G>( graph, source );
    }

}
