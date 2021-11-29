package com.ml.mutant.controller;

import com.ml.mutant.dto.DnaDto;
import com.ml.mutant.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutant")
public class MutantController {

    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> isMutant(@RequestBody
                                                       DnaDto dnaChain) {
        return (mutantService.isMutant(dnaChain.getDna())) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
