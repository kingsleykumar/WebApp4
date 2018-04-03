package com.sb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 * Created by Kingsley Kumar on 22/03/2018 at 15:07.
 */
@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserLocation {

    private String countryName;
    private String city;
    private String region;

    public Document toDocument() {

        return new Document("countryName", (countryName == null ? "" : countryName))
                .append("city", (city == null ? "" : city))
                .append("region", (region == null ? "" : region));
    }
}
