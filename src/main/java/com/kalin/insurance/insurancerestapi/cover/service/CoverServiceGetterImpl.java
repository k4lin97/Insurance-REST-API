package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service used to getting covers from database.
 */
@Service
public class CoverServiceGetterImpl implements CoverServiceGetter {
    private final CoverRepository coverRepository;

    @Autowired
    public CoverServiceGetterImpl(CoverRepository coverRepository) {
        this.coverRepository = coverRepository;
    }

    @Override
    public List<Cover> findAll() {
        return coverRepository.findAll();
    }

    @Override
    public Cover findById(Long id) {
        return getCoverById(id);
    }

    private Cover getCoverById(Long id) throws CoverNotFoundException {
        return coverRepository.findById(id).orElseThrow(() -> new CoverNotFoundException(id));
    }
}
