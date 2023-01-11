package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterAll;
import com.kalin.insurance.insurancerestapi.model.service.ServiceGetterById;

/**
 * Defines methods needed to get cover from database.
 */
public interface CoverServiceGetter extends ServiceGetterAll<Cover>, ServiceGetterById<Cover> {
}
