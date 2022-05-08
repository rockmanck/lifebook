package pp.ua.lifebook.moments;


import pp.ua.lifebook.utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class Moment {
    private Integer id;
    private LocalDate date;
    private String description;
    private int userId;

    public Moment() {}

    private Moment(Integer id, LocalDate date, String description, int userId) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDescriptionLines() {
        return Stream.of(description.split("\n"))
            .toList();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRawDate() {
        return date != null ? DateUtils.format(date) : "";
    }

    public static MomentBuilder builder() {
        return new MomentBuilder();
    }

    public static final class MomentBuilder {

        private Integer id;
        private LocalDate date;
        private String description;
        private int userId;

        private MomentBuilder() {
            // hide public constructor
        }

        public MomentBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public MomentBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public MomentBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MomentBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Moment createMoment() {
            return new Moment(id, date, description, userId);
        }
    }
}
