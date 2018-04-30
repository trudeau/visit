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

import static org.junit.Assert.assertEquals;
import static org.nnsoft.trudeau.connector.GraphConnector.populate;
import static org.nnsoft.trudeau.visit.GraphVisitor.visit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nnsoft.trudeau.connector.AbstractMutableGraphConnection;
import org.nnsoft.trudeau.connector.AbstractMutableValueGraphConnection;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public final class VisitTestCase
{

    @Test( expected = IllegalStateException.class )
    public void testNotExistVertex()
    {
        Graph<String> input = GraphBuilder.undirected().build();

        visit( input ).from( "DOES NOT EXIST" );
    }

    /**
     * Graph picture can be see
     * <a href="http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/breadthSearch.htm">here</a>
     */
    @Test
    public void verifyBreadthFirstSearch()
    {
        // input graph

        MutableGraph<String> input = GraphBuilder.undirected().build();

        populate( input ).withConnections( new AbstractMutableGraphConnection<String>()
        {

            @Override
            public void connect()
            {
                String r = addNode( "r" );
                String s = addNode( "s" );
                String t = addNode( "t" );
                String u = addNode( "u" );
                String v = addNode( "v" );
                String w = addNode( "w" );
                String x = addNode( "x" );
                String y = addNode( "y" );

                connect( s ).to( r );
                connect( s ).to( w );

                connect( r ).to( v );

                connect( w ).to( t );
                connect( w ).to( x );

                connect( t ).to( u );
                connect( t ).to( x );

                connect( y ).to( u );
                connect( x ).to( y );
            }

        } );

        // expected graph

        MutableGraph<String> expected = GraphBuilder.undirected().build();

        populate( expected ).withConnections( new AbstractMutableGraphConnection<String>()
        {

            @Override
            public void connect()
            {
                String r = addNode( "r" );
                String s = addNode( "s" );
                String t = addNode( "t" );
                String u = addNode( "u" );
                String v = addNode( "v" );
                String w = addNode( "w" );
                String x = addNode( "x" );
                String y = addNode( "y" );

                connect( s ).to( r );
                connect( s ).to( w );

                connect( r ).to( v );

                connect( w ).to( t );
                connect( w ).to( x );

                connect( t ).to( u );

                connect( x ).to( y );
            }

        } );

        // actual graph

        Graph<String> actual = visit( input ).from( "s" ).applyingBreadthFirstSearch();

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
        final List<String> expected = new ArrayList<String>();

        // input graph

        MutableValueGraph<String, String> input = ValueGraphBuilder.undirected().build();
        populate( input ).withConnections( new AbstractMutableValueGraphConnection<String, String>()
        {

            @Override
            public void connect()
            {
                String a = addNode( "A" );
                String b = addNode( "B" );
                String c = addNode( "C" );
                String d = addNode( "D" );
                String e = addNode( "E" );
                String f = addNode( "F" );
                String g = addNode( "G" );
                String h = addNode( "H" );
                String s = addNode( "S" );

                putEdgeValue( "S <-> A" ).from( s ).to( a );
                putEdgeValue( "S <-> B" ).from( s ).to( b );

                putEdgeValue( "A <-> C" ).from( a ).to( c );
                putEdgeValue( "A <-> D" ).from( a ).to( d );

                putEdgeValue( "B <-> E" ).from( b ).to( e );
                putEdgeValue( "B <-> F" ).from( b ).to( f );

                putEdgeValue( "E <-> H" ).from( e ).to( h );
                putEdgeValue( "E <-> G" ).from( e ).to( g );

                // populate the expected list, order is not the same in the pic, due to Stack use
                expected.add( s );
                expected.add( a );
                expected.add( c );
                expected.add( d );
                expected.add( b );
                expected.add( f );
                expected.add( e );
                expected.add( g );
                expected.add( h );
            }

        } );

        // actual node set

        final List<String> actual =
            visit( input.asGraph() ).from( "S" ).applyingDepthFirstSearch( new NodeSequenceVisitor() );

        // assertion

        assertEquals( expected, actual );
    }

}
