package com.optimagrowth.license.service.client;

import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.OrganizationRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class OrganizationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrganizationRedisRepository redisRepository;

    public Organization getOrganization(String organizationId) {
        Organization organization = checkRedisCache(organizationId);

        if (organization != null) {
            log.info("레디스 캐시히트 {}, {}", organizationId, organization);
            return organization;
        }

        log.info("레디스 캐시미스 {}", organizationId);
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organization-service/v1/organization/{organization}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId
        );

        organization = restExchange.getBody();
        if (organization != null) {
            cacheOrganizationObject(organization);
        }

        return organization;
    }

    private Organization checkRedisCache(String organizationId) {
        try {
            return redisRepository.findById(organizationId)
                    .orElse(null);
        } catch (Exception e) {
            log.error("레디스 검색 에러 {}", e.getMessage());
            return null;
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            redisRepository.save(organization);
        } catch (Exception e) {
            log.error("레디스 갱신 에러 {}", e.getMessage());
        }
    }

}
