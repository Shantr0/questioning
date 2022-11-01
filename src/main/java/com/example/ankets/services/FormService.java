package com.example.ankets.services;

import com.example.ankets.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("formService")
public class FormService {

    public UserForm createUserFormFromForm(Form form, User user) {
        UserForm userForm = new UserForm();
        userForm.setUser(user);
        userForm.setName(form.getName());
        Set<Question> questionOptions = createUserAnswersFromForm(form);
        userForm.setQuestionOptions(new ArrayList<>(questionOptions));
        return userForm;
    }

    public Set<Question> createUserAnswersFromForm(Form form) {
        Set<Question> questionOptions = new HashSet<>(form.getQuestionOptions());
        Set<Question> answers = new HashSet<>(questionOptions.size());

        for (Question questionOption : questionOptions) {
            Question answer = new Question();
            answer.setMultiselect(questionOption.isMultiselect());
            answer.setQuestion(questionOption.getQuestion());

            Set<Option> options = new HashSet<>(questionOption.getOptions());
            answer.setOptions(new ArrayList<>(options));

            answers.add(answer);
        }
        return answers;
    }

    public List<Option> createOptions(Collection<String> answers) {
        List<Option> res = answers.stream().map(a -> {
            Option o = new Option();
            o.setText(a);
            return o;
        }).collect(Collectors.toList());
        return res;
    }

    public void setRadioValues(UserForm userForm, MultiValueMap<String, String> queryMap) {// проставлем нужные кфвшщ
        String key1, key2;
        for (int i = 0; i < userForm.getQuestionOptions().size(); i++) {
            key1 = String.format("option%s", i);
            key2 = String.format("questionOptions[%s].multiselect", i);
            if (queryMap.containsKey(key1) && queryMap.containsKey(key2)) {
                Collection opt = queryMap.get(key1);
                List checkbox = queryMap.get(key2);
                if (!Boolean.parseBoolean(checkbox.get(0).toString())) {// multiselect==false
                    int index = parseOptionIndex(opt.iterator().next().toString());
                    userForm.getQuestionOptions().get(i).getOptions().get(index).setChecked(true);
                }
            }
        }
    }

    protected int parseOptionIndex(String o) {
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(o);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String radioCheckIndex = o.substring(start, end);
            return Integer.parseInt(radioCheckIndex);
        }
        return -1;
    }
}
