package subway.favorite.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.application.FavoriteService;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FavoriteController {
    private FavoriteService favoriteService;
    private StationService stationService;

    public FavoriteController(FavoriteService favoriteService, StationService stationService) {
        this.favoriteService = favoriteService;
        this.stationService = stationService;
    }

    @PostMapping("/favorites")
    public ResponseEntity<Favorite> createFavorite(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteService.createFavorite(loginMember.getId(), favoriteRequest.getSource(), favoriteRequest.getTarget());
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).body(favorite);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        List<Favorite> favorites = favoriteService.findAllFavorites(loginMember.getId());
        List<FavoriteResponse> favoriteResponses = favorites.stream()
                .map(favorite -> new FavoriteResponse(favorite.getId(),
                        StationResponse.of(stationService.findStationById(favorite.getSourceStationId())),
                        StationResponse.of(stationService.findStationById(favorite.getTargetStationId()))
                        ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(favoriteResponses);
    }

    @DeleteMapping("/favorites/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(loginMember.getId(), favoriteId);
        return ResponseEntity.noContent().build();
    }
}
