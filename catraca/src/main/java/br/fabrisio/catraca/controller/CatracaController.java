package br.fabrisio.catraca.controller;

import br.fabrisio.catraca.message.RabbitMessage;
import br.fabrisio.catraca.population.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catraca")
public class CatracaController {

    @Autowired
    private PopulationService populationService;

    @GetMapping()
    public void isEmailOrUsernameInUse() {
        populationService.integration(RabbitMessage.builder().amount(0).build());
    }

}
