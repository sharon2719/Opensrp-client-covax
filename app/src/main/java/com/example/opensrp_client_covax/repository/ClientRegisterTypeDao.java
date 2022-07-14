package com.example.opensrp_client_covax.repository;

public interface ClientRegisterTypeDao {
    boolean removeAll(String baseEntityId);

    boolean add(String registerType, String baseEntityId);
}
