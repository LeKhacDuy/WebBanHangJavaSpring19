package midtermjava.application.controller;
import org.springframework.ui.Model;

import midtermjava.application.entity.Shoes;
import midtermjava.application.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class ShoesController {
    @Autowired
    private ShoesService shoesService;
    @GetMapping(value = "/", produces = "text/html")
    public String getShoes( Model model){

        Iterable<Shoes> shoes = shoesService.listAllShoes();
        model.addAttribute("shoes", shoes);
        return "index";
    }
    @GetMapping(value = "/checkout", produces = "text/html")
    public String getCheckout(){
        return "checkout";
    }
    @GetMapping(value = "/info", produces = "text/html")
    public String getInfo(Model model, @RequestParam("id") String id){
        Shoes shoes = shoesService.getShoesById(Long.parseLong(id));
        model.addAttribute("shoes", shoes);
        return "info";
    }
    @GetMapping(value = "/search")
    @ResponseBody
    public Iterable<Shoes> getSearchShoes(@RequestParam("search") String search){
        return shoesService.getSearchShoes(search);
    }

    @GetMapping(value = "/filter")
    @ResponseBody
    public Iterable<Shoes> getFilterShoes(@RequestParam HashMap<String , String> params){
        return shoesService.getFilterShoes(params);
    }

    @GetMapping(value = "/history", produces = "text/html")
    public String getHistory(){
        return "history";
    }



}

