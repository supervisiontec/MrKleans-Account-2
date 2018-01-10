/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.items.items;

import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.master.brand.BrandRepository;
import com.mac.care_point.master.brand.model.MBrand;
import com.mac.care_point.master.category.CategoryRepository;
import com.mac.care_point.master.category.model.MCategory;
import com.mac.care_point.master.item_category.ItemCategoryRepository;
import com.mac.care_point.master.item_category.model.MItemCategory;
import com.mac.care_point.master.item_department.ItemDepartmentRepository;
import com.mac.care_point.master.item_department.model.MItemDepartmentMain;
import com.mac.care_point.master.items.item_unit.ItemUnitRepository;
import com.mac.care_point.master.items.item_unit.model.MItemUnits;
import com.mac.care_point.master.items.items.model.MItem;
import com.mac.care_point.master.items.items.model.MItemMix;
import com.mac.care_point.master.reOrderLevel.ReOrderRepository;
import com.mac.care_point.master.reOrderLevel.model.MReOrderLevel;
import com.mac.care_point.master.subCategory.SubCategoryRepository;
import com.mac.care_point.master.subCategory.model.MSubCategory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
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

    @Autowired
    private ReOrderRepository reOrderRepository;

    @Autowired
    private ItemDepartmentRepository itemDepartmentRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    
    @Autowired
    private BrandRepository brandRepository;
    
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Transactional
    public MItem saveItem(MItemMix mix) {
        MItem item = mix.getItem();
        Integer number = item.getIndexNo();

        Integer saveDepartment = saveDepartment(mix.getDepartment());
        if (saveDepartment != null) {
            mix.getItem().setDepartment(saveDepartment);
        }
        Integer saveCategory = saveCategory(mix.getCategory());
        if (saveCategory != null) {
            mix.getItem().setCategory(saveCategory);
        }
        Integer saveSubCategory = saveSubCategory(mix.getSubCategory());
        if (saveSubCategory != null) {
            mix.getItem().setSub_category(saveSubCategory);
        }
        Integer saveBrand = saveBrand(mix.getBrand());
        if (saveBrand != null) {
            mix.getItem().setBrand(saveBrand);
        }
        Integer saveItemCategory = saveItemCategory(mix.getItemCategory());
        if (saveItemCategory != null) {
            mix.getItem().setBrand(saveItemCategory);
        }

        MItem saveItem = itemRepository.save(item);

        if ("STOCK".equals(saveItem.getType()) || "NON STOCK".equals(saveItem.getType())) {
            //set reorder qty
            if (null == number) {
                List<MBranch> branchList = branchRepository.findAll();

                List<MReOrderLevel> reOrderList = new ArrayList<>();

                for (MBranch branch : branchList) {
                    MReOrderLevel reOrderLevel = new MReOrderLevel();
                    reOrderLevel.setItem(saveItem.getIndexNo());
                    reOrderLevel.setBranch(branch.getIndexNo());
                    reOrderLevel.setReOrderMax(item.getReOrderMax());
                    reOrderLevel.setReOrderMin(item.getReOrderMin());
                    reOrderList.add(reOrderLevel);
                }

                for (MReOrderLevel mReOrderLevel : reOrderList) {
                    reOrderRepository.save(mReOrderLevel);

                }
            }
        }
        if ("STOCK".equals(saveItem.getType()) || "SERVICE".equals(saveItem.getType()) || "NON STOCK".equals(saveItem.getType())) {

            //create item units
            List<MItemUnits> findItemList = itemUnitRepository.findByItemAndItemUnitType(saveItem.getIndexNo(), "MAIN");

            if (findItemList.isEmpty()) {

                MItemUnits itemUnits = new MItemUnits();
                itemUnits.setItem(saveItem.getIndexNo());
                itemUnits.setItemUnitType("MAIN");
                itemUnits.setName(item.getName());
                itemUnits.setQty(new BigDecimal(1));
                itemUnits.setSalePriceNormal(item.getSalePriceNormal());
                itemUnits.setSalePriceRegister(item.getSalePriceRegister());
                itemUnits.setCostPrice(item.getCostPrice());
                itemUnitRepository.save(itemUnits);

            } else {

                MItemUnits itemUnits = findItemList.get(0);
                itemUnits.setName(item.getName());
                itemUnits.setSalePriceNormal(item.getSalePriceNormal());
                itemUnits.setSalePriceRegister(item.getSalePriceRegister());
                itemUnits.setCostPrice(item.getCostPrice());
                itemUnitRepository.save(itemUnits);

            }

        }
        return saveItem;
    }

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
            itemModifyOb.setSalePriceNormal((BigDecimal) objects[1]);
            itemModifyOb.setSalePriceRegister((BigDecimal) objects[2]);
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

    public MItemDepartmentMain saveItemDepartment(String name) {
        List<MItemDepartmentMain> list = itemDepartmentRepository.findByName(name);
        if (list.size() > 0) {
            return list.get(0);
        }

        MItemDepartmentMain department = new MItemDepartmentMain();
        department.setName(name);

        return itemDepartmentRepository.save(department);

    }

    private Integer saveDepartment(String name) {
        if (name != null) {

            List<MItemDepartmentMain> list = itemDepartmentRepository.findByName(name);
            if (list.size() > 0) {
                return list.get(0).getIndexNo();
            }

            MItemDepartmentMain department = new MItemDepartmentMain();
            department.setName(name);

            return itemDepartmentRepository.save(department).getIndexNo();
        }
        return null;
    }

    private Integer saveCategory(String name) {
        if (name != null) {

            List<MCategory> list = categoryRepository.findByName(name);
            if (list.size() > 0) {
                return list.get(0).getIndexNo();
            }

            MCategory category = new MCategory();
            category.setName(name);

            return categoryRepository.save(category).getIndexNo();
        }
        return null;
    }

    private Integer saveSubCategory(String name) {
        if (name != null) {

            List<MSubCategory> list = subCategoryRepository.findByName(name);
            if (list.size() > 0) {
                return list.get(0).getIndexNo();
            }

            MSubCategory subCategory = new MSubCategory();
            subCategory.setName(name);

            return subCategoryRepository.save(subCategory).getIndexNo();
        }
        return null;
    }

    private Integer saveBrand(String name) {
        if (name != null) {

            List<MBrand> list = brandRepository.findByName(name);
            if (list.size() > 0) {
                return list.get(0).getIndexNo();
            }

            MBrand brand = new MBrand();
            brand.setName(name);

            return brandRepository.save(brand).getIndexNo();
        }
        return null;
    }

    private Integer saveItemCategory(String name) {
        if (name != null) {

            List<MItemCategory> list = itemCategoryRepository.findByName(name);
            if (list.size() > 0) {
                return list.get(0).getIndexNo();
            }

            MItemCategory brand = new MItemCategory();
            brand.setName(name);

            return itemCategoryRepository.save(brand).getIndexNo();
        }
        return null;
    }
}
