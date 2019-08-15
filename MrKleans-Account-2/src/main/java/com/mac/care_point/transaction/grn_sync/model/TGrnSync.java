/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.grn_sync.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "t_int_grn")
public class TGrnSync implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer index_no;

    @Column(name = "loc_id")
    private String loc_id;

    @Column(name = "grn_no")
    private String grn_no;

    @Column(name = "ref_no")
    private String ref_no;

    @Column(name = "cate_id")
    private String cate_id;

    @Column(name = "supp_code")
    private String supp_code;

    @Column(name = "grn_date")
    private String grn_date;

    @Column(name = "staff_code")
    private String staff_code;

    @Column(name = "remar")
    private String remar;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "sys_tyme")
    private String sys_tyme;

    @Column(name = "grn_total")
    private double grn_total;

    @Column(name = "grn_stat")
    private int grn_stat;

    @Column(name = "tot_tax")
    private double tot_tax;

    @Column(name = "tot_dis")
    private double tot_dis;

    @Column(name = "trn_yes")
    private int trn_yes;

    @Column(name = "ext_dis")
    private double ext_dis;

    @Column(name = "gross_tot")
    private double gross_tot;

    @Column(name = "tax_two")
    private double tax_two;

    @Column(name = "dis_after_tax")
    private String dis_after_tax;

    @Column(name = "hide_stat")
    private int hide_stat;

    @Column(name = "is_get_data")
    private int is_get_data;

    @Column(name = "is_update_data")
    private int is_update_data;

    @Column(name = "is_sync_data")
    private int is_sync_data;

    public TGrnSync() {
    }

    public TGrnSync(Integer index_no, String loc_id, String grn_no, String ref_no, String cate_id, String supp_code, String grn_date, String staff_code, String remar, String user_id, String sys_tyme, double grn_total, int grn_stat, double tot_tax, double tot_dis, int trn_yes, double ext_dis, double gross_tot, double tax_two, String dis_after_tax, int hide_stat, int is_get_data, int is_update_data, int is_sync_data) {
        this.index_no = index_no;
        this.loc_id = loc_id;
        this.grn_no = grn_no;
        this.ref_no = ref_no;
        this.cate_id = cate_id;
        this.supp_code = supp_code;
        this.grn_date = grn_date;
        this.staff_code = staff_code;
        this.remar = remar;
        this.user_id = user_id;
        this.sys_tyme = sys_tyme;
        this.grn_total = grn_total;
        this.grn_stat = grn_stat;
        this.tot_tax = tot_tax;
        this.tot_dis = tot_dis;
        this.trn_yes = trn_yes;
        this.ext_dis = ext_dis;
        this.gross_tot = gross_tot;
        this.tax_two = tax_two;
        this.dis_after_tax = dis_after_tax;
        this.hide_stat = hide_stat;
        this.is_get_data = is_get_data;
        this.is_update_data = is_update_data;
        this.is_sync_data = is_sync_data;
    }

    public Integer getIndex_no() {
        return index_no;
    }

    public void setIndex_no(Integer index_no) {
        this.index_no = index_no;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String getGrn_no() {
        return grn_no;
    }

    public void setGrn_no(String grn_no) {
        this.grn_no = grn_no;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getSupp_code() {
        return supp_code;
    }

    public void setSupp_code(String supp_code) {
        this.supp_code = supp_code;
    }

    public String getGrn_date() {
        return grn_date;
    }

    public void setGrn_date(String grn_date) {
        this.grn_date = grn_date;
    }

    public String getStaff_code() {
        return staff_code;
    }

    public void setStaff_code(String staff_code) {
        this.staff_code = staff_code;
    }

    public String getRemar() {
        return remar;
    }

    public void setRemar(String remar) {
        this.remar = remar;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSys_tyme() {
        return sys_tyme;
    }

    public void setSys_tyme(String sys_tyme) {
        this.sys_tyme = sys_tyme;
    }

    public double getGrn_total() {
        return grn_total;
    }

    public void setGrn_total(double grn_total) {
        this.grn_total = grn_total;
    }

    public int getGrn_stat() {
        return grn_stat;
    }

    public void setGrn_stat(int grn_stat) {
        this.grn_stat = grn_stat;
    }

    public double getTot_tax() {
        return tot_tax;
    }

    public void setTot_tax(double tot_tax) {
        this.tot_tax = tot_tax;
    }

    public double getTot_dis() {
        return tot_dis;
    }

    public void setTot_dis(double tot_dis) {
        this.tot_dis = tot_dis;
    }

    public int getTrn_yes() {
        return trn_yes;
    }

    public void setTrn_yes(int trn_yes) {
        this.trn_yes = trn_yes;
    }

    public double getExt_dis() {
        return ext_dis;
    }

    public void setExt_dis(double ext_dis) {
        this.ext_dis = ext_dis;
    }

    public double getGross_tot() {
        return gross_tot;
    }

    public void setGross_tot(double gross_tot) {
        this.gross_tot = gross_tot;
    }

    public double getTax_two() {
        return tax_two;
    }

    public void setTax_two(double tax_two) {
        this.tax_two = tax_two;
    }

    public String getDis_after_tax() {
        return dis_after_tax;
    }

    public void setDis_after_tax(String dis_after_tax) {
        this.dis_after_tax = dis_after_tax;
    }

    public int getHide_stat() {
        return hide_stat;
    }

    public void setHide_stat(int hide_stat) {
        this.hide_stat = hide_stat;
    }

    public int getIs_get_data() {
        return is_get_data;
    }

    public void setIs_get_data(int is_get_data) {
        this.is_get_data = is_get_data;
    }

    public int getIs_update_data() {
        return is_update_data;
    }

    public void setIs_update_data(int is_update_data) {
        this.is_update_data = is_update_data;
    }

    public int getIs_sync_data() {
        return is_sync_data;
    }

    public void setIs_sync_data(int is_sync_data) {
        this.is_sync_data = is_sync_data;
    }

}
