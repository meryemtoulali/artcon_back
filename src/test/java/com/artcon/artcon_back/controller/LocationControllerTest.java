package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Location;
import com.artcon.artcon_back.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class LocationControllerTest {
    @Mock(lenient = true)
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationController locationController;

    @Test
    void getLocationsSuccessful() {
        var request = Location.builder()
                .id(1)
                .name("Rabat")
                .build();
        var request2 = Location.builder()
                .id(2)
                .name("Casablanca")
                .build();
        List<Location> mockLocations = List.of(request, request2);
        when(locationRepository.findAll()).thenReturn(mockLocations);

        var result = locationController.getLocations();

        assertEquals(2, result.getBody().size());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockLocations, result.getBody());
    }

    @Test
    void addLocationsSuccessful() {
        List<Location> mockLocations = Arrays.asList(new Location(1, "Rabat"), new Location(2, "Casablanca"));
        when(locationRepository.saveAll(Mockito.anyList())).thenReturn(mockLocations);

        // added this to solve the no current servletrequestattributes exception
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<String> responseEntity = locationController.addLocations(mockLocations);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void addLocationsEmptyList() {
        ResponseEntity<String> responseEntity = locationController.addLocations(Arrays.asList());

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Location list empty", responseEntity.getBody());
    }

    @Test
    void addLocationsEmptyName() {
        ResponseEntity<String> responseEntity = locationController.addLocations(Arrays.asList(new Location(1, "")));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Location name empty", responseEntity.getBody());
    }

    @Test
    void addLocationsDuplicateNames() {
        List<Location> mockLocations = Arrays.asList(new Location(1, "Rabat"), new Location(2, "Rabat"));
        when(locationRepository.saveAll(Mockito.anyList())).thenReturn(mockLocations);

        ResponseEntity<String> responseEntity = locationController.addLocations(mockLocations);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Duplicate city names", responseEntity.getBody());
    }
}