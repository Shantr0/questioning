package com.example.ankets.controllers;

import com.example.ankets.model.Form;
import com.example.ankets.model.User;
import com.example.ankets.model.UserForm;
import com.example.ankets.repositories.FormRepository;
import com.example.ankets.repositories.UserFormRepository;
import com.example.ankets.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class FormController {
    @Autowired
    private UserFormRepository userFormRepository;
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private FormService formService;

    @GetMapping("/forms")
    public String getForms(Model model) {
        List<Form> forms = formRepository.findAllById(formRepository.findAllIds());
        model.addAttribute("forms", forms);
        return "form-list";
    }


    @GetMapping("/form/{id}")
    public String getUserForm(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user, Model model) {
        if (!formRepository.existsById(id)) {
            return "redirect:/forms";
        }
        Optional<Form> template = formRepository.findById(id);
        UserForm userForm = formService.createUserFormFromForm(template.get(), user);
        model.addAttribute("userForm", userForm);
        return "user-form";
    }

    @PostMapping("/form/{id}")
    public String saveUserForm(@ModelAttribute("userForm") UserForm userForm, @AuthenticationPrincipal User user, @RequestParam MultiValueMap<String, String> queryMap, Model model) {
        userForm.setUser(user);
        formService.setRadioValues(userForm, queryMap);
        userFormRepository.save(userForm);
        //model.addAttribute("userForm", userForm);
        return "redirect:/forms";
    }

  /*  @PostMapping("/formedit")
    public String postEditForm2(@RequestParam String name,  Model model) {
        Form form = new Form();
        form.setName(name);
        List<Question> questions=(List<Question>) model.getAttribute("questions");
        if(questions==null)
            questions=new ArrayList<>();
        form.setQuestionOptions(questions);
        model.addAttribute("form", form);
        formRepository.save(form);
        return "redirect:/";
    }*/

    @GetMapping("/formedit/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String getEditQuestion(Model model) {
        return "question-edit";
    }


}
