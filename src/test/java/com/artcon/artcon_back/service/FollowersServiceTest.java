package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.FollowersRepository;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FollowersServiceTest {
    @InjectMocks
    private FollowersService followersService;

    @Mock
    private FollowersRepository followersRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @Transactional
    void testFollowUser() {
        User follower = new User();
        follower.setId(1);
        follower.setFollowing_count(0);
        follower.setFollowers_count(0);

        User following = new User();
        following.setId(2);
        following.setFollowing_count(0);
        following.setFollowers_count(0);

        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(follower));
        when(userRepository.findById(2)).thenReturn(java.util.Optional.of(following));

        assertDoesNotThrow(() -> followersService.followUser(1, 2));
        assertEquals(follower.getFollowing_count(), 1);
        assertEquals(following.getFollowers_count(), 1);
    }

    @Test
    @Transactional
    void testUnfollowUser() {
        User follower = new User();
        follower.setId(1);
        follower.setFollowing_count(1);
        follower.setFollowers_count(1);

        User following = new User();
        following.setId(2);
        following.setFollowers_count(1);
        following.setFollowing_count(1);


        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(follower));
        when(userRepository.findById(2)).thenReturn(java.util.Optional.of(following));

        followersService.followUser(1, 2);
        assertDoesNotThrow(() -> followersService.unfollowUser(1, 2));

        verify(userRepository, times(4)).save(any(User.class));

        verify(followersRepository, times(1)).deleteByFollowerIdAndFollowingId(1, 2);
    }

    @Test
    void testCheckIfUserFollows_True() {
        when(followersRepository.existsByFollowerIdAndFollowingId(1, 2)).thenReturn(true);
        assertTrue(followersService.checkIfUserFollows(1, 2));
    }

    @Test
    void testCheckIfUserFollows_False() {
        when(followersRepository.existsByFollowerIdAndFollowingId(1, 2)).thenReturn(false);
        assertFalse(followersService.checkIfUserFollows(1, 2));
    }
}
