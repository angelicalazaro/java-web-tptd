package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SalarieController {
    @Autowired

    private SalarieAideADomicileService salarieAideADomicileService;

    @ModelAttribute("countSalaries")
    public Long populateCountSalaries() {
        return salarieAideADomicileService.countSalaries();
    }

    @GetMapping("/salaries/{id}")
    public String detail(@PathVariable Long id, ModelMap model) {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        model.put("salarie", salarie);
        return "detail_Salarie";
    }

    @PostMapping("/salaries/save")
    public String saveSalarie(
            @ModelAttribute SalarieAideADomicile salarie,
            BindingResult bindingResult,
            ModelMap model
    ) throws SalarieException {
        if (bindingResult.hasErrors()) {
            return "detail_Salarie";
        }
        salarie = salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping("salaries/aide/new")
    public String addSalarie(final ModelMap model) {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        model.put("salarie", salarie);
        return "detail_Salarie";
    }

    @GetMapping("/salaries")
    public String list(@RequestParam (required = false) String nom, Pageable pageable, ModelMap model) {
        List<SalarieAideADomicile> salaries;
        if ((nom == null) || (nom.isEmpty())) {
            salaries = salarieAideADomicileService.getSalaries();
        } else {
            salaries = salarieAideADomicileService.getSalaries(nom, pageable);
        }
        model.put("salaries", salaries);
        model.put("recherche", nom);
        return "list";
    }
    @PostMapping("/salaries/{id}")
    public String updateSalarie(
            @PathVariable Long id,
            @ModelAttribute SalarieAideADomicile salarie,
            BindingResult bindingResult,
            ModelMap model
    ) throws SalarieException {
        if (bindingResult.hasErrors()) {
            return "detail_Salarie";
        }
        salarie.setId(id);
        salarie = salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping("/salaries/{id}/delete")
    public String deleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }

}
