package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.util.DateTimeUtil;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

/**
 * MealTo.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 02.09.2018
 */
public class MealTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @NotBlank
    @Size(min = 2, max = 120, message = "length must between 5 and 32 characters")
    private String description;

    @NotNull
    @Range(min = 10, max = 5000)
    private Integer calories;

    public MealTo() {
    }

    public MealTo(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}