package cl.ucn.disc.dsm.pictwin.backend;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sqlite_master", schema = "main", catalog = "")
public class SqliteMasterEntity {
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "tbl_name")
    private String tblName;
    @Basic
    @Column(name = "rootpage")
    private Object rootpage;
    @Basic
    @Column(name = "sql")
    private String sql;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTblName() {
        return tblName;
    }

    public void setTblName(String tblName) {
        this.tblName = tblName;
    }

    public Object getRootpage() {
        return rootpage;
    }

    public void setRootpage(Object rootpage) {
        this.rootpage = rootpage;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqliteMasterEntity that = (SqliteMasterEntity) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tblName != null ? !tblName.equals(that.tblName) : that.tblName != null) return false;
        if (rootpage != null ? !rootpage.equals(that.rootpage) : that.rootpage != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tblName != null ? tblName.hashCode() : 0);
        result = 31 * result + (rootpage != null ? rootpage.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        return result;
    }
}
