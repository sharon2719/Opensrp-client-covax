package com.example.opensrp_client_covax.holders;

import org.smartregister.commonregistry.CommonRepository;
import org.smartregister.immunization.repository.VaccineRepository;

public class RepositoryHolder {
    private CommonRepository commonRepository;
    private VaccineRepository vaccineRepository;

    public CommonRepository getCommonRepository() {
        return commonRepository;
    }

    public void setCommonRepository(CommonRepository commonRepository) {
        this.commonRepository = commonRepository;
    }

    public VaccineRepository getVaccineRepository() {
        return vaccineRepository;
    }

    public void setVaccineRepository(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }
}
