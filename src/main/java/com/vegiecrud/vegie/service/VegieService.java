package com.vegiecrud.vegie.service;

import com.vegiecrud.vegie.dto.VegieDto;
import com.vegiecrud.vegie.exception.ResponseException;
import com.vegiecrud.vegie.model.Vegie;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VegieService {
    ResponseEntity<Vegie> createVegie(VegieDto vegie) throws ResponseException;

    List<Vegie> getAllVegie();

    boolean deleteVegie(String id);

    VegieDto updateVegie(String id, VegieDto vegieDto);

    VegieDto getVegieById(String id);
}
