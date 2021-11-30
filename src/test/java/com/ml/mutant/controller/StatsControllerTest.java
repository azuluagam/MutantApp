package com.ml.mutant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.mutant.domain.Stats;
import com.ml.mutant.dto.StatsDto;
import com.ml.mutant.service.MutantService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class StatsControllerTest {

    public static final String STATS_PATH = "/stats";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private MutantService mutantService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new StatsController(mutantService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void getStats_shouldReturnStats() throws Exception {
        //Given
        Stats stats = new Stats();
        stats.setCountMutantDna(40);
        stats.setCountHumanDna(100);

        given(mutantService.getMutantStats()).willReturn(stats);

        //When
        MvcResult result = mockMvc.perform(get(STATS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        //Then
        String r = result.getResponse().getContentAsString();
        StatsDto statsDto = mapper.readValue(result.getResponse().getContentAsString(), StatsDto.class);

        assertEquals(stats.getCountHumanDna(), statsDto.getCountHumanDna());
        assertEquals(stats.getCountMutantDna(), statsDto.getCountMutantDna());
        assertEquals(stats.getRatio(), statsDto.getRatio());
        assertEquals(0.4D, statsDto.getRatio());
        verify(mutantService, atMost(1)).getMutantStats();
    }

}
