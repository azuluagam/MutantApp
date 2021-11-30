package com.ml.mutant.controller;

import com.ml.mutant.dto.StatsDto;
import com.ml.mutant.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stats")
public class StatsController {

    private final MutantService mutantService;

    @Autowired
    public StatsController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @GetMapping
    public ResponseEntity<StatsDto> getStats() {
        StatsDto statsDto = new StatsDto();
        //mutantService.getMutantStats()
        return new ResponseEntity<>(statsDto, HttpStatus.OK);
    }

}
