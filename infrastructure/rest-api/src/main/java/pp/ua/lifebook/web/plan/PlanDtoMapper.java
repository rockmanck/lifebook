package pp.ua.lifebook.web.plan;

import pp.ua.lifebook.plan.Plan;
import pp.ua.lifebook.web.tag.TagDtoMapper;

public class PlanDtoMapper {

    public static Plan toDomain(PlanDto plan, int userId) {
        Plan result = new Plan();
        result.setId(plan.getId());
        result.setCategory(plan.getCategory());
        result.setComments(plan.getComments());
        result.setOutdated(plan.isOutdated());
        result.setRepeated(plan.getRepeated());
        result.setStatus(plan.getStatus());
        result.setTitle(plan.getTitle());
        result.setDueDate(plan.getDueDate());
        result.setTags(TagDtoMapper.toDomain(plan.getTags(), userId));
        return result;
    }
}
