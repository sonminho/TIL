package com.optimagrowth.license.events.handler;

import com.optimagrowth.license.events.CustomChannels;
import com.optimagrowth.license.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@Slf4j
@EnableBinding(CustomChannels.class)
public class OrganizationChangeHandler {

    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel organization) {

        log.info("Received a message of type " + organization.getType());

        switch(organization.getAction()){
            case "GET":
                log.info("Received a GET event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "CREATED":
                log.info("신규 조직이 생성됨 OrganizationChangeModel: {}", organization.toString());
                break;
            case "UPDATE":
                log.info("Received a UPDATE event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "DELETE":
                log.info("Received a DELETE event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            default:
                log.error("Received an UNKNOWN event from the organization service of type {}", organization.getType());
                break;
        }
    }

}