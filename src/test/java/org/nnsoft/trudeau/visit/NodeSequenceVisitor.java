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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.graph.Graph;

public final class NodeSequenceVisitor
    extends BaseGraphVisitHandler<String, Graph<String>, List<String>>
{

    private final List<String> nodes = new ArrayList<String>();

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitState discoverNode( String vertex )
    {
        nodes.add( vertex );
        return VisitState.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> onCompleted()
    {
        return unmodifiableList( nodes );
    }

}
