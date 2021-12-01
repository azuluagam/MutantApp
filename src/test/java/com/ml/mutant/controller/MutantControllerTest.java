package com.ml.mutant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.mutant.dto.DnaDto;
import com.ml.mutant.service.MutantService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class MutantControllerTest {

    public static final String MUTANT_PATH = "/mutant";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private MutantService mutantService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new MutantController(mutantService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void isMutant_shouldReturn200StatusWhenTrue() throws Exception {
        //Given
        String[] dnaChainArray = {"ATGCGA","CAGTGC","TTATGT", "AGAATG","CCCCTA","TCACTG"};
        DnaDto dnaDto = new DnaDto();
        dnaDto.setDna(dnaChainArray);

        given(mutantService.isMutant(dnaChainArray)).willReturn(true);

        //When
        MvcResult result = mockMvc.perform(post(MUTANT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dnaDto))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        //Then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        verify(mutantService, times(1)).isMutant(dnaChainArray);
    }

    @Test
    public void isMutant_shouldReturn403StatusWhenFalse() throws Exception {
        //Given
        String[] dnaChainArray = {"ATGCGA","CAGTGC","TTATGT", "AGAATG","GGCCTA","TCACTG"};
        DnaDto dnaDto = new DnaDto();
        dnaDto.setDna(dnaChainArray);

        given(mutantService.isMutant(dnaChainArray)).willReturn(false);

        //When
        MvcResult result = mockMvc.perform(post(MUTANT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dnaDto))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        //Then
        assertEquals(HttpStatus.FORBIDDEN.value(), result.getResponse().getStatus());
        verify(mutantService, times(1)).isMutant(dnaChainArray);
    }

}
