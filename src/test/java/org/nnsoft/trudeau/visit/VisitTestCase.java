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

import static org.junit.Assert.assertEquals;
import static org.nnsoft.trudeau.connector.GraphPopulator.populate;
import static org.nnsoft.trudeau.visit.GraphVisitor.visit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nnsoft.trudeau.api.Graph;
import org.nnsoft.trudeau.connector.AbstractGraphConnection;
import org.nnsoft.trudeau.inmemory.UndirectedMutableGraph;
import org.nnsoft.trudeau.inmemory.labeled.BaseLabeledEdge;
import org.nnsoft.trudeau.inmemory.labeled.BaseLabeledVertex;

public final class VisitTestCase
{

    @Test( expected = IllegalStateException.class )
    public void testNotExistVertex()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
                        new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        visit( input ).from( new BaseLabeledVertex( "NOT EXIST" ) );
    }

    /**
     * Graph picture can be see
     * <a href="http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/breadthSearch.htm">here</a>
     */
    @Test
    public void verifyBreadthFirstSearch()
    {
        // input graph

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
                        populate( new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>() )
                        .withConnections( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                BaseLabeledVertex r = addVertex( new BaseLabeledVertex( "r" ) );
                BaseLabeledVertex s = addVertex( new BaseLabeledVertex( "s" ) );
                BaseLabeledVertex t = addVertex( new BaseLabeledVertex( "t" ) );
                BaseLabeledVertex u = addVertex( new BaseLabeledVertex( "u" ) );
                BaseLabeledVertex v = addVertex( new BaseLabeledVertex( "v" ) );
                BaseLabeledVertex w = addVertex( new BaseLabeledVertex( "w" ) );
                BaseLabeledVertex x = addVertex( new BaseLabeledVertex( "x" ) );
                BaseLabeledVertex y = addVertex( new BaseLabeledVertex( "y" ) );

                addEdge( new BaseLabeledEdge( "s <-> r" ) ).from( s ).to( r );
                addEdge( new BaseLabeledEdge( "s <-> w" ) ).from( s ).to( w );

                addEdge( new BaseLabeledEdge( "r <-> v" ) ).from( r ).to( v );

                addEdge( new BaseLabeledEdge( "w <-> t" ) ).from( w ).to( t );
                addEdge( new BaseLabeledEdge( "w <-> x" ) ).from( w ).to( x );

                addEdge( new BaseLabeledEdge( "t <-> u" ) ).from( t ).to( u );
                addEdge( new BaseLabeledEdge( "t <-> x" ) ).from( t ).to( x );

                addEdge( new BaseLabeledEdge( "y <-> u" ) ).from( y ).to( u );
                addEdge( new BaseLabeledEdge( "x <-> y" ) ).from( x ).to( y );
            }

        } );

        // expected graph

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> expected =
                        populate( new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>() )
                        .withConnections( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                BaseLabeledVertex r = addVertex( new BaseLabeledVertex( "r" ) );
                BaseLabeledVertex s = addVertex( new BaseLabeledVertex( "s" ) );
                BaseLabeledVertex t = addVertex( new BaseLabeledVertex( "t" ) );
                BaseLabeledVertex u = addVertex( new BaseLabeledVertex( "u" ) );
                BaseLabeledVertex v = addVertex( new BaseLabeledVertex( "v" ) );
                BaseLabeledVertex w = addVertex( new BaseLabeledVertex( "w" ) );
                BaseLabeledVertex x = addVertex( new BaseLabeledVertex( "x" ) );
                BaseLabeledVertex y = addVertex( new BaseLabeledVertex( "y" ) );

                addEdge( new BaseLabeledEdge( "s <-> r" ) ).from( s ).to( r );
                addEdge( new BaseLabeledEdge( "s <-> w" ) ).from( s ).to( w );

                addEdge( new BaseLabeledEdge( "r <-> v" ) ).from( r ).to( v );

                addEdge( new BaseLabeledEdge( "w <-> t" ) ).from( w ).to( t );
                addEdge( new BaseLabeledEdge( "w <-> x" ) ).from( w ).to( x );

                addEdge( new BaseLabeledEdge( "t <-> u" ) ).from( t ).to( u );

                addEdge( new BaseLabeledEdge( "x <-> y" ) ).from( x ).to( y );
            }

        } );

        // actual graph

        Graph<BaseLabeledVertex, BaseLabeledEdge> actual = visit( input ).from( new BaseLabeledVertex( "s" ) ).applyingBreadthFirstSearch();

        // assertion

        assertEquals( expected, actual );
    }

    /**
     * Graph picture can be see
     * <a href="http://aiukswkelasgkelompok7.wordpress.com/metode-pencarian-dan-pelacakan/">here</a>
     */
    @Test
    public void verifyDepthFirstSearch()
    {
        // expected node set
        final List<BaseLabeledVertex> expected = new ArrayList<BaseLabeledVertex>();

        // input graph

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
                        populate( new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>() )
                        .withConnections( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                BaseLabeledVertex a = addVertex( new BaseLabeledVertex( "A" ) );
                BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );
                BaseLabeledVertex d = addVertex( new BaseLabeledVertex( "D" ) );
                BaseLabeledVertex e = addVertex( new BaseLabeledVertex( "E" ) );
                BaseLabeledVertex f = addVertex( new BaseLabeledVertex( "F" ) );
                BaseLabeledVertex g = addVertex( new BaseLabeledVertex( "G" ) );
                BaseLabeledVertex h = addVertex( new BaseLabeledVertex( "H" ) );
                BaseLabeledVertex s = addVertex( new BaseLabeledVertex( "S" ) );

                addEdge( new BaseLabeledEdge( "S <-> A" ) ).from( s ).to( a );
                addEdge( new BaseLabeledEdge( "S <-> B" ) ).from( s ).to( b );

                addEdge( new BaseLabeledEdge( "A <-> C" ) ).from( a ).to( c );
                addEdge( new BaseLabeledEdge( "A <-> D" ) ).from( a ).to( d );

                addEdge( new BaseLabeledEdge( "B <-> E" ) ).from( b ).to( e );
                addEdge( new BaseLabeledEdge( "B <-> F" ) ).from( b ).to( f );

                addEdge( new BaseLabeledEdge( "E <-> H" ) ).from( e ).to( h );
                addEdge( new BaseLabeledEdge( "E <-> G" ) ).from( e ).to( g );

                // populate the expected list, order is not the same in the pic, due to Stack use
                expected.add( s );
                expected.add( b );
                expected.add( f );
                expected.add( e );
                expected.add( g );
                expected.add( h );
                expected.add( a );
                expected.add( d );
                expected.add( c );
            }

        } );

        // actual node set

        final List<BaseLabeledVertex> actual =
            visit( input ).from( new BaseLabeledVertex( "S" ) ).applyingDepthFirstSearch( new NodeSequenceVisitor() );

        // assertion

        assertEquals( expected, actual );
    }

}
