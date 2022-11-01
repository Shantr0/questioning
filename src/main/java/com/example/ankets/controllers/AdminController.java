package com.example.ankets.controllers;

import com.example.ankets.model.Form;
import com.example.ankets.model.Option;
import com.example.ankets.model.Question;
import com.example.ankets.model.UserForm;
import com.example.ankets.repositories.FormRepository;
import com.example.ankets.repositories.UserFormRepository;
import com.example.ankets.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private FormRepository formRepository;
    @Autowired
    private UserFormRepository userFormRepository;
    @Autowired
    private FormService formService;

    @GetMapping("/formedit")
    public String getEditForm(Model model) {
        model.addAttribute("form", new Form());
        return "form-edit";
    }

    @PostMapping("/formedit")
    public String postEditForm(@RequestParam String name, @RequestParam(name = "question[]") String[] questions, @RequestParam MultiValueMap<String, String> queryMap, Model model) {
        if (questions == null && questions.length == 0)
            return "redirect:/main";
        Form form = new Form();
        form.setName(name);
        List<Question> questionList = new ArrayList<>(questions.length);
        for (int i = 0; i < questions.length; i++) {
            Question q = new Question();
            String key = "option" + i + "[]";
            q.setQuestion(questions[i]);
            q.setMultiselect(queryMap.containsKey("multiselect" + i));// если в ключах есть multiselect[i] то это checkbox
            List<Option> options = formService.createOptions(queryMap.get(key));
            q.setOptions(options);
            //optionRepository.saveAll(options);
            questionList.add(q);
        }
        //questionRepository.saveAll(questionList);
        form.setQuestionOptions(questionList);
        model.addAttribute("form", form);
        formRepository.save(form);
        return "redirect:/main";
    }

    private boolean getMultiselectByIndex(Set elements, int i) {
        String val = "multiselect" + i;
        return elements.contains(val);
    }

    @PostMapping("/formedit/new")
    public String postEditQuestion(@RequestParam String question, @RequestParam(defaultValue = "false") boolean multiselect, @RequestParam(name = "option[]") String[] options, Model model) {
        List<Option> optionsList = new ArrayList<>(options.length);
        for (String option : options) {
            Option op = new Option();
            op.setText(option);
            optionsList.add(op);
        }
        Question res = new Question();
        res.setQuestion(question);
        res.setMultiselect(multiselect);
        res.setOptions(optionsList);
        model.addAttribute("question", res);
        return "redirect:/formedit";
    }

    @GetMapping("/useranswers")
    public String getUserForms(Model model) {
        List<UserForm> forms = userFormRepository.findAll();
        Map<String, List<UserForm>> formMap = new HashMap<>();
        for (UserForm form : forms) {
            String key = form.getUser().getLogin();
            if (formMap.containsKey(key)) {
                formMap.get(key).add(form);
            } else {
                List<UserForm> formList = new LinkedList<>();
                formList.add(form);
                formMap.put(key, formList);
            }
        }

        model.addAttribute("userForms", formMap);
        return "users-answers";
    }

    @GetMapping("/useranswer/{id}")
    public String getUserForm(@PathVariable(value = "id") long id, Model model) {
        Optional<UserForm> userForm = userFormRepository.findById(id);
        model.addAttribute("userForm", userForm.get());

        return "user-answer";
    }

    @PostMapping("/useranswer/{id}")
    public String postUserForm(Model model, @PathVariable String id) {
        return "redirect:/useranswers";
    }

}
