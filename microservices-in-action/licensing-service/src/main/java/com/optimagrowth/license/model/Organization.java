package com.optimagrowth.license.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Id;

@Getter @Setter
@RedisHash("organization") // 조직 데이터가 저장될 레디스 서버의 해시 이름 설정
public class Organization extends RepresentationModel<Organization> {

    @Id
    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

}
