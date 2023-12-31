package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.service.ItemState;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Item;
import com.ruoyi.system.service.IItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * item tableController
 *
 * @author ruoyi
 * @date 2023-11-12
 */
@RestController
@RequestMapping("/item/item")
public class ItemController extends BaseController
{
    @Autowired
    private IItemService itemService;
    private ItemState currentState;

    public void setItemState(Item item) {
        currentState.setState(item);
    }

    /**
     * 查询item table列表
     */
    @PreAuthorize("@ss.hasPermi('item:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(Item item)
    {
        startPage();
        List<Item> list = itemService.selectItemList(item);
        return getDataTable(list);
    }

    /**
     * 导出item table列表
     */
    @PreAuthorize("@ss.hasPermi('item:item:export')")
    @Log(title = "item table", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Item item)
    {
        List<Item> list = itemService.selectItemList(item);
        ExcelUtil<Item> util = new ExcelUtil<Item>(Item.class);
        util.exportExcel(response, list, "item table数据");
    }

    /**
     * 获取item table详细信息
     */
    @PreAuthorize("@ss.hasPermi('item:item:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(itemService.selectItemByItemId(itemId));
    }

    /**
     * 新增item table
     */
    @PreAuthorize("@ss.hasPermi('item:item:add')")
    @Log(title = "item table", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Item item)
    {
        return toAjax(itemService.insertItem(item));
    }

    /**
     * 修改item table
     */
    @PreAuthorize("@ss.hasPermi('item:item:edit')")
    @Log(title = "item table", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Item item)
    {
        return toAjax(itemService.updateItem(item));
    }

    /**
     * 删除item table
     */
    @PreAuthorize("@ss.hasPermi('item:item:remove')")
    @Log(title = "item table", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(itemService.deleteItemByItemIds(itemIds));
    }
}
