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
 * Search root node selector.
 *
 * @param <N> the Graph nodes type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
public interface VisitSourceSelector<N, G extends Graph<N>>
{

    /**
     * Select the root node the search begins from.
     *
     * @param source the root node the search begins from
     * @return the search visit algorithm selector
     */
    <S extends N> VisitAlgorithmsSelector<N, G> from( S source );

}
