package com.ProjectGraduation.rating.ProfileRating.controller;

import com.ProjectGraduation.common.ApiResponse;
import com.ProjectGraduation.auth.service.JWTService;
import com.ProjectGraduation.auth.service.UserService;
import com.ProjectGraduation.rating.ProfileRating.dto.ProfileRatingDTO;
import com.ProjectGraduation.rating.ProfileRating.service.ProfileRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile-ratings")
@RequiredArgsConstructor
public class ProfileRatingController {

    private final ProfileRatingService profileRatingService;
    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping("/{ratedUserId}")
    public ResponseEntity<ApiResponse> rateUser(@RequestHeader("Authorization") String token,
                                                @PathVariable Long ratedUserId,
                                                @RequestParam int stars,
                                                @RequestParam(required = false) String comment) {
        try {
            String username = jwtService.getUsername(token.replace("Bearer ", ""));
            Long raterId = userService.getUserByUsername(username).getId();

            profileRatingService.rateUser(raterId, ratedUserId, stars, comment);

            return ResponseEntity.ok(new ApiResponse(true, "Rating submitted successfully!", null));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "Failed to submit rating: " + ex.getMessage(), null));
        }
    }

    @GetMapping("/average/{ratedUserId}")
    public ResponseEntity<ApiResponse> getAverageRating(@PathVariable Long ratedUserId) {
        try {
            double avgRating = profileRatingService.getAverageRating(ratedUserId);
            return ResponseEntity.ok(new ApiResponse(true, "Average rating fetched successfully!", avgRating));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "Failed to fetch average rating: " + ex.getMessage(), null));
        }
    }

    @GetMapping("/all/{ratedUserId}")
    public ResponseEntity<ApiResponse> getAllRatingsForUser(@PathVariable Long ratedUserId) {
        try {
            List<ProfileRatingDTO> ratings = profileRatingService.getAllRatings(ratedUserId);
            return ResponseEntity.ok(new ApiResponse(true, "All ratings fetched successfully!", ratings));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "Failed to fetch ratings: " + ex.getMessage(), null));
        }
    }
}
