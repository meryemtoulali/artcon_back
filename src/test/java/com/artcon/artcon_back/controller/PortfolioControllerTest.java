package com.artcon.artcon_back.controller;
import com.artcon.artcon_back.model.PortfolioPost;
import com.artcon.artcon_back.model.PortfolioPostRequest;
import com.artcon.artcon_back.service.PortfolioPostService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioControllerTest {
    @Mock
    private PortfolioPostService portfolioPostService;

    @InjectMocks
    private PortfolioController portfolioController;

    private MockMvc mockMvc;

    @Test
    void createPortfolioPost_ValidRequest_ReturnsCreated() throws Exception {
        PortfolioPostRequest request = new PortfolioPostRequest();
        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/portfolio")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("portfolioPostRequest", request))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createPortfolioPost_EntityNotFoundException_ReturnsNotFound() throws Exception {
        PortfolioPostRequest request = new PortfolioPostRequest();
        doThrow(new EntityNotFoundException("User not found")).when(portfolioPostService).createPortfolioPost(request);

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/portfolio")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("portfolioPostRequest", request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getPortfolioPost_ValidId_ReturnsOk() throws Exception {
        PortfolioPost portfolioPost = new PortfolioPost();
        int portfolioPostId = 1;
        when(portfolioPostService.getPortfolioPost(portfolioPostId)).thenReturn(portfolioPost);

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/portfolio/{portfolioPostId}", portfolioPostId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPortfolioPost_EntityNotFoundException_ReturnsNotFound() throws Exception {
        int portfolioPostId = 1;
        doThrow(new EntityNotFoundException("Portfolio post not found"))
                .when(portfolioPostService).getPortfolioPost(portfolioPostId);

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/portfolio/{portfolioPostId}", portfolioPostId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updatePortfolioPost_ValidRequest_ReturnsOk() throws Exception {
        PortfolioPostRequest request = new PortfolioPostRequest();
        int portfolioPostId = 1;

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/portfolio/{portfolioPostId}", portfolioPostId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("updatePortfolioPostRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updatePortfolioPost_EntityNotFoundException_ReturnsNotFound() throws Exception {
        PortfolioPostRequest request = new PortfolioPostRequest();
        int portfolioPostId = 1;
        doThrow(new EntityNotFoundException("Portfolio post not found"))
                .when(portfolioPostService).updatePortfolioPost(portfolioPostId, request);

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/portfolio/{portfolioPostId}", portfolioPostId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("updatePortfolioPostRequest", request))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deletePortfolioPost_ValidId_ReturnsOk() throws Exception {
        int portfolioPostId = 1;

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/portfolio/{portfolioPostId}", portfolioPostId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }








}
