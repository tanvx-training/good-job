package com.goodjob.common.util;

import com.goodjob.common.exception.ServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * Utility class for making REST API calls.
 * Provides methods for common REST operations.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestClientUtils {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    /**
     * Makes a GET request to the specified URL.
     *
     * @param url the URL to make the request to
     * @param responseType the type of the response
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T get(String url, Class<T> responseType) {
        return get(url, responseType, null, null);
    }

    /**
     * Makes a GET request to the specified URL with query parameters.
     *
     * @param url the URL to make the request to
     * @param responseType the type of the response
     * @param queryParams the query parameters
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T get(String url, Class<T> responseType, Map<String, String> queryParams) {
        return get(url, responseType, queryParams, null);
    }

    /**
     * Makes a GET request to the specified URL with query parameters and headers.
     *
     * @param url the URL to make the request to
     * @param responseType the type of the response
     * @param queryParams the query parameters
     * @param headers the headers
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T get(String url, Class<T> responseType, Map<String, String> queryParams, Map<String, String> headers) {
        try {
            String fullUrl = buildUrlWithQueryParams(url, queryParams);
            HttpHeaders httpHeaders = buildHeaders(headers);
            HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
            
            log.debug("Making GET request to {}", fullUrl);
            ResponseEntity<T> response = REST_TEMPLATE.exchange(fullUrl, HttpMethod.GET, requestEntity, responseType);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error making GET request to {}: {}", url, e.getMessage());
            throw new ServiceException("Error making GET request: " + e.getMessage(), e);
        }
    }

    /**
     * Makes a POST request to the specified URL with the specified body.
     *
     * @param url the URL to make the request to
     * @param body the request body
     * @param responseType the type of the response
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T post(String url, Object body, Class<T> responseType) {
        return post(url, body, responseType, null);
    }

    /**
     * Makes a POST request to the specified URL with the specified body and headers.
     *
     * @param url the URL to make the request to
     * @param body the request body
     * @param responseType the type of the response
     * @param headers the headers
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T post(String url, Object body, Class<T> responseType, Map<String, String> headers) {
        try {
            HttpHeaders httpHeaders = buildHeaders(headers);
            HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
            
            log.debug("Making POST request to {}", url);
            ResponseEntity<T> response = REST_TEMPLATE.exchange(url, HttpMethod.POST, requestEntity, responseType);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error making POST request to {}: {}", url, e.getMessage());
            throw new ServiceException("Error making POST request: " + e.getMessage(), e);
        }
    }

    /**
     * Makes a PUT request to the specified URL with the specified body.
     *
     * @param url the URL to make the request to
     * @param body the request body
     * @param responseType the type of the response
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T put(String url, Object body, Class<T> responseType) {
        return put(url, body, responseType, null);
    }

    /**
     * Makes a PUT request to the specified URL with the specified body and headers.
     *
     * @param url the URL to make the request to
     * @param body the request body
     * @param responseType the type of the response
     * @param headers the headers
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T put(String url, Object body, Class<T> responseType, Map<String, String> headers) {
        try {
            HttpHeaders httpHeaders = buildHeaders(headers);
            HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
            
            log.debug("Making PUT request to {}", url);
            ResponseEntity<T> response = REST_TEMPLATE.exchange(url, HttpMethod.PUT, requestEntity, responseType);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error making PUT request to {}: {}", url, e.getMessage());
            throw new ServiceException("Error making PUT request: " + e.getMessage(), e);
        }
    }

    /**
     * Makes a DELETE request to the specified URL.
     *
     * @param url the URL to make the request to
     * @param responseType the type of the response
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T delete(String url, Class<T> responseType) {
        return delete(url, responseType, null);
    }

    /**
     * Makes a DELETE request to the specified URL with headers.
     *
     * @param url the URL to make the request to
     * @param responseType the type of the response
     * @param headers the headers
     * @param <T> the type of the response
     * @return the response
     */
    public static <T> T delete(String url, Class<T> responseType, Map<String, String> headers) {
        try {
            HttpHeaders httpHeaders = buildHeaders(headers);
            HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
            
            log.debug("Making DELETE request to {}", url);
            ResponseEntity<T> response = REST_TEMPLATE.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error making DELETE request to {}: {}", url, e.getMessage());
            throw new ServiceException("Error making DELETE request: " + e.getMessage(), e);
        }
    }

    /**
     * Builds a URL with query parameters.
     *
     * @param url the base URL
     * @param queryParams the query parameters
     * @return the URL with query parameters
     */
    private static String buildUrlWithQueryParams(String url, Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return url;
        }
        
        StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");
        
        queryParams.forEach((key, value) -> {
            urlBuilder.append(key).append("=").append(value).append("&");
        });
        
        // Remove the trailing "&"
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        
        return urlBuilder.toString();
    }

    /**
     * Builds HTTP headers.
     *
     * @param headers the headers
     * @return the HTTP headers
     */
    private static HttpHeaders buildHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        if (headers != null && !headers.isEmpty()) {
            headers.forEach(httpHeaders::set);
        }
        
        return httpHeaders;
    }
} 