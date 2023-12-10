package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.PortfolioPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioPostRepository extends JpaRepository<PortfolioPost,Integer> {

}
