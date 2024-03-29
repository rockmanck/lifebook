/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TagMomentRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer tagId;
    private final Integer momentId;

    public TagMomentRelation(TagMomentRelation value) {
        this.tagId = value.tagId;
        this.momentId = value.momentId;
    }

    public TagMomentRelation(
        Integer tagId,
        Integer momentId
    ) {
        this.tagId = tagId;
        this.momentId = momentId;
    }

    /**
     * Getter for <code>public.tag_moment_relation.tag_id</code>.
     */
    public Integer getTagId() {
        return this.tagId;
    }

    /**
     * Getter for <code>public.tag_moment_relation.moment_id</code>.
     */
    public Integer getMomentId() {
        return this.momentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final TagMomentRelation other = (TagMomentRelation) obj;
        if (tagId == null) {
            if (other.tagId != null)
                return false;
        }
        else if (!tagId.equals(other.tagId))
            return false;
        if (momentId == null) {
            if (other.momentId != null)
                return false;
        }
        else if (!momentId.equals(other.momentId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.tagId == null) ? 0 : this.tagId.hashCode());
        result = prime * result + ((this.momentId == null) ? 0 : this.momentId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TagMomentRelation (");

        sb.append(tagId);
        sb.append(", ").append(momentId);

        sb.append(")");
        return sb.toString();
    }
}
