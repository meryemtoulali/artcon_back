package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.config.JwtService;
import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.repository.InterestRepository;
import com.artcon.artcon_back.repository.PostRepository;
import com.artcon.artcon_back.repository.UserRepository;
import com.artcon.artcon_back.token.Token;
import com.artcon.artcon_back.token.TokenRepository;
import com.artcon.artcon_back.token.TokenType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final TokenRepository tokenRepository;
    private final FileStorageService fileStorageService;
    private final PostRepository postRepository;

    //Select user by
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //Select user by ID
    public User findUserById(Integer id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    public void updateUser(Integer userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Update user fields based on updateUserRequest
        if (updateUserRequest.getBio() != null) {
            user.setBio(updateUserRequest.getBio());
        }

        if (updateUserRequest.getFirstname() != null) {
            user.setFirstname(updateUserRequest.getFirstname());
        }

        if (updateUserRequest.getLastname() != null) {
            user.setLastname(updateUserRequest.getLastname());
        }

        if (updateUserRequest.getLocation() != null) {
            user.setLocation(updateUserRequest.getLocation());
        }

        if (updateUserRequest.getGender() != null) {
            user.setGender(updateUserRequest.getGender());
        }

        if (updateUserRequest.getPhone_number() != null) {
            user.setPhone_number(updateUserRequest.getPhone_number());
        }

        if (updateUserRequest.getTitle() != null) {
            user.setTitle(updateUserRequest.getTitle());
        }

        if (updateUserRequest.getType() != null) {
            user.setType(updateUserRequest.getType());
        }

        if (updateUserRequest.getUsername() != null) {
            user.setUsername(updateUserRequest.getUsername());
        }

        if (updateUserRequest.getPicture() != null) {
            // Save the file and get the file URL
            try {
                String fileUrl = fileStorageService.saveFile(updateUserRequest.getPicture());
                user.setPicture(fileUrl);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (updateUserRequest.getBanner() != null) {
            try {
                String fileUrl = fileStorageService.saveFile(updateUserRequest.getBanner());
                user.setBanner(fileUrl);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (updateUserRequest.getBirthday() != null) {
            user.setBirthday(updateUserRequest.getBirthday());
        }
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
    }

    public LoginResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .location(request.getLocation())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .phone_number(request.getPhonenumber())
                .followers_count(0)
                .following_count(0)
                .password_hash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        savedUserToken(savedUser, jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .userId(user.getId())
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        savedUserToken(user, jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .userId(user.getId())
                .build();
    }

    private void savedUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public List<User> getUserFollowers(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Followers> followersList = user.getFollowing();
//        return followersList;
        return extractUserListFromFollowingList(followersList);
    }

    public List<User> getUserFollowing(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Followers> followingList = user.getFollowers();
        return extractUserListFromFollowersList(followingList);
//        return followingList;
    }

    private List<User> extractUserListFromFollowingList(List<Followers> followersList) {
        List<User> userList = followersList.stream()
                .map(Followers::getFollower)
                .collect(Collectors.toList());

        return userList;
    }

    private List<User> extractUserListFromFollowersList(List<Followers> followersList) {
        List<User> userList = followersList.stream()
                .map(Followers::getFollowing)
                .collect(Collectors.toList());

        return userList;
    }




    public void uploadProfilePicture(Integer userId, MultipartFile file) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // Save the file and get the file URL
        String fileUrl;
        try {
            fileUrl = fileStorageService.saveFile(file);
            user.setPicture(fileUrl);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<PortfolioPost> getPortfolioPosts(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getPortfolioPosts();
    }

    @Transactional
    public void selectInterest(Integer userId,List<Long> interests){
        User user = userRepository.findUserById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        List<Interest> interestList = new ArrayList<>();
        for (Long id : interests) {
            // get the interest by id
            Interest interest = interestRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Interest not found"));

            //Add user to interest
            interest.getInterested().add(user);
            interestRepository.save(interest);

            // Add interest to list
            interestList.add(interest);
        }

        user.setInterestList(interestList);
        // Save changes to user
        userRepository.save(user);
    }

    //Get home filtered by interest
    public List<Post> getHomeFeed(Integer userId){
        return postRepository.findPostsByUserInterestList(userId);
    }

    public List<User> searchUsers(String query, String title, String type, String location){
        List<User> users = userRepository.searchUser(query,title,type,location);
        return users;
    }
    public UserRepository getUserRepository() {
        return userRepository;
    }

}
