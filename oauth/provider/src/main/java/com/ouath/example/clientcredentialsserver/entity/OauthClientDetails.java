package com.ouath.example.clientcredentialsserver.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_client_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OauthClientDetails {

    @Id
    @Column(name = "client_id")
    String clientId;

    @Column(name = "resource_ids")
    String resourceIds;

    @Column(name = "client_secret")
    String clientSecret;

    @Column(name = "scope")
    String scope;

    @Column(name = "authorized_grant_types")
    String authorizedGrantTypes;

    @Column(name = "web_server_redirect_uri")
    String webServerRedirectUri;

    @Column(name = "authorities")
    String authorities;

    @Column(name = "access_token_validity")
    Integer accessTokenValidity;

    @Column(name = "refresh_token_validity")
    Integer refreshTokenValidity;

    @Column(name = "additional_information")
    String additionalInformation;

    @Column(name = "autoapprove")
    String autoapprove;

}
