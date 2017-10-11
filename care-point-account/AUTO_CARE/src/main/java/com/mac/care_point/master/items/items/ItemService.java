/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.items.items;

import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.master.items.item_unit.ItemUnitRepository;
import com.mac.care_point.master.items.item_unit.model.MItemUnits;
import com.mac.care_point.master.items.items.model.MItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kavish Manjitha
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemUnitRepository itemUnitRepository;

    @Autowired
    private BranchRepository branchRepository;

  

    public List<MItem> findAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(Integer indexNo) {
        try {
            List<MItemUnits> findItemList = itemUnitRepository.findByItemAndItemUnitType(indexNo, "MAIN");
            itemUnitRepository.delete(findItemList.get(0));
            itemRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("cannot delete this item because there are details in other transaction");
        }
    }

    public List<MItem> findByCategoryAndPriceCategory(Integer category, Integer packageCategory) {
        List<Object[]> getItemData = itemRepository.findByCategoryAndPriceCategorys(category, packageCategory);
        List<MItem> returnItemData = new ArrayList<>();
        for (Object[] objects : getItemData) {
            MItem itemModifyOb = itemRepository.findOne(Integer.parseInt(objects[0].toString()));
            itemModifyOb.setSalePriceNormal((BigDecimal)objects[1]);
            itemModifyOb.setSalePriceRegister((BigDecimal)objects[2]);
            returnItemData.add(itemModifyOb);
        }
        return returnItemData;
    }

    public List<MItem> findItemsByTypeAndQty(String TYPE) {
        return itemRepository.findByType(TYPE);
    }

    public List<MItem> getSupplierItem(String stock, String nonStock) {
        return itemRepository.findByTypeOrType(stock, nonStock);
    }

    public List<MItem> findItemByItemType(String type) {
        return itemRepository.findItemByType(type);
    }
}
