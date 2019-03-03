package by.alexei.afanasyeu.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Cat {
    @NotNull(message = "котики не должны быть безымянными!")
    private String name;
    @NotNull
    @Pattern(regexp = "black|white|black & white|red|red & white|red & black & white",
            message = "permitted colors: black, white, black & white, red, red & white, red & black & white")
    private String color;
    @Min(value = 0, message = "tail length cannot be negative")
    @JsonProperty(value = "tail_length")
    private Integer tailLength;
    @Min(value = 0, message = "whiskers length cannot be negative")
    @JsonProperty(value = "whiskers_length")
    private Integer whiskersLength;

    public Cat() {
    }

    public Cat(String name, String color, Integer tailLength, Integer whiskersLength) {
        this.name = name;
        this.color = color;
        this.tailLength = tailLength;
        this.whiskersLength = whiskersLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getTailLength() {
        return tailLength;
    }

    public void setTailLength(Integer tailLength) {
        this.tailLength = tailLength;
    }

    public Integer getWhiskersLength() {
        return whiskersLength;
    }

    public void setWhiskersLength(Integer whiskersLength) {
        this.whiskersLength = whiskersLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cat cat = (Cat) o;

        if (name != null ? !name.equals(cat.name) : cat.name != null) return false;
        if (color != null ? !color.equals(cat.color) : cat.color != null) return false;
        if (tailLength != null ? !tailLength.equals(cat.tailLength) : cat.tailLength != null) return false;
        return whiskersLength != null ? whiskersLength.equals(cat.whiskersLength) : cat.whiskersLength == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (tailLength != null ? tailLength.hashCode() : 0);
        result = 31 * result + (whiskersLength != null ? whiskersLength.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", tailLength=" + tailLength +
                ", whiskersLength=" + whiskersLength +
                '}';
    }
}
