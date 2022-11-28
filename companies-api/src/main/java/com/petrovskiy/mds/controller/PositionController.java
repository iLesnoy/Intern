package com.petrovskiy.mds.controller;

import com.petrovskiy.mds.service.PositionService;
import com.petrovskiy.mds.service.dto.PositionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionDto create(@RequestBody PositionDto positionDto) {
        log.info("@RequestBody createPosition: " + positionDto);
        return positionService.create(positionDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PositionDto update(@PathVariable BigInteger id, @RequestBody PositionDto positionDto){
        log.info("@RequestBody updatePosition: " + positionDto);
        return positionService.update(id,positionDto);
    }

    @GetMapping("/{id}")
    public PositionDto findById(@PathVariable BigInteger id) {
        log.info("findPositionById: " + id);
        return positionService.findById(id);
    }

    @GetMapping("/amount/{id}/{amount}")
    public PositionDto checkAmount(@PathVariable BigInteger id , @PathVariable String amount) {
        return positionService.findPositionById(id,new BigDecimal(amount));
    }

    @GetMapping
    public Page<PositionDto> findAll(Pageable pageable) {
        return positionService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable BigInteger id){
        log.info("Delete position by id: " + id);
        positionService.delete(id);
    }
}
