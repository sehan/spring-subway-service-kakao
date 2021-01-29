package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimplePathFinder implements PathFinder {

    private WeightedGraph<Station, DefaultWeightedEdge> graph;

    public SimplePathFinder(List<Station> stations, List<Section> sections) {
        initGraph(stations, sections);
    }

    private void initGraph(List<Station> stations, List<Section> sections){
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (Station station : stations) {
            addStation(station);
        }
        for (Section section : sections) {
            addSection(section);
        }
    }

    @Override
    public void addStation(Station station){
        graph.addVertex(station);
    }

    @Override
    public void addSection(Section section){
        DefaultWeightedEdge edge = graph.addEdge(section.getUpStation(), section.getDownStation());
        if(Objects.nonNull(edge))
            graph.setEdgeWeight(edge, section.getDistance());
    }

    @Override
    public Path findShortestPath(Station start, Station end) {
        DijkstraShortestPath shortestPath = new DijkstraShortestPath<>(graph);
        GraphPath graphPath = shortestPath.getPath(start, end);
        double weight = shortestPath.getPathWeight(start, end);
        return Path.of(graphPath.getVertexList(), Double.valueOf(weight).intValue());
    }

    @Override
    public void refresh(List<Line> lines) {
        List<Station> allStations = lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .collect(Collectors.toList());
        List<Section> allSections = lines.stream()
                .flatMap(line -> line.getSections().getSections().stream())
                .distinct()
                .collect(Collectors.toList());
        initGraph(allStations, allSections);
    }
}
