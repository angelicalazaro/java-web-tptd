package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String saveSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarie = salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping("salaries/aide/new")
    public String addSalarie(final ModelMap model) {
        return "add_Salarie";
    }

    @GetMapping("/salaries")
    public String list(ModelMap model) {
        List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
        model.put("salaries", salaries);
        return "list";
    }

    @GetMapping("/salaries/{id}/delete")
    public String deleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }

}
