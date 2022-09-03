package com.ouath.example.clientcredentialsserver.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "oauth_refresh_token")
@NoArgsConstructor
@AllArgsConstructor
public class OauthRefreshToken {

    @Id
    @Column(name = "token_id")
    String tokenId;

    @Column(name = "token")
    Long token;

    @Lob
    @Column(name = "authentication")
    Long authentication;

}
