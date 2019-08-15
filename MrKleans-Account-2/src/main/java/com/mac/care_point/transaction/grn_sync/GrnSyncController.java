/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.grn_sync;

import com.mac.care_point.transaction.grn_sync.model.TGrnParam;
import com.mac.care_point.transaction.grn_sync.model.TGrnSync;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/grn-sync")
public class GrnSyncController {

    @Autowired
    private GrnSyncService grnSyncService;

    @RequestMapping(value = "/get-data", method = RequestMethod.POST)
    public List<Object[]> getDetail(@RequestBody TGrnParam param) {
        return grnSyncService.getDetail(param);
    }

    @RequestMapping(value = "/save-data", method = RequestMethod.POST)
    public int saveDetail(@RequestBody Object[] param) {
        try {
            return grnSyncService.save(param);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
