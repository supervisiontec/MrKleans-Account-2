/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.common;

import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 'Kasun Chamara'
 */
public class SearchCodeGenarator {
    @Autowired
    private BranchRepository branchRepository;
    
    public String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }
}
