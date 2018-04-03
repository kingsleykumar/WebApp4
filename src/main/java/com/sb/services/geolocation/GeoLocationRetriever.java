package com.sb.services.geolocation;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import com.sb.domain.UserLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetAddress;
import java.util.Optional;

public class GeoLocationRetriever {

    protected Logger logger = LogManager.getLogger(this.getClass());


    public static void main(String[] args) {
        GeoLocationRetriever obj = new GeoLocationRetriever();
        UserLocation location = obj.getLocation("35.177.196.167");
        System.out.println(location);
    }

    private UserLocation getLocation(String ipAddress) {

//        String fileLocation = getClass().getResource("/GeoLiteCity.dat.gz").getFile();

        File file = new File("C:\\GeoLite2-City.mmdb");

        System.out.println("file = " + file.getAbsolutePath());

        Optional<UserLocation> userLocation = getLocation(ipAddress, file);

        return userLocation.get();
    }

    public Optional<UserLocation> getLocation(String ipAddressStr, File dbFile) {

        if (dbFile == null) {

            return Optional.empty();
        }

        UserLocation userLocation = null;

        try {

//            RandomAccessFile randomAccessFile = new RandomAccessFile(dbFile, "r");

            DatabaseReader reader = new DatabaseReader.Builder(dbFile).build();

            InetAddress ipAddress = InetAddress.getByName(ipAddressStr);

            CityResponse response = reader.city(ipAddress);

            Country country = response.getCountry();
//            System.out.println(country.getIsoCode());            // 'US'
            logger.info(country.getName());               // 'United States'

            Subdivision subdivision = response.getMostSpecificSubdivision();
            logger.info(subdivision.getName());    // 'Minnesota'
//            System.out.println(subdivision.getIsoCode()); // 'MN'

            City city = response.getCity();
            logger.info(city.getName()); // 'Minneapolis'
//
//            Postal postal = response.getPostal();
//            logger.info(postal.getCode()); // '55455'

            userLocation = new UserLocation(country.getName(), city.getName(), subdivision.getName());

        } catch (Throwable e) {
//            e.printStackTrace();
            logger.error("Exception : " + e.getMessage());
        }

        return Optional.ofNullable(userLocation);
    }
}