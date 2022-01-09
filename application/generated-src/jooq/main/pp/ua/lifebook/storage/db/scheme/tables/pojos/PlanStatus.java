/*
 * This file is generated by jOOQ.
 */
package pp.ua.lifebook.storage.db.scheme.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PlanStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final String name;

    public PlanStatus(PlanStatus value) {
        this.code = value.code;
        this.name = value.name;
    }

    public PlanStatus(
        String code,
        String name
    ) {
        this.code = code;
        this.name = name;
    }

    /**
     * Getter for <code>public.plan_status.code</code>.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Getter for <code>public.plan_status.name</code>.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PlanStatus (");

        sb.append(code);
        sb.append(", ").append(name);

        sb.append(")");
        return sb.toString();
    }
}