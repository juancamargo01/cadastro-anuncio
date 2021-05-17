package com.sistema.sistemadecadastro.controllers;

import com.sistema.sistemadecadastro.models.Anuncio;
import com.sistema.sistemadecadastro.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AnuncioController {

    @Autowired
    AnuncioService anuncioService;

    @GetMapping("/listaAnuncios")
    public ModelAndView listar() {

        ModelAndView modelAndView = new ModelAndView("listaAnuncio");
        modelAndView.addObject("anuncios",anuncioService.findAll());
        return modelAndView;

    }

    @GetMapping("/relatorioAnuncios")
    public ModelAndView relatorioForm() {

        ModelAndView modelAndView = new ModelAndView("relatorioAnuncio");
        modelAndView.addObject("anuncio", new Anuncio());

        return modelAndView;

    }

    @PostMapping("/relatorioAnuncios")
    public ModelAndView listaRelatorio(Anuncio anuncio) {

        ModelAndView modelAndView = new ModelAndView("relatorioAnuncio");
        modelAndView.addObject("anuncios", this.anuncioService.relatorioAnuncio(anuncio));

        return modelAndView;

    }

    @GetMapping("/cadastroAnuncio")
    public ModelAndView cadastroAnuncioForm(Anuncio anuncio) {

        ModelAndView modelAndView = new ModelAndView("cadastroAnuncio");
        modelAndView.addObject("anuncio",new Anuncio());

        return modelAndView;

    }

    @PostMapping("/cadastroAnuncio")
    public String salvar(@Valid Anuncio anuncio,  BindingResult result, RedirectAttributes attributes){


        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique se os campos obrigatŕoios (*) foram preenchidos");
            return "redirect:/cadastroAnuncio";

        }else if(anuncio.getDataFinal().isBefore(anuncio.getDataInicio())){
            attributes.addFlashAttribute("mensagem", "A data de dinalização não pode ser anterior a Data de inicio");
            return "redirect:/cadastroAnuncio";
        }
        this.anuncioService.save(anuncio);
        attributes.addFlashAttribute("mensagem","Cadastrado com sucesso");
        return "redirect:/listaAnuncios";
    }

}
