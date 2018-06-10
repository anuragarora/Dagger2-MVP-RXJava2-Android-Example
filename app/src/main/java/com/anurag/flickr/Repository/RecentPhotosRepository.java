package com.anurag.flickr.Repository;

/**
 * Repository for tutorial related operations.
 */
public interface RecentPhotosRepository {
    String getRecentPhotosLastResponse();

    void saveRecentPhotosLastResponse(String value);

    void clearAll();
}
