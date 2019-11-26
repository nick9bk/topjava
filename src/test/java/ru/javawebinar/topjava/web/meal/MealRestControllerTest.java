package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealRestController mealRestController;

    @Test
    public void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> mealRestController.get(MEAL1_ID));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(contentJson(mealRestController.getAll()));
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "by?start=2015-05-30T10:00:00&end=2015-05-31T13:01:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(
                contentJson(
                        mealRestController.getBetween(LocalDate.of(2015, 05, 30),
                                LocalTime.of(10, 00), LocalDate.of(2015, 05, 31), LocalTime.of(13, 01))));
    }

    @Test
    void create() throws Exception {
        Meal newMeal = new Meal(MEAL1);
        newMeal.setId(null);
        newMeal.setDateTime(LocalDateTime.now());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Meal meal = readFromJson(resultActions, Meal.class);
        assertThat(newMeal).isEqualToIgnoringGivenFields(meal, "id");
    }

    @Test
    void update() throws Exception {
        Meal newMeal = new Meal(MEAL1);
        newMeal.setDateTime(LocalDateTime.now());
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isNoContent());
        Meal meal = mealRestController.get(newMeal.getId());
        assertMatch(newMeal, meal);
    }
}
