package subway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {
    private StationDao stationDao;
    private LineDao lineDao;
    private SectionDao sectionDao;
    private MemberDao memberDao;

    public DataLoader(StationDao stationDao, LineDao lineDao, SectionDao sectionDao, MemberDao memberDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.memberDao = memberDao;
    }

    @Override
    public void run(String... args) throws Exception {
        Station 강남역 = stationDao.insert(new Station("강남역"));
        Station 판교역 = stationDao.insert(new Station("판교역"));
        Station 정자역 = stationDao.insert(new Station("정자역"));
        Station 역삼역 = stationDao.insert(new Station("역삼역"));
        Station 잠실역 = stationDao.insert(new Station("잠실역"));
        Station 서현역 = stationDao.insert(new Station("서헌역"));
        Station 이매역 = stationDao.insert(new Station("이매역"));

        Line 신분당선 = lineDao.insert(new Line("신분당선", "red lighten-1")).get();
        신분당선.addSection(new Section(강남역, 판교역, 신분당선.getId(), 10));
        신분당선.addSection(new Section(판교역, 정자역, 신분당선.getId(), 10));
        sectionDao.insertSections(신분당선);

        Line 이호선 = lineDao.insert(new Line("2호선", "green lighten-1")).get();
        이호선.addSection(new Section(강남역, 역삼역, 이호선.getId(), 10));
        이호선.addSection(new Section(역삼역, 잠실역, 이호선.getId(), 10));
        sectionDao.insertSections(이호선);

        Line 분당선 = lineDao.insert(new Line("분당선", "green lighten-1")).get();
        분당선.addSection(new Section(서현역, 이매역, 분당선.getId(), 10));
        sectionDao.insertSections(분당선);


        Member member = new Member("email@email.com", "password", 10);
        memberDao.insert(member);
    }
}

