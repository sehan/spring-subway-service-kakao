package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.station.domain.Station;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FavoriteDao {

    public static final String SAVE_BY_ID_QUERY =
            "insert into favorite " +
                    "(member_id, source_station_id, target_station_id) " +
                    "values (?, ?, ?)";

    public static final String FIND_BY_ID_QUERY =
            "select F.id as favorite_id, " +
                    "F.source_station_id as ss_id, SS.name as ss_name, " +
                    "F.target_station_id as ts_id, TS.name as ts_name " +
                    "from FAVORITE F \n " +
                    "left outer join STATION SS on F.source_station_id = SS.id " +
                    "left outer join STATION TS on F.target_station_id = TS.id " +
                    "where F.member_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long source, Long target, Long memberId) {
        jdbcTemplate.update(SAVE_BY_ID_QUERY, memberId, source, target);
    }

    public Favorite findById(Long id) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(FIND_BY_ID_QUERY, new Object[]{id});
        return result.stream()
                .map(it -> new Favorite(
                        (Long) it.get("favorite_id"),
                        new Station((Long) it.get("ss_id"), (String) it.get("ss_name")),
                        new Station((Long) it.get("ts_id"), (String) it.get("ts_name"))
                )).collect(Collectors.toList()).get(0);
    }

    public void deleteById(Long favoriteId, Long memberId) {
        String sql = "delete from FAVORITE where id = ? and member_id = ?";
        jdbcTemplate.update(sql, favoriteId, memberId);
    }
}
