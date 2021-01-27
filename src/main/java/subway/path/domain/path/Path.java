package subway.path.domain.path;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exceptions.NotExistException;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class Path {
    private final List<Station> stations;
    private final int distance;
    private final int extraFare;

    public Path(Station sourceStation, Station destStation, List<Line> lines) {
        GraphPath<Station, CustomWeightedEdge> graph = getGraph(sourceStation, destStation, lines);
        validate(graph);
        this.stations = graph.getVertexList();
        this.distance = (int) graph.getWeight();
        this.extraFare = graph.getEdgeList()
                .stream()
                .map(CustomWeightedEdge::getExtraFare)
                .reduce((x, y) -> Math.max(x, y))
                .get();
    }

    private GraphPath<Station, CustomWeightedEdge> getGraph(Station sourceStation, Station destStation, List<Line> lines) {
        WeightedMultigraph<Station, CustomWeightedEdge> graph = new WeightedMultigraph(CustomWeightedEdge.class);
        for (Line line : lines) {
            addVerticesAndEdge(graph, line);
        }
        return new DijkstraShortestPath(graph).getPath(sourceStation, destStation);
    }

    private void validate(GraphPath<Station, CustomWeightedEdge> graph) {
        if(graph == null) {
            throw new NotExistException("경로가 존재하지 않습니다.");
        }
    }

    private void addVerticesAndEdge(WeightedMultigraph<Station, CustomWeightedEdge> graph, Line line) {
        for(Section section : line.getSections().getSections()) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();
            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.addEdge(upStation, downStation, new CustomWeightedEdge(section.getDistance(), line.getExtraFare()));
        }
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getExtraFare() {
        return extraFare;
    }
}