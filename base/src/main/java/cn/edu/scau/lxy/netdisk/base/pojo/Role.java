package cn.edu.scau.lxy.netdisk.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    private String id;
    private String role;
    private int code;
}
