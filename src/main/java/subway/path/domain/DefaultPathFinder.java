package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class DefaultPathFinder implements PathFinder {

    private WeightedGraph<Station, DefaultWeightedEdge> graph;

    public DefaultPathFinder(List<Station> stations, List<Section> sections) {
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (Station station : stations) {
            graph.addVertex(station);
        }
        for (Section section : sections) {
            DefaultWeightedEdge edge = graph.addEdge(section.getUpStation(), section.getDownStation());
            graph.setEdgeWeight(edge, section.getDistance());
        }
    }


    @Override
    public Path findShortestPath(Station start, Station end) {
        DijkstraShortestPath shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath graphPath = shortestPath.getPath(start, end);
        double weight = shortestPath.getPathWeight(start, end);
        return Path.of(graphPath.getVertexList(), Double.valueOf(weight).intValue());
    }
}
