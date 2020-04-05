package com.nbu.logistics.controllers;

import com.nbu.logistics.entities.Office;
import com.nbu.logistics.services.OfficesService;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OfficesController {
    @Autowired
    private OfficesService officesService;

    public OfficesController(OfficesService theOfficeService) {
        officesService = theOfficeService;
    }

    @RequestMapping("/offices")
    public String showOffices(Office office, Model model) {
        //get offices from db
        List<Office> theOffices = officesService.getAllOffices();
        model.addAttribute("offices", theOffices);
        return "offices";
    }

    @RequestMapping("/offices/{id}")
    public Office getOffice(@PathVariable("id") long id) {
        return officesService.getOffice(id);
    }

//    @GetMapping("/offices/create")
//    public String getCreate(Office office, Model model) {
//        Office theOffice = new Office();
//
//        model.addAttribute("office", theOffice);
//
//        return "create-office";
//    }
//    @PostMapping("/offices/create")
//    public String createOffice(@RequestParam("id") int id, Model model, Office office, BindingResult bindingResult) {  //dali e minalo bez problem mapvaneto na dannite samo kydeto imam html forma
//        Office theOffice = OfficesService.getOffice(id);
//
//        model.addAttribute("office", theOffice);
//        return "create-office";
//    }

    @GetMapping("/offices/create")
    public String getCreateOffice(Model model) {
        Office office = new Office();
        model.addAttribute("office", office);

        return "create-office";
    }

    @RequestMapping("offices/create")
    public String saveOffice(@ModelAttribute("office") Office office, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "offices";
        }

        officesService.save(office);
        return "redirect:/offices";
    }

//    @GetMapping("/offices/editOffice")
//    public String edit(@RequestParam("officeId") long id, Model model) {
//        //get office from service
//        Office office = officesService.getOffice(id);
//        //set office as a model att
//        model.addAttribute("office", office);
//        //send over to form
//        return "/offices";
//    }

        @GetMapping("/editOffice")
    public String update(@RequestParam("officeId") long id, Model model) {
        //get office from service
        Office office = officesService.getByIdOffice(id);
        //set office as a model att
        model.addAttribute("office", office);
        //send over to form
        return "create-office";
    }

//    @PostMapping("/edit/{id}")
//    public String edit(@PathVariable("id") long id, Model model) {
//
//        Office theOffice = officesService.getOffice(id);
//        model.addAttribute("office", theOffice);
//
//        return "edit-office";
//    }

//    @RequestMapping("/offices/edit/{id}")
//    public ModelAndView editOffice(@PathVariable("id") long id)  {
//        ModelAndView edit = new ModelAndView("office");
//
//        Office office = officesService.getOffice(id);
//        edit.addObject("office", office);
//
//        return edit;
//    }

//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public String delete(@PathVariable long id) {
//        officesService.deleteOffice(id);
//        return "redirect:/offices";
//    }


    @PostMapping("/offices/delete")
    public String delete(Office office, BindingResult bindingResult) {
        officesService.deleteOffice(office.getId());
        return "redirect:/offices";
    }
}