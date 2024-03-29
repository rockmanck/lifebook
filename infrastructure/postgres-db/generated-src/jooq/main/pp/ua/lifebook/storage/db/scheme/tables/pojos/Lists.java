/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Lists implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final Integer userId;
    private final String  name;
    private final Boolean deleted;

    public Lists(Lists value) {
        this.id = value.id;
        this.userId = value.userId;
        this.name = value.name;
        this.deleted = value.deleted;
    }

    public Lists(
        Integer id,
        Integer userId,
        String  name,
        Boolean deleted
    ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.deleted = deleted;
    }

    /**
     * Getter for <code>public.lists.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Getter for <code>public.lists.user_id</code>.
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * Getter for <code>public.lists.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for <code>public.lists.deleted</code>.
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Lists other = (Lists) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (deleted == null) {
            if (other.deleted != null)
                return false;
        }
        else if (!deleted.equals(other.deleted))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.deleted == null) ? 0 : this.deleted.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Lists (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(name);
        sb.append(", ").append(deleted);

        sb.append(")");
        return sb.toString();
    }
}
