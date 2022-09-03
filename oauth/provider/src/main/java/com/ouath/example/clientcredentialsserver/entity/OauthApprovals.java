package com.ouath.example.clientcredentialsserver.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "oauth_approvals")
@NoArgsConstructor
@AllArgsConstructor
public class OauthApprovals {

    @Id
    @Column(name = "userId")
    String userId;

    @Column(name = "clientId")
    String clientId;

    @Column(name = "scope")
    String scope;

    @Column(name = "status")
    String status;

    @Column(name = "expiresAt")
    LocalDateTime expiresAt;

    @Column(name = "lastModifiedAt")
    LocalDateTime lastModifiedAt;

}
