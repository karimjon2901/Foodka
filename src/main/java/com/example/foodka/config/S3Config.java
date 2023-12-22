package com.example.foodka.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${accessKey}")
    private String access_key;

    @Value("${secret}")
    private String secret_key;


    @Value("${region}")
    private String region;
    @Bean
    public AmazonS3 s3(){
        AWSCredentials credentials = new BasicAWSCredentials(access_key,secret_key);
        return AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}