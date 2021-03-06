package com.rui.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.entity.ProductCategory;
import com.rui.entity.ProductInfo;
import com.rui.exception.ShopException;
import com.rui.form.ProductEditForm;
import com.rui.form.ProductForm;
import com.rui.handler.CustomCellWriteHandler;
import com.rui.mapper.ProductCategoryMapper;
import com.rui.mapper.ProductInfoMapper;
import com.rui.result.ResponseEnum;
import com.rui.service.ProductCategoryService;
import com.rui.service.ProductInfoService;
import com.rui.util.ResultVOUtil;
import com.rui.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类目表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductInfoService productInfoService;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Autowired
    ProductInfoMapper productInfoMapper;

    //查询所有商品分类
    //创建CategoryVO
    @GetMapping("/findAllProductCategory")
    public ResultVO findAllProductCategory(){
        //从数据库中获取数据，存入vo中
        List<ProductCategory> list = this.productCategoryService.list();
        List<ProductCategoryVO> voList = new ArrayList<>();
        for (ProductCategory productCategory : list) {
            ProductCategoryVO vo = new ProductCategoryVO();
            vo.setName(productCategory.getCategoryName());
            vo.setType(productCategory.getCategoryType());
            voList.add(vo);
        }

        //封装上层数据层content
        Map<String,List> map = new HashMap<>();
        map.put("content",voList);

        return ResultVOUtil.success(map);
    }

    //添加商品
    @PostMapping("/add")
    public ResultVO add(@Valid @RequestBody ProductForm productForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.PRODUCT_EMPTY.getMsg());
        }

        //赋值
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm, productInfo); //两者的属性名必须完全一致
        productInfo.setProductStatus(1);

        //添加到数据库中
        boolean save = this.productInfoService.save(productInfo);

        if(save) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //查询商品所有商品--分页
    //创建返回数据SellerProductVO
    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page,
                         @PathVariable("size") Integer size){
        //设置分页信息
        Page<ProductInfo> page1 = new Page<>(page, size);
        //从数据库中查询需要呈现的页面
        Page<ProductInfo> selectPage= this.productInfoService.page(page1, null);
        //获取呈现页面上的所有记录的具体数据
        List<ProductInfo> records = selectPage.getRecords();

        //对呈现的数据vo进行赋值
        List<SellerProductVO> voList = new ArrayList<>();
        for (ProductInfo record : records) {
            SellerProductVO vo = new SellerProductVO();
            BeanUtils.copyProperties(record, vo);
            if (record.getProductStatus() == 1) {
                vo.setStatus(true);
            }
            String nameByType = this.productCategoryMapper.getNameByType(record.getCategoryType());
            vo.setCategoryName(nameByType);
            voList.add(vo);
        }

        //封装返回给前端的数据
        Map<String,Object> map = new HashMap<>();
        map.put("content", voList);      //给前端返回vo
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        return ResultVOUtil.success(map);
    }

    //商品的模糊查询 -- 分页
    @GetMapping("/like/{page}/{size}/{keyWord}")
    public ResultVO like(@PathVariable("page") Integer page,
                         @PathVariable("size") Integer size,
                         @PathVariable("keyWord") String keyWord){
        Page<ProductInfo> page1 = new Page<>(page, size);
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("product_name", keyWord);   //和商品查询相比只多了模糊查询条件
        Page<ProductInfo> selectPage = this.productInfoService.page(page1, queryWrapper);
        List<ProductInfo> records = selectPage.getRecords();
        List<SellerProductVO> voList = new ArrayList<>();
        for (ProductInfo record : records) {
            SellerProductVO vo = new SellerProductVO();
            BeanUtils.copyProperties(record, vo);
            if (record.getProductStatus() == 1) {
                vo.setStatus(true);
            }
            String nameByType = this.productCategoryMapper.getNameByType(record.getCategoryType());
            vo.setCategoryName(nameByType);
            voList.add(vo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("content", voList);
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        return ResultVOUtil.success(map);
    }

    //通过分类查询商品
    @GetMapping("/findByCategory/{page}/{size}/{categoryType}")
    public ResultVO findByCategory(@PathVariable("page") Integer page,
                                   @PathVariable("size") Integer size,
                                   @PathVariable("categoryType") Integer categoryType){
        Page<ProductInfo> page1 = new Page<>(page, size);
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_type", categoryType);    //添加商品分类查询条件
        Page<ProductInfo> selectPage = this.productInfoService.page(page1, queryWrapper);
        List<ProductInfo> records = selectPage.getRecords();
        List<SellerProductVO> voList = new ArrayList<>();
        for (ProductInfo record : records) {
            SellerProductVO vo = new SellerProductVO();
            BeanUtils.copyProperties(record, vo);
            if (record.getProductStatus() == 1) {
                vo.setStatus(true);
            }
            String nameByType = this.productCategoryMapper.getNameByType(record.getCategoryType());
            vo.setCategoryName(nameByType);
            voList.add(vo);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("content", voList);
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        return ResultVOUtil.success(map);
    }

    //通过Id查询商品
    //创建VO-SellerProductVOById
    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id){
        //获取数据库中相应id商品信息
        ProductInfo byId = this.productInfoService.getById(id);

        //赋值
        SellerProductVOById vo = new SellerProductVOById();
        BeanUtils.copyProperties(byId, vo);
        if (byId.getProductStatus() == 1) {
            vo.setStatus(true);
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("categoryType", byId.getCategoryType());
        vo.setCategory(map);

        return ResultVOUtil.success(vo);
    }

    //通过ID删除商品
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Integer id){
        boolean b = this.productInfoService.removeById(id);
        if (b) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //修改商品状态
    //在mapper中创建新的sql方法
    @PutMapping("/updateStatus/{id}/{status}")
    public ResultVO updateStatus(@PathVariable("id") Integer id,
                                 @PathVariable("status") Boolean status){
        Integer statusInt = 0;
        if(status) statusInt = 1;
        Boolean aBoolean = this.productInfoMapper.updateStatusById(id, statusInt);
        if(aBoolean) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //修改商品详细信息--根据Id
    //创建vo-ProductEditForm来接收前端传来的数据
    @PutMapping("/update")
    public ResultVO update(@Valid @RequestBody ProductEditForm productEditForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ShopException(ResponseEnum.PRODUCT_EMPTY.getMsg());
        }

        //整理数据赋值给数据库数据对象
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productEditForm, productInfo);
        productInfo.setProductStatus(0);
        if(productEditForm.getStatus()) productInfo.setProductStatus(1);
        productInfo.setCategoryType(productEditForm.getCategory().getCategoryType());

        //更新数据库数据
        boolean b = this.productInfoService.updateById(productInfo);

        if(b) return ResultVOUtil.success(null);
        return ResultVOUtil.fail(null);
    }

    //导出所有商品信息为excel表格
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("商品信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<ProductExcelVO> productExcelVOList = this.productInfoService.productExcelVOList();

            EasyExcel.write(response.getOutputStream(), ProductExcelVO.class)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet("商品信息")
                    .doWrite(productExcelVOList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //通过excel批量输入商品信息
    @PostMapping("/import")
    public ResultVO importData(@RequestParam("file") MultipartFile file){
        List<ProductInfo> productInfoList = null;
        try {
            productInfoList = this.productInfoService.excleToProductInfoList(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(productInfoList==null){
            return ResultVOUtil.fail("导入Excel失败！");
        }
        boolean result = this.productInfoService.saveBatch(productInfoList);
        if(result)return ResultVOUtil.success(null);
        return ResultVOUtil.fail("导入Excel失败！");
    }

}

