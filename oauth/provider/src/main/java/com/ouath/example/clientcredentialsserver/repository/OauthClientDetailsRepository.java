package com.ouath.example.clientcredentialsserver.repository;


import com.ouath.example.clientcredentialsserver.entity.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, String> {
}
