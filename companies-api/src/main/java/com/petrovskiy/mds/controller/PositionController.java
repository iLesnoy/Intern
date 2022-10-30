package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.dto.CompanyDto;
import com.petrovskiy.mds.service.dto.PositionDto;
import com.petrovskiy.mds.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionDto create(@RequestBody PositionDto positionDto) {
        return positionService.create(positionDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionDto update(@PathVariable BigInteger id, @RequestBody PositionDto positionDto){
        return positionService.update(id,positionDto);
    }

    @GetMapping("/{id}")
    public PositionDto findById(@PathVariable BigInteger id) {
        return positionService.findById(id);
    }


    @GetMapping
    public Page<PositionDto> findAll(Pageable pageable) {
        return positionService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable BigInteger id){
        positionService.delete(id);
    }
}
