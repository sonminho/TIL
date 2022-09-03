package com.ouath.example.clientcredentialsserver.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "oauth_access_token")
@NoArgsConstructor
@AllArgsConstructor
public class OauthAccessToken {

    @Id
    @Column(name = "authentication_id")
    String authenticationId;

    @Column(name = "token_id")
    String tokenId;

    @Lob
    @Column(name = "token")
    String token;

    @Column(name = "user_name")
    String userName;

    @Column(name = "client_id")
    String clientId;

    @Lob
    @Column(name = "authentication")
    Long authentication;

    @Column(name = "refresh_token")
    String refreshToken;

}
