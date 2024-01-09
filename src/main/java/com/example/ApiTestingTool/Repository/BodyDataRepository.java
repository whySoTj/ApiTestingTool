package com.example.ApiTestingTool.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ApiTestingTool.Models.ApiBody;

public interface BodyDataRepository extends JpaRepository<ApiBody,Integer> {
    
}
