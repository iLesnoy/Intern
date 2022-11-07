package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.impl.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.logging.Logger;

@RestController
@Slf4j
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    private Logger logger;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionDto create(@RequestBody PositionDto positionDto) {
        logger.info("@RequestBody createPosition: " + positionDto);
        return positionService.create(positionDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionDto update(@PathVariable BigInteger id, @RequestBody PositionDto positionDto){
        logger.info("@RequestBody updatePosition: " + positionDto);
        return positionService.update(id,positionDto);
    }

    @GetMapping("/{id}")
    public PositionDto findById(@PathVariable BigInteger id) {
        logger.info("findPositionById: " + id);
        return positionService.findById(id);
    }


    @GetMapping
    public Page<PositionDto> findAll(Pageable pageable) {
        return positionService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable BigInteger id){
        logger.info("Delete position by id: " + id);
        positionService.delete(id);
    }
}
