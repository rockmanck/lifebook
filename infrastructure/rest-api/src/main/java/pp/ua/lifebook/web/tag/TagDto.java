package pp.ua.lifebook.web.tag;

public class TagDto {
    private Integer value;
    private String label;
    private boolean newTag;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isNewTag() {
        return newTag;
    }

    public void setNewTag(boolean newTag) {
        this.newTag = newTag;
    }
}
